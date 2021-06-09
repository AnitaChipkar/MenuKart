package in.menukart.menukart.presenter.explore;


import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.explore.RestaurantList;
import in.menukart.menukart.model.explore.RestaurantListModel;
import in.menukart.menukart.model.explore.RestaurantListModelImp;
import in.menukart.menukart.view.explore.RestaurantListView;

public class RestaurantListPresenterImp implements RestaurantListPresenter, APIClientCallback<RestaurantList> {

    RestaurantListModel restaurantListModel;
    RestaurantListView restaurantListView;

    public RestaurantListPresenterImp(RestaurantListView restaurantListView, ApiClient client) {
        this.restaurantListView = restaurantListView;
        restaurantListModel = new RestaurantListModelImp(this, client);

    }

    @Override
    public void onSuccess(RestaurantList success) {
        restaurantListView.onSuccessfulRestaurantList(success);
    }

    @Override
    public void onFailure(Exception e) {
        restaurantListView.showError(e.getMessage());
    }

    @Override
    public void requestRestaurantList() {
        restaurantListModel.RestaurantList();
    }
}
