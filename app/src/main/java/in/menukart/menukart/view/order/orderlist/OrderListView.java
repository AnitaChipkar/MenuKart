package in.menukart.menukart.view.order.orderlist;


import in.menukart.menukart.entities.order.OrderMenu;

public interface OrderListView {
    void showError(String error);
    void onSuccessfulOrderList(OrderMenu orderMenu);
}
