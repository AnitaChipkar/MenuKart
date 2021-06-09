package in.menukart.menukart.view.foodcart.trackorder;


import in.menukart.menukart.entities.setting.ResponseSuccess;

public interface CheckOrderStatusView {
    void showError(String error);

    void onSuccessful(ResponseSuccess responseSuccess);
}
