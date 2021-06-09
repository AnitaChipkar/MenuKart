package in.menukart.menukart.model.foodcart.signup;

import java.util.Map;

import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.setting.ResponseSuccess;
import in.menukart.menukart.presenter.foodcart.signup.SignUpPresenterImp;

public class SignUpModelImp implements SignUpModel {

    private ApiClient apiClient;
    private APIClientCallback<ResponseSuccess> callback;

    public SignUpModelImp(SignUpPresenterImp callback, ApiClient client) {
        this.callback = (APIClientCallback<ResponseSuccess>) callback;
        this.apiClient = client;
    }

    @Override
    public void signUp(Map<String,String> params) {
        apiClient.userSignUp(callback, params);

    }

}
