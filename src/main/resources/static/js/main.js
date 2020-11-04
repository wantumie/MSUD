import OrdersView from './orderMoudle/ordersView.js';

init();
function init() {
	loadOrderTableHandler();
	$(".messageModal").on("hidden.bs.modal",()=>{
		if($(".modal-backdrop").length > 1){
			$(".modal-backdrop")[0].remove();
		}
	});
}

function loadOrderTableHandler() {
	let ordersView = new OrdersView.OrdersView();
	ordersView.init();
}

