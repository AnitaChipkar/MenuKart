package in.menukart.menukart.model.settings.privacypolicy;

import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.setting.Privacy;
import in.menukart.menukart.presenter.setting.privacypolicy.PrivacyPresenterImp;

public class PrivacyModelImp implements PrivacyModel {

    private ApiClient apiClient;
    private APIClientCallback<Privacy> callback;

    public PrivacyModelImp(PrivacyPresenterImp callback, ApiClient client) {
        this.callback = (APIClientCallback<Privacy>) callback;
        this.apiClient = client;
    }

    @Override
    public void privacyLinks() {
        apiClient.privacyLinks(callback);

    }

}
