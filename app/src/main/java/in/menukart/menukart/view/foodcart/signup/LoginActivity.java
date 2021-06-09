package in.menukart.menukart.view.foodcart.signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import java.util.HashMap;
import java.util.Map;

import in.menukart.menukart.R;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.foodcart.SendOtp;
import in.menukart.menukart.presenter.foodcart.otpverification.SendOtpPresenterImp;
import in.menukart.menukart.util.AppConstants;
import in.menukart.menukart.view.foodcart.otpverification.PhoneVerificationActivity;
import in.menukart.menukart.view.foodcart.otpverification.SendOtpView;

public class LoginActivity extends AppCompatActivity implements SendOtpView {

    private AppCompatEditText editLoginMobileNumber;
    private AppCompatButton btnLoginSendCode;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = LoginActivity.this;

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initLoginViews();
    }

    private void initLoginViews() {
        editLoginMobileNumber = findViewById(R.id.et_login_mobile_number);
        btnLoginSendCode = findViewById(R.id.btn_login_send_code);
        btnLoginSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editLoginMobileNumber.getText().toString().isEmpty()) {
                    editLoginMobileNumber.setError("Enter Mobile Number!");
                } else {
                    if (ApiClient.isConnectedToInternet(context)) {
                        callSendOtpAPI();
                    } else {
                        ApiClient.openAlertDialogWithPositive(context, getString(R.string.error_check_network),
                                getString(R.string.dialog_label_ok));
                    }
                }
            }
        });
    }

    private void callSendOtpAPI() {
        ApiClient.showProgressBar(LoginActivity.this);
        try {
            SendOtpPresenterImp sendOtpPresenterImp =
                    new SendOtpPresenterImp(this,
                            new ApiClient(LoginActivity.this));
            Map<String, String> params = new HashMap<String, String>();
            params.put("mobileno", editLoginMobileNumber.getText().toString());
            String string = params.toString();
            Log.d("TAG", string);
            sendOtpPresenterImp.requestSendOtp(params);

        } catch (Exception e) {
            Log.d("TAG", "sendOtp" + e.getMessage());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void showError(String error) {
        ApiClient.hideProgressBar();
        Log.d("TAG", "onMenuList: error");
        Toast.makeText(LoginActivity.this, getString(R.string.error_invalid_response), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onSuccessful(SendOtp sendOtp) {
        ApiClient.hideProgressBar();
        if (sendOtp.getOtp() != null) {
            Toast.makeText(context, "OTP sent successfully!", Toast.LENGTH_SHORT).show();
            Intent intentVerifyOtp = new Intent(LoginActivity.this, PhoneVerificationActivity.class);
            intentVerifyOtp.putExtra(AppConstants.USER_MOBILE_NUMBER, editLoginMobileNumber.getText().toString());
            startActivity(intentVerifyOtp);
            this.finish();
        }

    }
}