package in.menukart.menukart.view.foodcart.otpverification;


import in.menukart.menukart.entities.foodcart.SendOtp;
import in.menukart.menukart.entities.setting.ResponseSuccess;

public interface SendOtpView {
    void showError(String error);
    void onSuccessful(SendOtp sendOtp);
}
