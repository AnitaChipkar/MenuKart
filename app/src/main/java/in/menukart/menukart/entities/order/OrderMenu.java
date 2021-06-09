package in.menukart.menukart.entities.order;

import java.util.List;

public class OrderMenu {
    private String status;
    private String message;
    private List<OrderData> data;

    public List<OrderData> getData() {
        return data;
    }

    public void setData(List<OrderData> data) {
        this.data = data;
    }


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


}
