package in.menukart.menukart.model.foodcart.otpverification;

import java.util.Map;

import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.foodcart.ResendOtp;
import in.menukart.menukart.presenter.foodcart.otpverification.ResendOtpPresenterImp;

public class ResendOtpModelImp implements ResendOtpModel {

    private ApiClient apiClient;
    private APIClientCallback<ResendOtp> callback;

    public ResendOtpModelImp(ResendOtpPresenterImp callback, ApiClient client) {
        this.callback = (APIClientCallback<ResendOtp>) callback;
        this.apiClient = client;
    }

    @Override
    public void resendOtp(Map<String, String> params) {
        apiClient.userResendOtp(callback, params);

    }

}
