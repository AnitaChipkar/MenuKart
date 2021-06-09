package in.menukart.menukart.presenter.foodcart.otpverification;


import java.util.Map;

import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.foodcart.SendOtp;
import in.menukart.menukart.model.foodcart.otpverification.SendOtpModel;
import in.menukart.menukart.model.foodcart.otpverification.SendOtpModelImp;
import in.menukart.menukart.view.foodcart.otpverification.SendOtpView;

public class SendOtpPresenterImp implements SendOtpPresenter, APIClientCallback<SendOtp> {

    SendOtpModel sendOtpModel;
    SendOtpView sendOtpView;

    public SendOtpPresenterImp(SendOtpView sendOtpView, ApiClient client) {
        this.sendOtpView = sendOtpView;
        sendOtpModel = new SendOtpModelImp(this, client);

    }

    @Override
    public void onSuccess(SendOtp success) {
        sendOtpView.onSuccessful(success);
    }

    @Override
    public void onFailure(Exception e) {
        sendOtpView.showError(e.getMessage());
    }

    @Override
    public void requestSendOtp(Map<String, String> params) {
        sendOtpModel.sendOtp(params);
    }
}
