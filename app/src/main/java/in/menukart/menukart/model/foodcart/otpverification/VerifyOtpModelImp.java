package in.menukart.menukart.model.foodcart.otpverification;

import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.foodcart.VerifyOtp;
import in.menukart.menukart.presenter.foodcart.otpverification.VerifyOtpPresenterImp;

public class VerifyOtpModelImp implements VerifyOtpModel {

    private ApiClient apiClient;
    private APIClientCallback<VerifyOtp> callback;

    public VerifyOtpModelImp(VerifyOtpPresenterImp callback, ApiClient client) {
        this.callback = (APIClientCallback<VerifyOtp>) callback;
        this.apiClient = client;
    }

    @Override
    public void verifyOtp(String verifyOtp) {
        apiClient.userVerifyOtp(callback, verifyOtp);

    }

}
