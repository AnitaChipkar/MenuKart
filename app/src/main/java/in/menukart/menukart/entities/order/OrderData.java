package in.menukart.menukart.entities.order;

import java.io.Serializable;
import java.util.List;


public class OrderData implements Serializable {
    private String menu_id;
    private String subtotal;
    private String charges;
    private String sgst;
    private String cgst;
    private String totalall;
    private String payment_type;
    private String transaction_id;
    private String easepayid;
    private String transaction_date;
    private String created_at;
    private String to_delivery_address;
    private String to_latitude;
    private String to_longitude;
    private String from_address;
    private String from_latitude;
    private String from_longitude;
    private String restaurant_name;
    private String restaurant_logo;
    private String order_status;
    private List<OrderList> menus;


    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getSgst() {
        return sgst;
    }

    public void setSgst(String sgst) {
        this.sgst = sgst;
    }

    public String getCgst() {
        return cgst;
    }

    public void setCgst(String cgst) {
        this.cgst = cgst;
    }

    public String getTotalall() {
        return totalall;
    }

    public void setTotalall(String totalall) {
        this.totalall = totalall;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getEasepayid() {
        return easepayid;
    }

    public void setEasepayid(String easepayid) {
        this.easepayid = easepayid;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTo_delivery_address() {
        return to_delivery_address;
    }

    public void setTo_delivery_address(String to_delivery_address) {
        this.to_delivery_address = to_delivery_address;
    }

    public String getTo_latitude() {
        return to_latitude;
    }

    public void setTo_latitude(String to_latitude) {
        this.to_latitude = to_latitude;
    }

    public String getTo_longitude() {
        return to_longitude;
    }

    public void setTo_longitude(String to_longitude) {
        this.to_longitude = to_longitude;
    }

    public String getFrom_address() {
        return from_address;
    }

    public void setFrom_address(String from_address) {
        this.from_address = from_address;
    }

    public String getFrom_latitude() {
        return from_latitude;
    }

    public void setFrom_latitude(String from_latitude) {
        this.from_latitude = from_latitude;
    }

    public String getFrom_longitude() {
        return from_longitude;
    }

    public void setFrom_longitude(String from_longitude) {
        this.from_longitude = from_longitude;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getRestaurant_logo() {
        return restaurant_logo;
    }

    public void setRestaurant_logo(String restaurant_logo) {
        this.restaurant_logo = restaurant_logo;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public List<OrderList> getMenus() {
        return menus;
    }

    public void setMenus(List<OrderList> menus) {
        this.menus = menus;
    }


}
