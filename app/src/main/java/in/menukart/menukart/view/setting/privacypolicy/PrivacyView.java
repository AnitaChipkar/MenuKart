package in.menukart.menukart.view.setting.privacypolicy;


import in.menukart.menukart.entities.setting.Privacy;

public interface PrivacyView {
    void showError(String error);
    void onSuccessfulPrivacyLinks(Privacy policy);
}
