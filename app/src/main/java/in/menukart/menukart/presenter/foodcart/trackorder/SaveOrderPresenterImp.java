package in.menukart.menukart.presenter.foodcart.trackorder;


import java.util.Map;

import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.foodcart.SaveOrder;
import in.menukart.menukart.model.foodcart.trackorder.SaveOrderModel;
import in.menukart.menukart.model.foodcart.trackorder.SaveOrderModelImp;
import in.menukart.menukart.view.foodcart.trackorder.SaveOrderView;

public class SaveOrderPresenterImp implements SaveOrderPresenter, APIClientCallback<SaveOrder> {

    SaveOrderModel saveOrderModel;
    SaveOrderView saveOrderView;

    public SaveOrderPresenterImp(SaveOrderView saveOrderView, ApiClient client) {
        this.saveOrderView = saveOrderView;
        saveOrderModel = new SaveOrderModelImp(this, client);

    }

    @Override
    public void onSuccess(SaveOrder success) {
        saveOrderView.onSuccess(success);
    }

    @Override
    public void onFailure(Exception e) {
        saveOrderView.showError(e.getMessage());
    }

    @Override
    public void requestSaveOrder(Map<String, String> params) {
        saveOrderModel.saveOrder(params);
    }
}
