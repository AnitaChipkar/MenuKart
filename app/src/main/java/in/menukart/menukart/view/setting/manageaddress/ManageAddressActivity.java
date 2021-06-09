package in.menukart.menukart.view.setting.manageaddress;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import in.menukart.menukart.R;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.foodcart.UserDetails;
import in.menukart.menukart.entities.setting.ManageAddress;
import in.menukart.menukart.presenter.setting.manageaddress.ManageAddressPresenterImp;
import in.menukart.menukart.util.AppConstants;

public class ManageAddressActivity extends AppCompatActivity implements ManageAddressView {
    RecyclerView recyclerViewAddress;
    private String TAG = "ManageAddressActivity";
    private Context context;
    private FloatingActionButton fbAddAddress;
    AppCompatTextView textNoAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initAddressViews();


    }

    private void initAddressViews() {
        context = ManageAddressActivity.this;
        textNoAddress = findViewById(R.id.tv_no_user_address);
        fbAddAddress = findViewById(R.id.fb_add_user_address);
        recyclerViewAddress = findViewById(R.id.rcv_manage_address);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewAddress.setLayoutManager(layoutManager);
        recyclerViewAddress.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewAddress.getContext(),
                layoutManager.getOrientation());
        recyclerViewAddress.addItemDecoration(dividerItemDecoration);

        if (ApiClient.isConnectedToInternet(context)) {
            getListOfUserAddress();
        } else {
            ApiClient.openAlertDialogWithPositive(context, getString(R.string.error_check_network),
                    getString(R.string.dialog_label_ok));
        }
        fbAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMap = new Intent(ManageAddressActivity.this, MapsActivity.class);
                startActivity(intentMap);
                finish();

            }
        });
    }

    private void getListOfUserAddress() {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(AppConstants.USER_DETAILS, null);
        UserDetails userDetails = gson.fromJson(json, UserDetails.class);

        ApiClient.showProgressBar(context);
        // restaurantId = sharedPreferences.getString(AppConstants.RESTAURANT_ID, null);
        try {
            ManageAddressPresenterImp manageAddressPresenterImp =
                    new ManageAddressPresenterImp(this,
                            new ApiClient(context));
            manageAddressPresenterImp.requestUserAddress(userDetails.getUser_id());
        } catch (Exception e) {
            Log.d(TAG, "MenuList" + e.getMessage());
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

    @Override
    public void showError(String error) {
        ApiClient.hideProgressBar();
        Log.d(TAG, "onMenuList: error");
        Toast.makeText(ManageAddressActivity.this, getString(R.string.error_invalid_response), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onSuccessfulPrivacyLinks(ManageAddress manageAddress) {
        ApiClient.hideProgressBar();
        if (manageAddress.getAddress() != null) {
            textNoAddress.setVisibility(View.GONE);
            ManageAddressAdapter manageAddressAdapter = new ManageAddressAdapter(ManageAddressActivity.this, manageAddress.getAddress());
            recyclerViewAddress.setAdapter(manageAddressAdapter);
            manageAddressAdapter.notifyDataSetChanged();
        }
        else
        {
            textNoAddress.setVisibility(View.VISIBLE);
        }

    }
}