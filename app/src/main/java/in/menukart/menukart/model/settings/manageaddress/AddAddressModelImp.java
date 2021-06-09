package in.menukart.menukart.model.settings.manageaddress;

import java.util.Map;

import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.setting.ResponseSuccess;
import in.menukart.menukart.presenter.setting.manageaddress.AddAddressPresenterImp;

public class AddAddressModelImp implements AddAddressModel {

    private ApiClient apiClient;
    private APIClientCallback<ResponseSuccess> callback;

    public AddAddressModelImp(AddAddressPresenterImp callback, ApiClient client) {
        this.callback = (APIClientCallback<ResponseSuccess>) callback;
        this.apiClient = client;
    }

    @Override
    public void addAddress(Map<String,String> params) {
        apiClient.userAddAddressList(callback, params);

    }

}
