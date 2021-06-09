package in.menukart.menukart.entities.order;

import java.io.Serializable;

public class Category implements Serializable {
    private String id;
    private String restaurant_id;
    private String category_name;
    private String category_olname;
    private String category_img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_olname() {
        return category_olname;
    }

    public void setCategory_olname(String category_olname) {
        this.category_olname = category_olname;
    }

    public String getCategory_img() {
        return category_img;
    }

    public void setCategory_img(String category_img) {
        this.category_img = category_img;
    }


}
