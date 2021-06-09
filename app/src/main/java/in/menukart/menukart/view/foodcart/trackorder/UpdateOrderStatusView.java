package in.menukart.menukart.view.foodcart.trackorder;


import in.menukart.menukart.entities.setting.ResponseSuccess;

public interface UpdateOrderStatusView {
    void showErrorUpdateOrderStatus(String error);

    void onUpdateOrderStatusSuccessful(ResponseSuccess responseSuccess);
}
