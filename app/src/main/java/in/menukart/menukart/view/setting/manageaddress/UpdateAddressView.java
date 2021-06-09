package in.menukart.menukart.view.setting.manageaddress;


import in.menukart.menukart.entities.setting.ResponseSuccess;

public interface UpdateAddressView {
    void showUpdateAddressError(String error);
    void onSuccessfulUpdateAddress(ResponseSuccess responseSuccess);
}
