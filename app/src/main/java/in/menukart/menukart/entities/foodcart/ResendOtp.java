package in.menukart.menukart.entities.foodcart;

import java.io.Serializable;

public class ResendOtp implements Serializable {
    private String status;
    private String message;
    private String otp;

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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }


}
