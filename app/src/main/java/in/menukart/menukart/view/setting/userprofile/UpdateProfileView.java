package in.menukart.menukart.view.setting.userprofile;


import in.menukart.menukart.entities.setting.ResponseSuccess;

public interface UpdateProfileView {
    void showError(String error);
    void onSuccessful(ResponseSuccess responseSuccess);
}
