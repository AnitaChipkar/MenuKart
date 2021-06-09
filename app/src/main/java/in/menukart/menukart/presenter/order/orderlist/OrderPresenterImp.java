package in.menukart.menukart.presenter.order.orderlist;


import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.order.OrderMenu;
import in.menukart.menukart.model.order.orderlist.OrderModel;
import in.menukart.menukart.model.order.orderlist.OrderModelImp;
import in.menukart.menukart.view.order.orderlist.OrderListView;

public class OrderPresenterImp implements OrderPresenter, APIClientCallback<OrderMenu> {

    OrderModel orderModel;
    OrderListView orderListView;

    public OrderPresenterImp(OrderListView menuView, ApiClient client) {
        this.orderListView = menuView;
        orderModel = new OrderModelImp(this, client);

    }

    @Override
    public void onSuccess(OrderMenu success) {
        orderListView.onSuccessfulOrderList(success);
    }

    @Override
    public void onFailure(Exception e) {
        orderListView.showError(e.getMessage());
    }

    @Override
    public void requestOrderList(String orderListJson) {
        orderModel.OrderList(orderListJson);
    }
}
