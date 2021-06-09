package in.menukart.menukart.entities.order;

import java.io.Serializable;
import java.util.List;

import in.menukart.menukart.entities.explore.Restaurant;


public class MenuData implements Serializable {
    private Restaurant restaurant;
    private List<RestaurantMenu> menus;
    private List<Category> categories;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<RestaurantMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<RestaurantMenu> menus) {
        this.menus = menus;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }


}
