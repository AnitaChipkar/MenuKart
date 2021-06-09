package in.menukart.menukart.model.order.menu;

import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.order.Menu;
import in.menukart.menukart.presenter.order.menu.MenuPresenterImp;

public class MenuModelImp implements MenuModel {

    private ApiClient apiClient;
    private APIClientCallback<Menu> callback;

    public MenuModelImp(MenuPresenterImp callback, ApiClient client) {
        this.callback = (APIClientCallback<Menu>) callback;
        this.apiClient = client;
    }

    @Override
    public void MenuList(String menuListJson) {
        apiClient.menuList(callback, menuListJson);

    }

}
