package in.menukart.menukart.view.foodcart.trackorder;


import in.menukart.menukart.entities.foodcart.SaveOrder;

public interface SaveOrderView {
    void showError(String error);

    void onSuccess(SaveOrder saveOrder);
}
