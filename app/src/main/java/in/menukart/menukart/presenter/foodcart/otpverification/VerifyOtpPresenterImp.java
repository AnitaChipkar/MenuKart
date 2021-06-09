package in.menukart.menukart.presenter.foodcart.otpverification;


import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.foodcart.VerifyOtp;
import in.menukart.menukart.model.foodcart.otpverification.VerifyOtpModel;
import in.menukart.menukart.model.foodcart.otpverification.VerifyOtpModelImp;
import in.menukart.menukart.view.foodcart.otpverification.VerifyOtpView;

public class VerifyOtpPresenterImp implements VerifyOtpPresenter, APIClientCallback<VerifyOtp> {

    VerifyOtpModel verifyOtpModel;
    VerifyOtpView verifyOtpView;

    public VerifyOtpPresenterImp(VerifyOtpView verifyOtpView, ApiClient client) {
        this.verifyOtpView = verifyOtpView;
        verifyOtpModel = new VerifyOtpModelImp(this, client);

    }

    @Override
    public void onSuccess(VerifyOtp success) {
        verifyOtpView.onSuccess(success);
    }

    @Override
    public void onFailure(Exception e) {
        verifyOtpView.showError(e.getMessage());
    }

    @Override
    public void requestVerifyOtp(String verifyOtp) {
        verifyOtpModel.verifyOtp(verifyOtp);
    }
}
