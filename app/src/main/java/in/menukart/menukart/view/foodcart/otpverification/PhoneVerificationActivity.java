package in.menukart.menukart.view.foodcart.otpverification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import in.menukart.menukart.R;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.foodcart.ResendOtp;
import in.menukart.menukart.entities.foodcart.VerifyOtp;
import in.menukart.menukart.presenter.foodcart.otpverification.ResendOtpPresenterImp;
import in.menukart.menukart.presenter.foodcart.otpverification.VerifyOtpPresenterImp;
import in.menukart.menukart.util.AppConstants;
import in.menukart.menukart.view.foodcart.signup.SignUpActivity;
import in.menukart.menukart.view.foodcart.trackorder.OrderSummaryActivity;

public class PhoneVerificationActivity extends AppCompatActivity implements VerifyOtpView, ResendOtpView {

    private AppCompatButton btnVerifyOtp;
    private AppCompatEditText editOtp;
    private AppCompatTextView textCodeDetails, textResendCode;
    private Context context;
    private String userMobileNumber, userAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        context = PhoneVerificationActivity.this;
        userMobileNumber = getIntent().getExtras().getString(AppConstants.USER_MOBILE_NUMBER);
        initVerificationViews();
    }

    @SuppressLint("SetTextI18n")
    private void initVerificationViews() {
        btnVerifyOtp = findViewById(R.id.btn_verify_otp);
        editOtp = findViewById(R.id.et_verification_code);
        textCodeDetails = findViewById(R.id.tv_code_details);
        textResendCode = findViewById(R.id.tv_resend_code);
        textCodeDetails.setText("A Verification code is sent to your number " + userMobileNumber);
        btnVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editOtp.getText().toString().isEmpty()) {
                    editOtp.setError("Enter Otp!");
                } else {
                    if (ApiClient.isConnectedToInternet(context)) {
                        getUserDetailsData();
                    } else {
                        ApiClient.openAlertDialogWithPositive(context, getString(R.string.error_check_network),
                                getString(R.string.dialog_label_ok));
                    }
                }

            }
        });
        textResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ApiClient.isConnectedToInternet(context)) {
                    callResendOtpAPI();
                } else {
                    ApiClient.openAlertDialogWithPositive(context, getString(R.string.error_check_network),
                            getString(R.string.dialog_label_ok));
                }
            }
        });
    }

    private void callResendOtpAPI() {
        ApiClient.showProgressBar(context);
        try {
            ResendOtpPresenterImp resendOtpPresenterImp =
                    new ResendOtpPresenterImp(this,
                            new ApiClient(context));
            Map<String, String> params = new HashMap<String, String>();
            params.put("mobileno", userMobileNumber);
            String string = params.toString();
            Log.d("TAG", string);
            resendOtpPresenterImp.requestResendOtp(params);

        } catch (Exception e) {
            Log.d("TAG", "VerifyOtp" + e.getMessage());
        }
    }

    private void getUserDetailsData() {
        ApiClient.showProgressBar(context);
        try {
            VerifyOtpPresenterImp verifyOtpPresenterImp =
                    new VerifyOtpPresenterImp(this,
                            new ApiClient(context));
            // verifyOtpPresenterImp.requestVerifyOtp(editOtp.getText().toString());
            verifyOtpPresenterImp.requestVerifyOtp(userMobileNumber);
        } catch (Exception e) {
            Log.d("TAG", "VerifyOtp" + e.getMessage());
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
        Toast.makeText(context, getString(R.string.error_invalid_response), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onSuccess(VerifyOtp verifyOtp) {
        ApiClient.hideProgressBar();
        SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (verifyOtp.getUserdetails() != null) {
            Intent intentOrderSummary = new Intent(PhoneVerificationActivity.this, OrderSummaryActivity.class);
            startActivity(intentOrderSummary);
            finish();

            Gson gson = new Gson();
            String json = gson.toJson(verifyOtp.getUserdetails());
            editor.putString(AppConstants.USER_DETAILS, json);
            //  editor.putString(AppConstants.USER_ID, verifyOtp.getUserdetails().getUser_id());
            editor.apply();


        } else {
            // editor.clear();
            //editor.apply();
            new iOSDialogBuilder(context)
//                .setTitle(context.getResources().getString(R.string.cricketer))
                    .setSubtitle(getResources().getString(R.string.text_number_not_registered))
                    .setBoldPositiveLabel(false)
                    .setFont(Typeface.SANS_SERIF)
                    .setCancelable(false)
                    .setPositiveListener(context.getString(R.string.btn_text_sign_up), new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {
                            Intent intentSignUp = new Intent(PhoneVerificationActivity.this, SignUpActivity.class);
                            startActivity(intentSignUp);
                            finish();
                        }
                    })
                    .build().show();
        }

    }

    @Override
    public void showErrorResendOtp(String error) {
        ApiClient.hideProgressBar();
        Log.d("TAG", "onMenuList: error");
        Toast.makeText(context, getString(R.string.error_invalid_response), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onSuccessfulResendOtp(ResendOtp resendOtp) {
        ApiClient.hideProgressBar();
        if (resendOtp.getOtp() != null) {
            Toast.makeText(context, "OTP sent successfully!", Toast.LENGTH_SHORT).show();
        }

    }
}