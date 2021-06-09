package in.menukart.menukart.entities.foodcart;

import java.io.Serializable;

public class VerifyOtp implements Serializable {
    private String status;
    private String message;
    private UserDetails userdetails;

    public UserDetails getUserdetails() {
        return userdetails;
    }

    public void setUserdetails(UserDetails userdetails) {
        this.userdetails = userdetails;
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
