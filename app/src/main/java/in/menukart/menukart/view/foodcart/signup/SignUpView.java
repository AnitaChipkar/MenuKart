package in.menukart.menukart.view.foodcart.signup;


import in.menukart.menukart.entities.setting.ResponseSuccess;

public interface SignUpView {
    void showError(String error);

    void onSuccessful(ResponseSuccess responseSuccess);
}
