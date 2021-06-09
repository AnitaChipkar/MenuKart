package in.menukart.menukart.view.foodcart.signup;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import in.menukart.menukart.R;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.setting.ResponseSuccess;
import in.menukart.menukart.presenter.foodcart.signup.SignUpPresenterImp;

public class SignUpActivity extends AppCompatActivity implements SignUpView {

    private AppCompatButton btnSignUp;
    private AppCompatEditText editFirstName, editLastName,
            editEmail, editMobileNumber, editBirthDate, editPassword;
    private Context context;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        context = SignUpActivity.this;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initSignUpViews();
    }

    private void initSignUpViews() {
        editFirstName = findViewById(R.id.et_sign_up_first_name);
        editLastName = findViewById(R.id.et_sign_up_last_name);
        editEmail = findViewById(R.id.et_sign_up_email);
        editMobileNumber = findViewById(R.id.et_sign_up_mobile_number);
        editBirthDate = findViewById(R.id.et_sign_up_birth_date);
        editPassword = findViewById(R.id.et_sign_up_password);
        btnSignUp = findViewById(R.id.btn_sign_up);
        editBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidations()) {
                    if (ApiClient.isConnectedToInternet(context)) {
                        callUserSignUp();
                    } else {
                        ApiClient.openAlertDialogWithPositive(context, getString(R.string.error_check_network),
                                getString(R.string.dialog_label_ok));
                    }

                }
            }
        });


    }

    private void callUserSignUp() {
        ApiClient.showProgressBar(SignUpActivity.this);
        try {
            SignUpPresenterImp signUpPresenterImp =
                    new SignUpPresenterImp(this,
                            new ApiClient(SignUpActivity.this));
            Map<String, String> params = new HashMap<String, String>();
            params.put("mobileno", editMobileNumber.getText().toString());
            params.put("fname", editFirstName.getText().toString());
            params.put("lname", editLastName.getText().toString());
            params.put("email", editEmail.getText().toString());
            params.put("birthdate", editBirthDate.getText().toString());
            params.put("password", editPassword.getText().toString());

            String string = params.toString();
            Log.d("TAG", string);
            signUpPresenterImp.requestSignUp(params);

        } catch (Exception e) {
            Log.d("TAG", "signUp" + e.getMessage());
        }
    }

    private void setDate() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        editBirthDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private boolean checkValidations() {
        if (editFirstName.getText().toString().isEmpty()) {
            editFirstName.setError("Enter First Name!");
            editLastName.requestFocus();
            return false;
        } else {
            editFirstName.setError(null);
        }
        if (editLastName.getText().toString().isEmpty()) {
            editLastName.setError("Enter Last Name!");
            editLastName.requestFocus();
            return false;
        } else {
            editLastName.setError(null);
        }
        if (editEmail.getText().toString().isEmpty()) {
            editEmail.setError("Enter Email Id!");
            editEmail.requestFocus();
            return false;
        } else {
            editEmail.setError(null);
        }
        if (editMobileNumber.getText().toString().isEmpty()) {
            editMobileNumber.setError("Enter Mobile Number!");
            editMobileNumber.requestFocus();
            return false;
        } else {
            editMobileNumber.setError(null);
        }
        if (editBirthDate.getText().toString().isEmpty()) {
            editBirthDate.setError("Enter Birth Date!");
            editBirthDate.requestFocus();
            return false;
        } else {
            editBirthDate.setError(null);
        }
        if (editPassword.getText().toString().isEmpty()) {
            editPassword.setError("Enter Password!");
            editPassword.requestFocus();
            return false;
        } else {
            editPassword.setError(null);
        }

        return true;
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
        Toast.makeText(SignUpActivity.this, getString(R.string.error_invalid_response), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onSuccessful(ResponseSuccess responseSuccess) {
        ApiClient.hideProgressBar();
        if ("200".equalsIgnoreCase(responseSuccess.getStatus())) {
            Toast.makeText(context, responseSuccess.getMessage(), Toast.LENGTH_SHORT).show();
            Intent intentLogin = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intentLogin);
            this.finish();

        }
    }
}