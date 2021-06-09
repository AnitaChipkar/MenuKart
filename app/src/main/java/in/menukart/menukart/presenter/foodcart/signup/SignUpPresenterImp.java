package in.menukart.menukart.presenter.foodcart.signup;


import java.util.Map;

import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.setting.ResponseSuccess;
import in.menukart.menukart.model.foodcart.signup.SignUpModel;
import in.menukart.menukart.model.foodcart.signup.SignUpModelImp;
import in.menukart.menukart.view.foodcart.signup.SignUpView;

public class SignUpPresenterImp implements SignUpPresenter, APIClientCallback<ResponseSuccess> {

    SignUpModel signUpModel;
    SignUpView signUpView;

    public SignUpPresenterImp(SignUpView signUpView, ApiClient client) {
        this.signUpView = signUpView;
        signUpModel = new SignUpModelImp(this, client);

    }

    @Override
    public void onSuccess(ResponseSuccess success) {
        signUpView.onSuccessful(success);
    }

    @Override
    public void onFailure(Exception e) {
        signUpView.showError(e.getMessage());
    }

    @Override
    public void requestSignUp(Map<String, String> params) {
        signUpModel.signUp(params);
    }
}
