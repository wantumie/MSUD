import OrdersModel from './ordersModel.js';
import CommonUtil from '../common/common.js';
import OrderCommonUtil from './orderCommonUtil.js';

let orderViewTpl = "";
let orderItemTpl ="";
let orderDetailsModalTpl="";
let currentData = {};
let orderList = [];
let ordersModel  = new OrdersModel.OrdersModel();
let commonUtil = new CommonUtil.CommonUtil();
let orderCommonUtil = new OrderCommonUtil.OrderCommonUtil();
let labelBundle = orderCommonUtil.getLabels();

Handlebars.registerHelper("disableBtnForFooter",function(ind,params){
	let totalPage = params && params.hasOwnProperty("totalPageCount") && params.totalPageCount ? params.totalPageCount : 0;
	let cuttentPage = params && params.hasOwnProperty("currentPageNo") && params.currentPageNo ? params.currentPageNo : 0;
	if(totalPage && totalPage<=1){
		return "ui-disabled";
	}else if(ind && (ind === "N" || ind === "L") && totalPage && cuttentPage && cuttentPage >= totalPage ){
		return "ui-disabled";
	}
	return "" ;
});



class OrdersView {
	init(){	
		commonUtil.uiLoader("show");
		Promise.all([ordersModel.fetchOrdersForSpecPage(0),
		 ordersModel.fetchTemplate("./orderMoudleTemplates/orderView.tpl"),
		 ordersModel.fetchTemplate("./orderMoudleTemplates/orderItem.tpl"),
		 ordersModel.fetchTemplate("./orderMoudleTemplates/orderDetailsModal.tpl")])
		 .then(([result,orderViewTemplate,orderItemTemplate,orderDetailsModalTemplate])=>{
			 currentData = typeof result === "object" ? result : {};;
			 orderItemTpl = Handlebars.compile(orderItemTemplate);
			 Handlebars.registerPartial('orderItemTemplate',orderItemTpl);
			 orderViewTpl = Handlebars.compile(orderViewTemplate);
			 orderDetailsModalTpl = Handlebars.compile(orderDetailsModalTemplate);
			 this.renderPage();
		 }).catch((error)=>{
			commonUtil.uiLoader("hide");
			$(".messageModal .messageInfo").html(labelBundle.FETCH_DATA_FAILD_MSG);
			$(".messageModal").modal('show');
		});
	}
	renderPage() {
		commonUtil.uiLoader("hide");
	    orderList = currentData && currentData.hasOwnProperty("resultInfo") &&  currentData.resultInfo instanceof Array ? currentData.resultInfo : [];
		let renderData = {
			queryName:orderCommonUtil.getSpecQueryField(),
			header:orderCommonUtil.getOrderHeader(),
			data:currentData,
			labelBundle:labelBundle
		}
		$("#orderTableViewContainer").html(orderViewTpl(renderData));
		this.eventBindingHandler();

	}
	
	eventBindingHandler(){
		$(".footer .navToPrevPageBtn").on("click",(event)=>this.navigateToSpecPageHandler(event));
		$(".footer .navToNextPageBtn").on("click",(event)=>this.navigateToSpecPageHandler(event));
		$(".footer .navToLastPageButton").on("click",(event)=>this.navigateToSpecPageHandler(event));
		$(".footer .navToLastPageButton").on("click",(event)=>this.navigateToSpecPageHandler(event));
		$("#filterButton").on("click",(event)=>this.querySpecOrderHandler(event));
		$("#addOrderBtn").on("click",(event)=>this.addOrderHandler(event));
		$(".orderItem .orderInfos").on("click",(event)=>this.viewOrderHandler(event));
		$(".orderItem .editOrder").on("click",(event)=>this.editOrderHandler(event));
		$(".orderItem .deleteOrder").on("click",(event)=>this.deleteOrderHandler(event));
	}
	navigateToSpecPageHandler(event){
		$(".specPageNoTextInput").addClass("red-border");
		let currentPageNo = currentData && currentData.hasOwnProperty("currentPageNo") &&  currentData.currentPageNo ? currentData.currentPageNo : 1;
		let totalPageCount = currentData && currentData.hasOwnProperty("totalPageCount") &&  currentData.totalPageCount ? currentData.totalPageCount : 0;
		let pageNumber = 0
		let btn = $(event.currentTarget);
		if(btn.attr("data-indicator") === "P"){
			pageNumber = currentPageNo > 1 ?currentPageNo - 1 : 0;
		}
		if(btn.attr("data-indicator") === "N"){
			pageNumber = currentPageNo >= totalPageCount ? currentPageNo : currentPageNo+1;
		}
		if(btn.attr("data-indicator") === "L"){
			pageNumber = totalPageCount;
		}
		if(btn.attr("data-indicator") === "S"){
			let sepecPageNo = Number($(".specPageNoTextInput").val().trim());
			if(!Number.isNaN(sepecPageNo) && sepecPageNo > 0 ){
				pageNumber =  sepecPageNo;
			}else{
				$(".specPageNoTextInput").addClass("red-border");
				return;
			}
		}
		commonUtil.uiLoader("show");
		ordersModel.fetchOrdersForSpecPage(pageNumber).then((result)=>{
			currentData = typeof result === "object" ? result : {};
			this.renderPage();
		}).catch((error)=>{
			this.renderPage();
			$(".messageModal .messageInfo").html(labelBundle.FETCH_DATA_FAILD_MSG);
			$(".messageModal").modal('show');
		});
	}
	
	querySpecOrderHandler(event){
		let queryStr = $("#filterTextInput").val().trim() ;
		if(queryStr === "" ){
			 $("#filterTextInput").addClass("red-border");
			return;
		}else{
			commonUtil.uiLoader("show");
			ordersModel.querySpecOrder({"field":orderCommonUtil.getSpecQueryField(), "value":queryStr})
			.then((result)=>this.renderOrder(result))
			.catch(()=>{
				commonUtil.uiLoader("hide");
				$(".messageModal .messageInfo").html(labelBundle.FETCH_DATA_FAILD_MSG);
				$(".messageModal").modal('show');
			});
		}
		
	}
	renderOrder(data){
		commonUtil.uiLoader("hide");
		orderList = data && data.hasOwnProperty("resultInfo") &&  data.resultInfo instanceof Array ? data.resultInfo : [];
	    $(".orderContentContainer").html(orderItemTpl({"orderList":orderList,"labelBundle":labelBundle}));
		$(".orderItem .orderInfos").on("click",(event)=>this.viewOrderHandler(event));
		$(".orderItem .editOrder").on("click",(event)=>this.editOrderHandler(event));
		$(".orderItem .deleteOrder").on("click",(event)=>this.deleteOrderHandler(event));
	}
	addOrderHandler(event){
		let data={
			title:labelBundle.ADD_PRODUCT_MODAL_TITLE,
			header:orderCommonUtil.getOrderHeader(),
			isAddOrder:true,
			labelBundle:labelBundle
		}
		this.renderOrderDetailModalHandler(data);
		$(".ordersContentContainer .OrderDetailsModal #confirmAddOrderBtn").on("click",()=>this.confirmAddOrderHandler());
	}
	confirmAddOrderHandler(){
		let validateFailed = false;
		let inputCompList = $(".ordersContentContainer .OrderDetailsModal input");
		inputCompList.removeClass("red-border");
		for(let inputComp of inputCompList){
			if($(inputComp).val().trim() === ""){
				$(inputComp).addClass("red-border");
				validateFailed = true;
			}
		}
		if(validateFailed){
			return;
		}
		commonUtil.uiLoader("show");
		ordersModel.addOrder(this.getOrderDetails()).then((result)=>{
			currentData = typeof result === "object" ? result : {};;
			this.renderPage();
			$(".ordersContentContainer .OrderDetailsModal").modal("hide");
			$(".modal-backdrop")[0].remove();
			$(".messageModal .messageInfo").html(labelBundle.ADD_DATA_SUCCESS_MSG);
			$(".messageModal").modal('show');
		}).catch(()=>{
			commonUtil.uiLoader("hide");
			$(".messageModal .messageInfo").html(labelBundle.ADD_DATA_FAILD_MSG);
			$(".messageModal").modal('show');
		});
	}
	viewOrderHandler(event){
		let currentOrder = this.getCurrentOrder(event);
		if(currentOrder){
			let data={
				title:labelBundle.VIEW_PRODUCT_MODAL_TITLE,
				header:orderCommonUtil.getOrderHeader(),
				order:currentOrder,
				isViewOrder:true,
				labelBundle:labelBundle
			}
			this.renderOrderDetailModalHandler(data);
		}
		
	}
	renderOrderDetailModalHandler(orderDetails){
		$(".ordersContentContainer .OrderDetailsModal .modal-dialog").html(orderDetailsModalTpl(orderDetails));
		$(".ordersContentContainer .OrderDetailsModal").modal("show");
	}
	editOrderHandler(event){
		let currentOrder = this.getCurrentOrder(event);
		if(currentOrder){
			let data={
				title:labelBundle.EDIT_PRODUCT_MODAL_TITLE,
				header:orderCommonUtil.getOrderHeader(),
				order:currentOrder,
				eitableFeilds:orderCommonUtil.getEditableFieldsForOrder(),
				isEditOrder:true,
				labelBundle:labelBundle
			}
			this.renderOrderDetailModalHandler(data);
			$(".ordersContentContainer .OrderDetailsModal #confirmEditOrderBtn").on("click",()=>this.confirmEditOrderHandler());
			
		}
	}
	confirmEditOrderHandler(){
		let validateFailed = false;
		let inputCompList = $(".ordersContentContainer .OrderDetailsModal input").not($("input:disabled"));
		inputCompList.removeClass("red-border");
		for(let inputComp of inputCompList){
			if($(inputComp).val().trim() === ""){
				$(inputComp).addClass("red-border");
				validateFailed = true;
			}
		}
		if(validateFailed){
			return;
		}
		commonUtil.uiLoader("show");
		ordersModel.updateOrder(this.getOrderDetails()).then((result)=>{
			currentData = typeof result === "object" ? result : {};;
			this.renderPage();
			$(".ordersContentContainer .OrderDetailsModal").modal("hide");
			$(".modal-backdrop")[0].remove();
			$(".messageModal .messageInfo").html(labelBundle.UPDATE_DATA_SUCCESS_MSG);
			$(".messageModal").modal('show');
		}).catch(()=>{
			commonUtil.uiLoader("hide");
			$(".messageModal .messageInfo").html(labelBundle.UPDATE_DATA_FAILD_MSG);
			$(".messageModal").modal('show');
		});
		
	}
	deleteOrderHandler(event){
		let orderInd = $(event.currentTarget).parents(".orderItem").attr("data-id");
		ordersModel.deleteOrder(orderInd).then((result)=>{
			currentData = typeof result === "object" ? result : {};;
			this.renderPage();
			$(".ordersContentContainer .OrderDetailsModal").modal("hide");
			$(".messageModal .messageInfo").html(labelBundle.DELET_DATA_SUCCESS_MSG);
			$(".messageModal").modal('show');
		}).catch(()=>{
			commonUtil.uiLoader("hide");
			$(".messageModal .messageInfo").html(labelBundle.DELET_DATA_FAILD_MSG);
			$(".messageModal").modal('show');
		});
	}
	getCurrentOrder(event){
		let indicator = $(event.currentTarget).parents(".orderItem").attr("data-id");
		let currentOrder = orderList.find((item)=>{
			return item && item.hasOwnProperty("packingTypeId") && item.packingTypeId === indicator;
		});
		return currentOrder || {};
	}
	getOrderDetails(){
		let orderDetails = orderCommonUtil.getOrderdetils();
		orderDetails.segNo = $(".OrderDetailsModal .segNoInput").val().trim();
		orderDetails.factoryProductid =  $(".OrderDetailsModal .factoryProductidInput").val().trim();
		orderDetails.spec =  $(".OrderDetailsModal .segNoInput").val().trim();
		orderDetails.productTypeName =  $(".OrderDetailsModal .productTypeNameInput").val().trim();
		orderDetails.shopsign = $(".OrderDetailsModal .shopsignInput").val().trim();
		orderDetails.quantity = $(".OrderDetailsModal .quantityInput").val().trim();
		orderDetails.machineId = $(".OrderDetailsModal .machineIdInput").val().trim();
		orderDetails.qualityGradeName = $(".OrderDetailsModal .qualityGradeNameInput").val().trim();
		orderDetails.confirmDate = $(".OrderDetailsModal .confirmDateInput").val().trim();
		orderDetails.unitedPackid = $(".OrderDetailsModal .unitedPackidInput").val().trim();
		orderDetails.confirmPerson = $(".OrderDetailsModal .confirmPersonInput").val().trim();
		orderDetails.partid = $(".OrderDetailsModal .partidInput").val().trim();
		orderDetails.mwrapid = $(".OrderDetailsModal .mwrapidInput").val().trim();
		orderDetails.grossWeight = $(".OrderDetailsModal .grossWeightInput").val().trim();
		orderDetails.putinWeight = $(".OrderDetailsModal .putinWeightInput").val().trim();
		orderDetails.netWeight = $(".OrderDetailsModal .netWeightInput").val().trim();
		return orderDetails;
	}
}

export default {OrdersView};