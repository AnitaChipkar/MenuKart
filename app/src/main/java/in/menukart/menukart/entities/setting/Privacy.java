package in.menukart.menukart.entities.setting;

import java.io.Serializable;

public class Privacy implements Serializable {
    private String status;
    private String message;
    private Links links;

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

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }


}