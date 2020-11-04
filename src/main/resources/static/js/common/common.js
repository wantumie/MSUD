class CommonUtil{
	
	getContextRootURL(){
		return window.location.pathname.substring(0,window.location.pathname.indexOf("/",2));
		
	}
	uiLoader(args){
		if(args === "show"){
			$("#loadingPage").show();
		}else{
			$("#loadingPage").hide();
		}
	}
}

export default {CommonUtil};