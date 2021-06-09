package in.menukart.menukart.entities.foodcart;

import java.io.Serializable;

public class FoodCart implements Serializable {
    private String cartSubTotal;
    private String totalCgst;
    private String totalSgst;
    private String charges;
    private String total;
    private String qty;

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }



    public String getCartSubTotal() {
        return cartSubTotal;
    }

    public void setCartSubTotal(String cartSubTotal) {
        this.cartSubTotal = cartSubTotal;
    }

    public String getTotalCgst() {
        return totalCgst;
    }

    public void setTotalCgst(String totalCgst) {
        this.totalCgst = totalCgst;
    }

    public String getTotalSgst() {
        return totalSgst;
    }

    public void setTotalSgst(String totalSgst) {
        this.totalSgst = totalSgst;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }



}
