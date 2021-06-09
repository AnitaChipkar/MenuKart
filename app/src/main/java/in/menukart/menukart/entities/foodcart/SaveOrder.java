package in.menukart.menukart.entities.foodcart;

import java.io.Serializable;

public class SaveOrder implements Serializable {
    private String status;
    private String message;
    private String order_id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

}