package in.menukart.menukart.presenter.setting.userprofile;


import java.util.Map;

import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.setting.ResponseSuccess;
import in.menukart.menukart.model.settings.userprofile.UpdateProfileModel;
import in.menukart.menukart.model.settings.userprofile.UpdateProfileModelImp;
import in.menukart.menukart.view.setting.userprofile.UpdateProfileView;

public class UpdateProfilePresenterImp implements UpdateProfilePresenter, APIClientCallback<ResponseSuccess> {

    UpdateProfileModel updateProfileModel;
    UpdateProfileView updateProfileView;

    public UpdateProfilePresenterImp(UpdateProfileView addAddressView, ApiClient client) {
        this.updateProfileView = addAddressView;
        updateProfileModel = new UpdateProfileModelImp(this, client);

    }

    @Override
    public void onSuccess(ResponseSuccess success) {
        updateProfileView.onSuccessful(success);
    }

    @Override
    public void onFailure(Exception e) {
        updateProfileView.showError(e.getMessage());
    }

    @Override
    public void requestUpdateProfile(Map<String, String> params) {
        updateProfileModel.updateProfile(params);
    }
}
