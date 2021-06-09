package in.menukart.menukart.view.explore;


import in.menukart.menukart.entities.explore.RestaurantList;

public interface RestaurantListView {
    void showError(String error);
    void onSuccessfulRestaurantList(RestaurantList restaurantList);
}
