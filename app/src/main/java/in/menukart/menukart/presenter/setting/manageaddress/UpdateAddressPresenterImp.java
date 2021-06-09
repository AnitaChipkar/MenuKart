package in.menukart.menukart.presenter.setting.manageaddress;


import java.util.Map;

import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.setting.ResponseSuccess;
import in.menukart.menukart.model.settings.manageaddress.UpdateAddressModel;
import in.menukart.menukart.model.settings.manageaddress.UpdateAddressModelImp;
import in.menukart.menukart.view.setting.manageaddress.UpdateAddressView;

public class UpdateAddressPresenterImp implements UpdateAddressPresenter, APIClientCallback<ResponseSuccess> {

    UpdateAddressModel updateAddressModel;
    UpdateAddressView updateAddressView;

    public UpdateAddressPresenterImp(UpdateAddressView addAddressView, ApiClient client) {
        this.updateAddressView = addAddressView;
        updateAddressModel = new UpdateAddressModelImp(this, client);

    }

    @Override
    public void onSuccess(ResponseSuccess success) {
        updateAddressView.onSuccessfulUpdateAddress(success);
    }

    @Override
    public void onFailure(Exception e) {
        updateAddressView.showUpdateAddressError(e.getMessage());
    }

    @Override
    public void requestUpdateAddress(Map<String,String> params) {
        updateAddressModel.updateAddress(params);
    }
}
