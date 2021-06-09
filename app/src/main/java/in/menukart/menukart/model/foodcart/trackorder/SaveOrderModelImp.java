package in.menukart.menukart.model.foodcart.trackorder;

import java.util.Map;

import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.foodcart.SaveOrder;
import in.menukart.menukart.presenter.foodcart.trackorder.SaveOrderPresenterImp;

public class SaveOrderModelImp implements SaveOrderModel {

    private ApiClient apiClient;
    private APIClientCallback<SaveOrder> callback;

    public SaveOrderModelImp(SaveOrderPresenterImp callback, ApiClient client) {
        this.callback = (APIClientCallback<SaveOrder>) callback;
        this.apiClient = client;
    }

    @Override
    public void saveOrder(Map<String, String> params) {
        apiClient.userSaveOrder(callback, params);

    }

}
