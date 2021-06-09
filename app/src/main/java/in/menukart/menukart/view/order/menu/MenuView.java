package in.menukart.menukart.view.order.menu;


import in.menukart.menukart.entities.order.Menu;

public interface MenuView {
    void showError(String error);
    void onSuccessfulMenuList(Menu menu);
}
