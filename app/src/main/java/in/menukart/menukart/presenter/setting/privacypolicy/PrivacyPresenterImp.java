package in.menukart.menukart.presenter.setting.privacypolicy;


import in.menukart.menukart.api.APIClientCallback;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.setting.Privacy;
import in.menukart.menukart.model.settings.privacypolicy.PrivacyModel;
import in.menukart.menukart.model.settings.privacypolicy.PrivacyModelImp;
import in.menukart.menukart.view.setting.privacypolicy.PrivacyView;

public class PrivacyPresenterImp implements PrivacyPresenter, APIClientCallback<Privacy> {

    PrivacyModel privacyModel;
    PrivacyView privacyView;

    public PrivacyPresenterImp(PrivacyView policyView, ApiClient client) {
        this.privacyView = policyView;
        privacyModel = new PrivacyModelImp(this, client);

    }

    @Override
    public void onSuccess(Privacy success) {
        privacyView.onSuccessfulPrivacyLinks(success);
    }

    @Override
    public void onFailure(Exception e) {
        privacyView.showError(e.getMessage());
    }

    @Override
    public void requestPrivacyLinks() {
        privacyModel.privacyLinks();
    }
}
