package in.menukart.menukart.presenter.order.menu;


import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.order.Menu;
import in.menukart.menukart.model.order.menu.MenuModel;
import in.menukart.menukart.model.order.menu.MenuModelImp;
import in.menukart.menukart.view.order.menu.MenuView;

public class MenuPresenterImp implements MenuPresenter, APIClientCallback<Menu> {

    MenuModel menuModel;
    MenuView menuView;

    public MenuPresenterImp(MenuView menuView, ApiClient client) {
        this.menuView = menuView;
        menuModel = new MenuModelImp(this, client);

    }

    @Override
    public void onSuccess(Menu success) {
        menuView.onSuccessfulMenuList(success);
    }

    @Override
    public void onFailure(Exception e) {
        menuView.showError(e.getMessage());
    }

    @Override
    public void requestMenuList(String menuListJson) {
        menuModel.MenuList(menuListJson);
    }
}
