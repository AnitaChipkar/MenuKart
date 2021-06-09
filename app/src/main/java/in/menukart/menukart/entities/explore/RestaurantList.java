package in.menukart.menukart.entities.explore;

import java.io.Serializable;
import java.util.List;

public class RestaurantList implements Serializable {
    private String status;
    private String message;
    private List<Restaurant> list;

    public List<Restaurant> getList() {
        return list;
    }

    public void setList(List<Restaurant> list) {
        this.list = list;
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
