package in.menukart.menukart.entities.order;

import java.io.Serializable;


public class Menu implements Serializable {
    private String status;
    private String message;
    private MenuData data;


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

    public MenuData getData() {
        return data;
    }

    public void setData(MenuData data) {
        this.data = data;
    }





}
