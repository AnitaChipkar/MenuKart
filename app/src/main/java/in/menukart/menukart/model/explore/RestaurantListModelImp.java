package in.menukart.menukart.model.explore;

import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.explore.RestaurantList;
import in.menukart.menukart.presenter.explore.RestaurantListPresenterImp;

public class RestaurantListModelImp implements RestaurantListModel {

    private ApiClient apiClient;
    private APIClientCallback<RestaurantList> callback;

    public RestaurantListModelImp(RestaurantListPresenterImp callback, ApiClient client) {
        this.callback = (APIClientCallback<RestaurantList>) callback;
        this.apiClient = client;
    }

    @Override
    public void RestaurantList() {
        apiClient.restaurantList(callback);

    }

}
