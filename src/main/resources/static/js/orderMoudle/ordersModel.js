import CommonUtil from '../common/common.js';

let commonUtil = new CommonUtil.CommonUtil();
let contextRootUrl = commonUtil.getContextRootURL();

class OrdersModel{
	
	fetchOrdersForSpecPage(pageNumber){
		let url = contextRootUrl+ "/querypartpacklist?pageNumber=" + pageNumber;
		const promise = new Promise((resolve,reject)=>this.fetchDataForJson(url,resolve,reject));
		return promise;
		
	}
	querySpecOrder(queryData){
		let url = contextRootUrl+ "/queryOrder?" + queryData.field+"=" + queryData.value;
		const promise = new Promise((resolve,reject)=>this.fetchDataForJson(url,resolve,reject));
		return promise;
	}
	addOrder(params){
		let url = contextRootUrl + "/addOrder";
		const promise = new Promise((resolve,reject)=>this.postData(url,params,resolve,reject));
		return promise;
	}
	updateOrder(params){
		let url = contextRootUrl + "/updateOrder";
		const promise = new Promise((resolve,reject)=>this.postData(url,params,resolve,reject));
		return promise;
	}
	deleteOrder(params){
		let url = contextRootUrl + "/DeleteOrder";
		const promise = new Promise((resolve,reject)=>this.postData(url,params,resolve,reject));
		return promise;
	}
	
	fetchDataForJson(currentURL,resolve,reject){
		$.ajax({
			type: "get",
			url: currentURL,
			cache: false,
			dataType: "json",
			success:function(result){
				resolve(result);
			},
			error:function(result){
				reject(result.statusText);
			}
		});
	}
	postData(currentURL,params,resolve,reject){
		$.ajax({
			type: "post",
			data:{"order":params},
			url: currentURL,
			cache: false,
			dataType:"json",
			success:function(result){
				resolve(result);
			},
			error:function(result){
				reject(result.statusText);
			}
		});
	}
	fetchTemplate(urlStr){
		const promise = new Promise(function(resolve,reject){	
			$.ajax({
				type: "get",
				url: urlStr,
				cache: false,
				dataType: "text",
				success:function(result){
					resolve(result);
				},
				error:function(result){
					reject(result.statusText);
				}
			});
		});
		return promise;
	}
}



export default {OrdersModel};