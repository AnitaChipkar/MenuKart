package in.menukart.menukart.model.settings.userprofile;

import java.util.Map;

import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.setting.ResponseSuccess;
import in.menukart.menukart.presenter.setting.userprofile.UpdateProfilePresenterImp;

public class UpdateProfileModelImp implements UpdateProfileModel {

    private ApiClient apiClient;
    private APIClientCallback<ResponseSuccess> callback;

    public UpdateProfileModelImp(UpdateProfilePresenterImp callback, ApiClient client) {
        this.callback = (APIClientCallback<ResponseSuccess>) callback;
        this.apiClient = client;
    }

    @Override
    public void updateProfile(Map<String,String> params) {
        apiClient.userUpdateProfile(callback, params);

    }

}
