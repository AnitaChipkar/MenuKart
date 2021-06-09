package in.menukart.menukart.presenter.setting.manageaddress;


import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.setting.ManageAddress;
import in.menukart.menukart.model.settings.manageaddress.ManageAddressModel;
import in.menukart.menukart.model.settings.manageaddress.ManageAddressModelImp;
import in.menukart.menukart.view.setting.manageaddress.ManageAddressView;

public class ManageAddressPresenterImp implements ManageAddressPresenter, APIClientCallback<ManageAddress> {

    ManageAddressModel manageAddressModel;
    ManageAddressView manageAddressView;

    public ManageAddressPresenterImp(ManageAddressView manageAddressView, ApiClient client) {
        this.manageAddressView = manageAddressView;
        manageAddressModel = new ManageAddressModelImp(this, client);

    }

    @Override
    public void onSuccess(ManageAddress success) {
        manageAddressView.onSuccessfulPrivacyLinks(success);
    }

    @Override
    public void onFailure(Exception e) {
        manageAddressView.showError(e.getMessage());
    }

    @Override
    public void requestUserAddress(String userAddress) {
        manageAddressModel.userAddress(userAddress);
    }
}
