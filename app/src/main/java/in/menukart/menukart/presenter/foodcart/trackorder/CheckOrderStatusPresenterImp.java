package in.menukart.menukart.presenter.foodcart.trackorder;


import java.util.Map;

import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.setting.ResponseSuccess;
import in.menukart.menukart.model.foodcart.trackorder.CheckOrderStatusModel;
import in.menukart.menukart.model.foodcart.trackorder.CheckOrderStatusModelImp;
import in.menukart.menukart.view.foodcart.trackorder.CheckOrderStatusView;

public class CheckOrderStatusPresenterImp implements CheckOrderStatusPresenter, APIClientCallback<ResponseSuccess> {

    CheckOrderStatusModel checkOrderStatusModel;
    CheckOrderStatusView checkOrderStatusView;

    public CheckOrderStatusPresenterImp(CheckOrderStatusView checkOrderStatusView, ApiClient client) {
        this.checkOrderStatusView = checkOrderStatusView;
        checkOrderStatusModel = new CheckOrderStatusModelImp(this, client);

    }

    @Override
    public void onSuccess(ResponseSuccess success) {
        checkOrderStatusView.onSuccessful(success);
    }

    @Override
    public void onFailure(Exception e) {
        checkOrderStatusView.showError(e.getMessage());
    }

    @Override
    public void requestCheckOrderStatus(Map<String, String> params) {
        checkOrderStatusModel.checkOrderStatus(params);
    }
}
