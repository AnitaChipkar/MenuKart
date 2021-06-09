package in.menukart.menukart.view.setting.manageaddress;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import in.menukart.menukart.R;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.foodcart.UserDetails;
import in.menukart.menukart.entities.setting.ResponseSuccess;
import in.menukart.menukart.entities.setting.UserAddress;
import in.menukart.menukart.presenter.setting.manageaddress.AddAddressPresenterImp;
import in.menukart.menukart.presenter.setting.manageaddress.UpdateAddressPresenterImp;
import in.menukart.menukart.util.AppConstants;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, AddAddressView, UpdateAddressView {

    private static final int LOCATION_REQUEST_CODE = 101;
    String updateTitle, updateAddress, userUpdateId;
    Double latitude, longitude, updateLatitude, updateLongitude;
    Bundle bundle;
    UserDetails userDetails;
    private AppCompatEditText editAddressTitle, editAddress;
    // private GoogleMap mMap;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LatLng latLng;
    private AppCompatButton btnSaveAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(AppConstants.USER_DETAILS, null);
        userDetails = gson.fromJson(json, UserDetails.class);

        editAddressTitle = findViewById(R.id.et_address_title);
        editAddress = findViewById(R.id.et_address);
        btnSaveAddress = findViewById(R.id.btn_save_address);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            UserAddress userAddress = (UserAddress) bundle.getSerializable("userAddress");
            if (userAddress != null) {
                userUpdateId = userAddress.getId();
                updateTitle = userAddress.getTitle();
                updateAddress = userAddress.getAddress();
                updateLatitude = Double.valueOf(userAddress.getLatitude());
                updateLongitude = Double.valueOf(userAddress.getLongitude());
                editAddressTitle.setText(updateTitle);
                editAddress.setText(updateAddress);
                SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                supportMapFragment.getMapAsync(MapsActivity.this);


            }
        } else {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                return;
            }
            fetchLastLocation();

        }
        btnSaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bundle != null) {
                    if (ApiClient.isConnectedToInternet(MapsActivity.this)) {
                        callUpdateUserAddress();
                    } else {
                        ApiClient.openAlertDialogWithPositive(MapsActivity.this, getString(R.string.error_check_network),
                                getString(R.string.dialog_label_ok));
                    }


                } else {
                    if (ApiClient.isConnectedToInternet(MapsActivity.this)) {
                        callAddUserAddress();
                    } else {
                        ApiClient.openAlertDialogWithPositive(MapsActivity.this, getString(R.string.error_check_network),
                                getString(R.string.dialog_label_ok));
                    }
                }


            }
        });


    }


    private void callAddUserAddress() {
        ApiClient.showProgressBar(MapsActivity.this);
        try {
            AddAddressPresenterImp addAddressPresenterImp =
                    new AddAddressPresenterImp(this,
                            new ApiClient(MapsActivity.this));
            Map<String, String> params = new HashMap<String, String>();
            params.put("user_id", userDetails.getUser_id());
            params.put("address", editAddress.getText().toString());
            params.put("title", editAddressTitle.getText().toString());
            params.put("latitude", String.valueOf(latitude));
            params.put("longitude", String.valueOf(longitude));
            //return params;

            String string = params.toString();
            Log.d("TAG", string);
            addAddressPresenterImp.requestAddAddress(params);

        } catch (Exception e) {
            Log.d("TAG", "MenuList" + e.getMessage());
        }
    }

    private void callUpdateUserAddress() {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(AppConstants.USER_DETAILS, null);
        UserDetails userDetails = gson.fromJson(json, UserDetails.class);

        ApiClient.showProgressBar(MapsActivity.this);
        try {
            UpdateAddressPresenterImp updateAddressPresenterImp =
                    new UpdateAddressPresenterImp(this,
                            new ApiClient(MapsActivity.this));
            Map<String, String> params = new HashMap<String, String>();
            params.put("id", userUpdateId);
            params.put("user_id", userDetails.getUser_id());
            params.put("address", editAddress.getText().toString());
            params.put("title", editAddressTitle.getText().toString());
            params.put("latitude", String.valueOf(updateLatitude));
            params.put("longitude", String.valueOf(updateLongitude));
            //return params;

            String string = params.toString();
            Log.d("TAG", string);
            updateAddressPresenterImp.requestUpdateAddress(params);

        } catch (Exception e) {
            Log.d("TAG", "MenuList" + e.getMessage());
        }
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(MapsActivity.this, currentLocation.getLatitude() + " " + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    supportMapFragment.getMapAsync(MapsActivity.this);
                    latitude = currentLocation.getLatitude();
                    longitude = currentLocation.getLongitude();
                    if (updateLatitude != null && updateLongitude != null) {
                        getLocation(updateLatitude, updateLongitude);

                    } else {
                        getLocation(currentLocation.getLatitude(), currentLocation.getLongitude());

                    }

                } else {
                    Toast.makeText(MapsActivity.this, "No Location recorded", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        if (updateLatitude != null && updateLongitude != null) {
            latLng = new LatLng(updateLatitude, updateLongitude);

        } else {
            latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        }
         /* //MarkerOptions are used to create a new Marker.You can specify location, title etc with MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("You are Here");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        //Adding the created the marker on the map
        googleMap.addMarker(markerOptions);*/


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(15f).tilt(30).build();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(false);
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("System out", "onMarkerDragStart..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onMarkerDragEnd(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("System out", "onMarkerDragEnd..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);

                googleMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
                latitude = arg0.getPosition().latitude;
                longitude = arg0.getPosition().longitude;
                getLocation(arg0.getPosition().latitude, arg0.getPosition().longitude);
            }

            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub
                Log.i("System out", "onMarkerDrag...");
            }
        });

//Don't forget to Set draggable(true) to marker, if this not set marker does not drag.

        googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                //  .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_my_location))
                .draggable(true));


    }

    private void getLocation(double latitude, double longitude) {
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

            if (bundle != null) {
                editAddressTitle.setText(updateTitle);
            } else {
                editAddressTitle.setText("New User");
            }

            editAddress.setText(address);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResult) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();
                } else {
                    Toast.makeText(MapsActivity.this, "Location permission missing", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void showError(String error) {
        ApiClient.hideProgressBar();
        Log.d("TAG", "onMenuList: error");
        Toast.makeText(MapsActivity.this, getString(R.string.error_invalid_response), Toast.LENGTH_LONG).show();


    }

    @Override
    public void onSuccessfulAddAddress(ResponseSuccess responseSuccess) {
        ApiClient.hideProgressBar();
        if ("200".equalsIgnoreCase(responseSuccess.getStatus())) {
            Toast.makeText(MapsActivity.this, "Address added Successfully!", Toast.LENGTH_SHORT).show();
            Intent intentAdd = new Intent(MapsActivity.this, ManageAddressActivity.class);
            startActivity(intentAdd);
            finish();

        }

    }

    @Override
    public void showUpdateAddressError(String error) {
        ApiClient.hideProgressBar();
        Log.d("TAG", "onMenuList: error");
        Toast.makeText(MapsActivity.this, getString(R.string.error_invalid_response), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onSuccessfulUpdateAddress(ResponseSuccess responseSuccess) {
        ApiClient.hideProgressBar();
        if ("200".equalsIgnoreCase(responseSuccess.getStatus())) {
            Toast.makeText(MapsActivity.this, "Address updated Successfully!", Toast.LENGTH_SHORT).show();
            Intent intentUpdate = new Intent(MapsActivity.this, ManageAddressActivity.class);
            startActivityForResult(intentUpdate,2);
            finish();
        }
    }
}