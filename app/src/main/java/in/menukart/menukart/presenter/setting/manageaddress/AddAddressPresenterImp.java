package in.menukart.menukart.presenter.setting.manageaddress;


import java.util.Map;
import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.setting.ResponseSuccess;
import in.menukart.menukart.model.settings.manageaddress.AddAddressModel;
import in.menukart.menukart.model.settings.manageaddress.AddAddressModelImp;
import in.menukart.menukart.view.setting.manageaddress.AddAddressView;


public class AddAddressPresenterImp implements AddAddressPresenter, APIClientCallback<ResponseSuccess> {

    AddAddressModel addAddressModel;
    AddAddressView addAddressView;

    public AddAddressPresenterImp(AddAddressView addAddressView, ApiClient client) {
        this.addAddressView = addAddressView;
        addAddressModel = new AddAddressModelImp(this, client);

    }

    @Override
    public void onSuccess(ResponseSuccess success) {
        addAddressView.onSuccessfulAddAddress(success);
    }

    @Override
    public void onFailure(Exception e) {
        addAddressView.showError(e.getMessage());
    }

    @Override
    public void requestAddAddress(Map<String,String> params) {
        addAddressModel.addAddress(params);
    }
}
