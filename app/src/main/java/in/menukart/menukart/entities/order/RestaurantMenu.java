package in.menukart.menukart.entities.order;

import java.io.Serializable;
import java.util.List;

public class RestaurantMenu implements Serializable {
    private String menu_id;
    private String restaurant_id;
    private String menu_logo;
    private String menu_status;
    private String menu_foodtype;
    private String menu_name;
    private String menu_displayname;
    private String menu_price;
    private String menu_categoryid;
    private String menu_shortcode;
    private List<Variation> variation;
    private String menu_description;

    public String getMenu_foodtype() {
        return menu_foodtype;
    }

    public void setMenu_foodtype(String menu_foodtype) {
        this.menu_foodtype = menu_foodtype;
    }




    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getMenu_logo() {
        return menu_logo;
    }

    public void setMenu_logo(String menu_logo) {
        this.menu_logo = menu_logo;
    }

    public String getMenu_status() {
        return menu_status;
    }

    public void setMenu_status(String menu_status) {
        this.menu_status = menu_status;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_displayname() {
        return menu_displayname;
    }

    public void setMenu_displayname(String menu_displayname) {
        this.menu_displayname = menu_displayname;
    }

    public String getMenu_price() {
        return menu_price;
    }

    public void setMenu_price(String menu_price) {
        this.menu_price = menu_price;
    }

    public String getMenu_categoryid() {
        return menu_categoryid;
    }

    public void setMenu_categoryid(String menu_categoryid) {
        this.menu_categoryid = menu_categoryid;
    }

    public String getMenu_shortcode() {
        return menu_shortcode;
    }

    public void setMenu_shortcode(String menu_shortcode) {
        this.menu_shortcode = menu_shortcode;
    }

    public List<Variation> getVariation() {
        return variation;
    }

    public void setVariation(List<Variation> variation) {
        this.variation = variation;
    }

    public String getMenu_description() {
        return menu_description;
    }

    public void setMenu_description(String menu_description) {
        this.menu_description = menu_description;

    }


}
