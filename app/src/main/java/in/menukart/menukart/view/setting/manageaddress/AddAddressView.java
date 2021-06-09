package in.menukart.menukart.view.setting.manageaddress;


import in.menukart.menukart.entities.setting.ResponseSuccess;

public interface AddAddressView {
    void showError(String error);
    void onSuccessfulAddAddress(ResponseSuccess responseSuccess);
}
