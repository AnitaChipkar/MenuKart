package in.menukart.menukart.view.setting.manageaddress;


import in.menukart.menukart.entities.setting.ManageAddress;

public interface ManageAddressView {
    void showError(String error);
    void onSuccessfulPrivacyLinks(ManageAddress manageAddress);
}
