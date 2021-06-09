package in.menukart.menukart.model.foodcart.trackorder;

import java.util.Map;

import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.setting.ResponseSuccess;
import in.menukart.menukart.presenter.foodcart.trackorder.CheckOrderStatusPresenterImp;

public class CheckOrderStatusModelImp implements CheckOrderStatusModel {

    private ApiClient apiClient;
    private APIClientCallback<ResponseSuccess> callback;

    public CheckOrderStatusModelImp(CheckOrderStatusPresenterImp callback, ApiClient client) {
        this.callback = (APIClientCallback<ResponseSuccess>) callback;
        this.apiClient = client;
    }

    @Override
    public void checkOrderStatus(Map<String,String> params) {
        apiClient.userCheckOrderStatus(callback, params);

    }

}
