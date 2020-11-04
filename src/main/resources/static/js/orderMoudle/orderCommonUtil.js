class OrderCommonUtil{
	getLabels(){
		return {
			"QUERY_BTN_LBL":"查询",
			"QUERY_RESULT_LBL":"查询结果",
			"ADD_PRODUCT_BTN_LBL":"添加",
			"ACTION_LBL":"操作",
			"PREV_PAGE_BTN_LBL":"上一页",
			"NEXT_PAGE_BTN_LBL":"下一页",
			"LAST_PAGE_BTN_LBL":"最后一页",
			"VIEW_BTN_LBL":"查看",
			"EDIT_BTN_LBL":"编辑",
			"DELETE_BTN_LBL":"删除",
			"CONFRIM_BTN_LBL":"确认",
			"CANCEL_BTN_LBL":"取消",
			"FETCH_DATA_FAILD_MSG":"获取数据失败，请联系管理员，感谢您的使用!",
			"DELET_DATA_FAILD_MSG":"产品删除失败，请联系管理员，感谢您的使用!",
			"DELET_DATA_SUCCESS_MSG":"产品删除成功!",
			"UPDATE_DATA_FAILD_MSG":"产品信息更新失败，请联系管理员，感谢您的使用!",
			"UPDATE_DATA_SUCCESS_MSG":"产品信息更新成功!",
			"ADD_DATA_FAILD_MSG":"产品添加失败，请联系管理员，感谢您的使用!",
			"ADD_DATA_SUCCESS_MSG":"产品添加成功!",
			"ADD_PRODUCT_MODAL_TITLE":"添加产品",
			"VIEW_PRODUCT_MODAL_TITLE":"查看产品",
			"EDIT_PRODUCT_MODAL_TITLE":"编辑产品"
		}
		
	}
	getSpecQueryField(){
		return "packingTypeId";
	}
	getOrderHeader(){
		let orderHeader = {"segNo":"segNo",
			"packingTypeId":"类型编号",
			"factoryProductid":"factoryProductid",
			"spec":"spec",
			"productTypeName":"产品类别名称",
			"shopsign":"shopsign",
			"quantity":"数量",
			"machineId":"机器编号",
			"qualityGradeName":"qualityGradeName",
			"confirmDate":"确认时间",
			"unitedPackid":"unitedPackid",
			"confirmPerson":"confirmPerson",
			"partid":"partid",
			"mwrapid":"mwrapid",
			"grossWeight":"grossWeight",
			"putinWeight":"putinWeight",
			"netWeight":"netWeight",
		};
		return orderHeader;
	}
	getEditableFieldsForOrder(){
		let editableFieldsObj = {"segNo":false,
			"packingTypeId":false,
			"factoryProductid":false,
			"spec":false,
			"productTypeName":false,
			"shopsign":false,
			"quantity":true,
			"machineId":true,
			"qualityGradeName":true,
			"confirmDate":true,
			"unitedPackid":true,
			"confirmPerson":true,
			"partid":true,
			"mwrapid":true,
			"grossWeight":true,
			"putinWeight":true,
			"netWeight":true,
		};
		return editableFieldsObj;
	}
	getOrderdetils(){
		let orderDetails = {"segNo":"",
			"packingTypeId":"",
			"factoryProductid":"",
			"spec":"",
			"productTypeName":"",
			"shopsign":"",
			"quantity":"",
			"machineId":"",
			"qualityGradeName":"",
			"confirmDate":"",
			"unitedPackid":"",
			"confirmPerson":"",
			"partid":"",
			"mwrapid":"",
			"grossWeight":"",
			"putinWeight":"",
			"netWeight":"",
		};
		return orderDetails;
	}
	
}

export default {OrderCommonUtil};