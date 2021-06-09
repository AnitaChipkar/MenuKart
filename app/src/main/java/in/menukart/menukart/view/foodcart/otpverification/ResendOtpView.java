package in.menukart.menukart.view.foodcart.otpverification;


import in.menukart.menukart.entities.foodcart.ResendOtp;

public interface ResendOtpView {
    void showErrorResendOtp(String error);

    void onSuccessfulResendOtp(ResendOtp resendOtp);
}
