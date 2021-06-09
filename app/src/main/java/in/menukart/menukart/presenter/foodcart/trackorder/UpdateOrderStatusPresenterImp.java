package in.menukart.menukart.presenter.foodcart.trackorder;


import java.util.Map;

import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.setting.ResponseSuccess;
import in.menukart.menukart.model.foodcart.trackorder.UpdateOrderStatusModel;
import in.menukart.menukart.model.foodcart.trackorder.UpdateOrderStatusModelImp;
import in.menukart.menukart.view.foodcart.trackorder.UpdateOrderStatusView;

public class UpdateOrderStatusPresenterImp implements UpdateOrderStatusPresenter, APIClientCallback<ResponseSuccess> {

    UpdateOrderStatusModel updateOrderStatusModel;
    UpdateOrderStatusView updateOrderStatusView;

    public UpdateOrderStatusPresenterImp(UpdateOrderStatusView updateOrderStatusView, ApiClient client) {
        this.updateOrderStatusView = updateOrderStatusView;
        updateOrderStatusModel = new UpdateOrderStatusModelImp(this, client);

    }

    @Override
    public void onSuccess(ResponseSuccess success) {
        updateOrderStatusView.onUpdateOrderStatusSuccessful(success);
    }

    @Override
    public void onFailure(Exception e) {
        updateOrderStatusView.showErrorUpdateOrderStatus(e.getMessage());
    }

    @Override
    public void requestUpdateOrderStatus(Map<String, String> params) {
        updateOrderStatusModel.updateOrderStatus(params);
    }
}
