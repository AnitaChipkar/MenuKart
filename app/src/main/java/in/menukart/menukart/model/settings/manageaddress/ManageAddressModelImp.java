package in.menukart.menukart.model.settings.manageaddress;

import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.setting.ManageAddress;
import in.menukart.menukart.presenter.setting.manageaddress.ManageAddressPresenterImp;

public class ManageAddressModelImp implements ManageAddressModel {

    private ApiClient apiClient;
    private APIClientCallback<ManageAddress> callback;

    public ManageAddressModelImp(ManageAddressPresenterImp callback, ApiClient client) {
        this.callback = (APIClientCallback<ManageAddress>) callback;
        this.apiClient = client;
    }

    @Override
    public void userAddress(String userAddress) {
        apiClient.UserAddressList(callback, userAddress);

    }

}
