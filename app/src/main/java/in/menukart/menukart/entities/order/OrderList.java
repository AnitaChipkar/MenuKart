package in.menukart.menukart.entities.order;

import java.io.Serializable;

public class OrderList implements Serializable {
    private String order_menu_id;
    private String menu_name;
    private String menu_variation;
    private String menu_price;
    private String qty;
    private String menu_logo;


    public String getOrder_menu_id() {
        return order_menu_id;
    }

    public void setOrder_menu_id(String order_menu_id) {
        this.order_menu_id = order_menu_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_variation() {
        return menu_variation;
    }

    public void setMenu_variation(String menu_variation) {
        this.menu_variation = menu_variation;
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

    public String getMenu_logo() {
        return menu_logo;
    }

    public void setMenu_logo(String menu_logo) {
        this.menu_logo = menu_logo;
    }


}
