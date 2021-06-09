package in.menukart.menukart.model.settings.manageaddress;

import java.util.Map;

import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.setting.ResponseSuccess;
import in.menukart.menukart.presenter.setting.manageaddress.UpdateAddressPresenterImp;

public class UpdateAddressModelImp implements UpdateAddressModel {

    private ApiClient apiClient;
    private APIClientCallback<ResponseSuccess> callback;

    public UpdateAddressModelImp(UpdateAddressPresenterImp callback, ApiClient client) {
        this.callback = (APIClientCallback<ResponseSuccess>) callback;
        this.apiClient = client;
    }

    @Override
    public void updateAddress(Map<String,String> params) {
        apiClient.userUpdateAddressList(callback, params);

    }

}
