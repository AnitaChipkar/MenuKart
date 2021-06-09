package in.menukart.menukart.presenter.foodcart.otpverification;


import java.util.Map;

import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.foodcart.ResendOtp;
import in.menukart.menukart.model.foodcart.otpverification.ResendOtpModel;
import in.menukart.menukart.model.foodcart.otpverification.ResendOtpModelImp;
import in.menukart.menukart.view.foodcart.otpverification.ResendOtpView;

public class ResendOtpPresenterImp implements ResendOtpPresenter, APIClientCallback<ResendOtp> {

    ResendOtpModel resendOtpModel;
    ResendOtpView resendOtpView;

    public ResendOtpPresenterImp(ResendOtpView resendOtpView, ApiClient client) {
        this.resendOtpView = resendOtpView;
        resendOtpModel = new ResendOtpModelImp(this, client);

    }

    @Override
    public void onSuccess(ResendOtp success) {
        resendOtpView.onSuccessfulResendOtp(success);
    }

    @Override
    public void onFailure(Exception e) {
        resendOtpView.showErrorResendOtp(e.getMessage());
    }

    @Override
    public void requestResendOtp(Map<String, String> params) {
        resendOtpModel.resendOtp(params);
    }
}
