package in.menukart.menukart.view.setting.privacypolicy;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

import in.menukart.menukart.R;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.setting.Privacy;
import in.menukart.menukart.presenter.setting.privacypolicy.PrivacyPresenterImp;
import in.menukart.menukart.util.AppConstants;

public class PrivacyActivity extends AppCompatActivity implements PrivacyView {
    WebView webViewPolicy;
    PrivacyPresenterImp privacyPresenterImp;
    private String TAG = "PrivacyActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initPrivacyViews();

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initPrivacyViews() {
        webViewPolicy = findViewById(R.id.web_view_policy);
        webViewPolicy.getSettings().setJavaScriptEnabled(true);
        //This will zoom out the WebView
        webViewPolicy.getSettings().setUseWideViewPort(true);
        webViewPolicy.getSettings().setLoadWithOverviewMode(true);
        webViewPolicy.getSettings().setSupportZoom(true);


        privacyPresenterImp = new PrivacyPresenterImp(this, new ApiClient(this));
        if (ApiClient.isConnectedToInternet(this)) {
            getListOfWebLinks();
        } else {
            ApiClient.openAlertDialogWithPositive(this, getString(R.string.error_check_network),
                    getString(R.string.dialog_label_ok));
        }
    }

    private void getListOfWebLinks() {
        ApiClient.showProgressBar(this);
        try {
            privacyPresenterImp.requestPrivacyLinks();
        } catch (Exception e) {
            Log.d(TAG, "PrivacyLinks" + e.getMessage());
        }
    }

    @Override
    public void showError(String error) {
        ApiClient.hideProgressBar();
        Log.d(TAG, "onMenuList: error");
        Toast.makeText(PrivacyActivity.this, getString(R.string.error_invalid_response), Toast.LENGTH_LONG).show();


    }

    @Override
    public void onSuccessfulPrivacyLinks(Privacy privacy) {
        ApiClient.hideProgressBar();
        if (privacy.getLinks() != null) {

            webViewPolicy.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    ApiClient.showProgressBar(PrivacyActivity.this);
                    view.loadUrl(url);

                    return true;
                }

                @Override
                public void onPageFinished(WebView view, final String url) {
                    ApiClient.hideProgressBar();
                }
            });
            try {
                if (Objects.requireNonNull(getIntent().getExtras()).
                        getString(AppConstants.TERMS_CONDITIONS) != null) {

                    webViewPolicy.loadUrl("http://docs.google.com/gview?embedded=true&url=" +
                            URLEncoder.encode(privacy.getLinks().getTerms_and_conditions(), "ISO-8859-1"));

               /* webViewPolicy.loadUrl("http://docs.google.com/gview?embedded=true&url=" +
                        privacy.getLinks().getTerms_and_conditions());*/
                } else if (Objects.requireNonNull(getIntent().getExtras()).
                        getString(AppConstants.PRIVACY_POLICY) != null) {

                    webViewPolicy.loadUrl("http://docs.google.com/gview?embedded=true&url=" +
                            privacy.getLinks().getPrivacy_policy());
                } else if (Objects.requireNonNull(getIntent().getExtras()).
                        getString(AppConstants.CANCELLATION_POLICY) != null) {
                    webViewPolicy.loadUrl("http://docs.google.com/gview?embedded=true&url=" +
                            privacy.getLinks().getCancellation_and_refund());
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}

