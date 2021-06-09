package in.menukart.menukart.view.foodcart.otpverification;


import in.menukart.menukart.entities.foodcart.VerifyOtp;

public interface VerifyOtpView {
    void showError(String error);

    void onSuccess(VerifyOtp verifyOtp);
}
