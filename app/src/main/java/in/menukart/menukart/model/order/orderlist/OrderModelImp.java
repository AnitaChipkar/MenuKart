package in.menukart.menukart.model.order.orderlist;

import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.order.Menu;
import in.menukart.menukart.entities.order.OrderMenu;
import in.menukart.menukart.model.order.menu.MenuModel;
import in.menukart.menukart.presenter.order.menu.MenuPresenterImp;
import in.menukart.menukart.presenter.order.orderlist.OrderPresenterImp;

public class OrderModelImp implements OrderModel {

    private ApiClient apiClient;
    private APIClientCallback<OrderMenu> callback;

    public OrderModelImp(OrderPresenterImp callback, ApiClient client) {
        this.callback = (APIClientCallback<OrderMenu>) callback;
        this.apiClient = client;
    }

    @Override
    public void OrderList(String orderListJson) {
        apiClient.orderList(callback, orderListJson);

    }

}
