package in.menukart.menukart.view.other;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import in.menukart.menukart.R;
import in.menukart.menukart.entities.foodcart.UserDetails;
import in.menukart.menukart.util.AppConstants;
import in.menukart.menukart.view.explore.ExploreFragment;
import in.menukart.menukart.view.order.orderlist.OrdersFragment;
import in.menukart.menukart.view.setting.SettingsFragment;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 101;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbarHome;
    LinearLayout linearLayoutToolbar, linearLayoutOther;
    AppCompatTextView toolbarTitle, tvLocationName;
    SharedPreferences sharedPreferences;
    UserDetails userDetails;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setCustomToolbar();
        initHomeViews();


    }

    private void setCustomToolbar() {
        toolbarHome = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbarHome);
        toolbarTitle = toolbarHome.findViewById(R.id.tv_toolbar_title);
        tvLocationName = toolbarHome.findViewById(R.id.tv_location_name);
        sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(AppConstants.USER_DETAILS, null);
        userDetails = gson.fromJson(json, UserDetails.class);
        linearLayoutToolbar = toolbarHome.findViewById(R.id.ll_toolbar_explore);
        linearLayoutOther = toolbarHome.findViewById(R.id.ll_toolbar_other);
    }

    private void initHomeViews() {
        context = MainActivity.this;

        bottomNavigationView = findViewById(R.id.navigation);
        setBottomNavigationMenus();
        Fragment fragment = new ExploreFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
        //   bottomNavigationView.setSelectedItemId(R.id.menu_explore);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();


    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    //Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    getUserCurrentAddress(currentLocation.getLatitude(),currentLocation.getLongitude());
                }
            }
        });
    }

    private void setBottomNavigationMenus() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.menu_explore:
                        fragment = new ExploreFragment();
                        loadFragment(fragment);
                        linearLayoutToolbar.setVisibility(View.VISIBLE);
                        linearLayoutOther.setVisibility(View.GONE);
                        //Toast.makeText(context, "Explore Clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    //break;
                    case R.id.menu_orders:
                        fragment = new OrdersFragment();
                        loadFragment(fragment);
                        toolbarTitle.setText("Order History");
                        linearLayoutToolbar.setVisibility(View.GONE);
                        linearLayoutOther.setVisibility(View.VISIBLE);
                        // Toast.makeText(context, "Orders Clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_settings:
                        fragment = new SettingsFragment();
                        loadFragment(fragment);
                        toolbarTitle.setText("Settings");
                        linearLayoutToolbar.setVisibility(View.GONE);
                        linearLayoutOther.setVisibility(View.VISIBLE);

                        return true;
                    default:
                }
                return false;
            }
        });


    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void getUserCurrentAddress(double latitude,double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            // Toast.makeText(MapsActivity.this,address,Toast.LENGTH_SHORT).show();
            tvLocationName.setText(city);
            SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(AppConstants.USER_CURRENT_ADDRESS, address);
            editor.putString(AppConstants.USER_CURRENT_CITY, city);
            editor.putString(AppConstants.USER_CURRENT_LATITUDE, String.valueOf(latitude));
            editor.putString(AppConstants.USER_CURRENT_LONGITUDE, String.valueOf(longitude));
            editor.apply();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onBackPressed() {
        BottomNavigationView mBottomNavigationView = findViewById(R.id.navigation);
        if (mBottomNavigationView.getSelectedItemId() == R.id.menu_explore) {
            super.onBackPressed();
            //  clearBackStack();
            finish();
        } else {
            mBottomNavigationView.setSelectedItemId(R.id.menu_explore);
        }
    }

    public void clearBackStack() {

        FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(
                0);
        getSupportFragmentManager().popBackStack(entry.getId(),
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().executePendingTransactions();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }


}