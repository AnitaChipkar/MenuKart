package in.menukart.menukart.view.setting.userprofile;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import in.menukart.menukart.R;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.foodcart.UserDetails;
import in.menukart.menukart.entities.setting.ResponseSuccess;
import in.menukart.menukart.presenter.setting.userprofile.UpdateProfilePresenterImp;
import in.menukart.menukart.util.AppConstants;
import in.menukart.menukart.view.other.MainActivity;

public class UpdateProfileActivity extends AppCompatActivity implements UpdateProfileView {

    AppCompatEditText editProfileFirstName, editProfileLastName,
            editProfileEmail, editProfileMobileNumber, editProfileBirthDate;
    UserDetails userDetails;
    SharedPreferences sharedPreferences;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initUpdateProfileViews();
    }

    private void initUpdateProfileViews() {
        sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(AppConstants.USER_DETAILS, null);
        userDetails = gson.fromJson(json, UserDetails.class);


        editProfileFirstName = findViewById(R.id.et_update_profile_first_name);
        editProfileLastName = findViewById(R.id.et_update_profile_last_name);
        editProfileEmail = findViewById(R.id.et_update_profile_email);
        editProfileMobileNumber = findViewById(R.id.et_update_profile_mobile_number);
        editProfileBirthDate = findViewById(R.id.et_update_profile_birth_date);
        editProfileBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate();
            }
        });

        setUserData();

        findViewById(R.id.btn_save_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ApiClient.isConnectedToInternet(UpdateProfileActivity.this)) {
                    callUpdateUserProfile();
                } else {
                    ApiClient.openAlertDialogWithPositive(UpdateProfileActivity.this, getString(R.string.error_check_network),
                            getString(R.string.dialog_label_ok));
                }
            }
        });


    }

    private void setUserData() {
        if (userDetails != null) {
            editProfileFirstName.setText(userDetails.getFname());
            editProfileLastName.setText(userDetails.getLname());
            editProfileEmail.setText(userDetails.getEmail());
            editProfileMobileNumber.setText(userDetails.getMobileno());
            editProfileBirthDate.setText(userDetails.getBirthdate());
        }

    }

    private void callUpdateUserProfile() {
        ApiClient.showProgressBar(UpdateProfileActivity.this);
        try {
            UpdateProfilePresenterImp updateProfilePresenterImp =
                    new UpdateProfilePresenterImp(this,
                            new ApiClient(UpdateProfileActivity.this));
            Map<String, String> params = new HashMap<String, String>();
            params.put("user_id", userDetails.getUser_id());
            params.put("fname", editProfileFirstName.getText().toString());
            params.put("lname", editProfileLastName.getText().toString());
            params.put("email", editProfileEmail.getText().toString());
            params.put("mobileno", editProfileMobileNumber.getText().toString());
            params.put("birthdate", editProfileBirthDate.getText().toString());
            params.put("profileImage", "");

            String string = params.toString();
            Log.d("TAG", string);
            updateProfilePresenterImp.requestUpdateProfile(params);

        } catch (Exception e) {
            Log.d("TAG", "MenuList" + e.getMessage());
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

                        editProfileBirthDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
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

    @Override
    public void showError(String error) {
        ApiClient.hideProgressBar();
        Log.d("TAG", "onMenuList: error");
        Toast.makeText(UpdateProfileActivity.this, getString(R.string.error_invalid_response), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onSuccessful(ResponseSuccess responseSuccess) {
        ApiClient.hideProgressBar();
        if ("200".equalsIgnoreCase(responseSuccess.getStatus())) {
            Toast.makeText(UpdateProfileActivity.this, "Profile updated Successfully!", Toast.LENGTH_SHORT).show();
            Intent intentAdd = new Intent(UpdateProfileActivity.this, MainActivity.class);
            startActivity(intentAdd);
            finish();

            // Update Data in Local storage also
            userDetails.setUser_id(userDetails.getUser_id());
            userDetails.setFname(editProfileFirstName.getText().toString());
            userDetails.setLname(editProfileLastName.getText().toString());
            userDetails.setEmail(editProfileEmail.getText().toString());
            userDetails.setMobileno(editProfileMobileNumber.getText().toString());
            userDetails.setBirthdate(editProfileBirthDate.getText().toString());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(userDetails);
            editor.putString(AppConstants.USER_DETAILS, json);
          //  editor.putString(AppConstants.USER_ID, userDetails.getUser_id());
            editor.apply();


        }
    }
}