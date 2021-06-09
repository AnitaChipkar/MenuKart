package in.menukart.menukart.entities.foodcart;

import java.io.Serializable;
import java.util.List;

import in.menukart.menukart.entities.order.Variation;

public class SaveRestaurantMenu implements Serializable {
    private String menu_id;
    private String restaurant_id;
    private String menu_name;
    private String menu_price;
    private String qty;
    private String cgst;
    private String sgst;
    private List<Variation> variation;

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

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_price() {
        return menu_price;
    }

    public void setMenu_price(String menu_price) {
        this.menu_price = menu_price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getCgst() {
        return cgst;
    }

    public void setCgst(String cgst) {
        this.cgst = cgst;
    }

    public String getSgst() {
        return sgst;
    }

    public void setSgst(String sgst) {
        this.sgst = sgst;
    }

    public List<Variation> getVariation() {
        return variation;
    }

    public void setVariation(List<Variation> variation) {
        this.variation = variation;
    }


}
