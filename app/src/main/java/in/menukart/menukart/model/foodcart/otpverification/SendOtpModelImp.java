package in.menukart.menukart.model.foodcart.otpverification;

import java.util.Map;

import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.foodcart.SendOtp;
import in.menukart.menukart.presenter.foodcart.otpverification.SendOtpPresenterImp;

public class SendOtpModelImp implements SendOtpModel {

    private ApiClient apiClient;
    private APIClientCallback<SendOtp> callback;

    public SendOtpModelImp(SendOtpPresenterImp callback, ApiClient client) {
        this.callback = (APIClientCallback<SendOtp>) callback;
        this.apiClient = client;
    }

    @Override
    public void sendOtp(Map<String,String> params) {
        apiClient.userSendOtp(callback, params);

    }

}
