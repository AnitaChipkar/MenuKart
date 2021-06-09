package in.menukart.menukart.view.foodcart.trackorder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import in.menukart.menukart.R;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.MarkerData;
import in.menukart.menukart.entities.explore.Restaurant;
import in.menukart.menukart.entities.setting.ResponseSuccess;
import in.menukart.menukart.presenter.foodcart.trackorder.CheckOrderStatusPresenterImp;
import in.menukart.menukart.presenter.foodcart.trackorder.UpdateOrderStatusPresenterImp;
import in.menukart.menukart.util.AppConstants;
import in.menukart.menukart.view.other.MainActivity;

public class TrackOrderActivity extends AppCompatActivity implements OnMapReadyCallback, CheckOrderStatusView, UpdateOrderStatusView {


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 102;
    private AppCompatCheckBox checkBoxOrderPlaced, checkBoxOrderConfirmed,
            checkBoxPreparingFood, checkBoxFoodReady, checkBoxFoodOnWay, checkBoxFoodDelivered;
    private AppCompatButton btnRefresh;
    private Context context;
    private SharedPreferences sharedPreferences;
    private String orderId, orderStatus,userCurrentLatitude,userCurrentLongitude;
    private GoogleMap mMap;
    private Restaurant restaurant;
    double restaurantLat, restaurantLong, userLat, userLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        context = TrackOrderActivity.this;
        sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        orderId = sharedPreferences.getString(AppConstants.ORDER_ID, null);
        orderStatus = sharedPreferences.getString(AppConstants.ORDER_STATUS, null);
        Gson gson = new Gson();
        String jsonRestaurantDetails = sharedPreferences.getString(AppConstants.SELECTED_RESTAURANT_DETAILS, null);
        restaurant = gson.fromJson(jsonRestaurantDetails, Restaurant.class);
        userCurrentLatitude = sharedPreferences.getString(AppConstants.USER_CURRENT_LATITUDE, null);
        userCurrentLongitude = sharedPreferences.getString(AppConstants.USER_CURRENT_LONGITUDE, null);


        initTrackOrderViews();

         if (ApiClient.isConnectedToInternet(context)) {
            callUpdateOrderStatus();
        } else {
            ApiClient.openAlertDialogWithPositive(context, getString(R.string.error_check_network),
                    getString(R.string.dialog_label_ok));
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_track_order);
        mapFragment.getMapAsync(this);

    }

    private void makeLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
    }
    private void callCheckOrderStatus() {
        ApiClient.showProgressBar(TrackOrderActivity.this);
        try {
            CheckOrderStatusPresenterImp checkOrderStatusPresenterImp =
                    new CheckOrderStatusPresenterImp(this,
                            new ApiClient(TrackOrderActivity.this));
            Map<String, String> params = new HashMap<String, String>();
            params.put("order_id", orderId);
            String string = params.toString();
            Log.d("TAG", string);
            checkOrderStatusPresenterImp.requestCheckOrderStatus(params);

        } catch (Exception e) {
            Log.d("TAG", "sendOtp" + e.getMessage());
        }
    }

    private void initTrackOrderViews() {
        checkBoxOrderPlaced = findViewById(R.id.checkbox_order_placed);
        checkBoxOrderConfirmed = findViewById(R.id.checkbox_order_confirmed);
        checkBoxPreparingFood = findViewById(R.id.checkbox_preparing_food);
        checkBoxFoodReady = findViewById(R.id.checkbox_food_ready);
        checkBoxFoodOnWay = findViewById(R.id.checkbox_food_on_way);
        checkBoxFoodDelivered = findViewById(R.id.checkbox_food_delivered);
        btnRefresh = findViewById(R.id.btn_track_refresh);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ApiClient.isConnectedToInternet(context)) {
                    callCheckOrderStatus();
                } else {
                    ApiClient.openAlertDialogWithPositive(context, getString(R.string.error_check_network),
                            getString(R.string.dialog_label_ok));
                }
            }
        });


    }

    private void callUpdateOrderStatus() {
        ApiClient.showProgressBar(TrackOrderActivity.this);
        try {
            UpdateOrderStatusPresenterImp updateOrderStatusPresenterImp =
                    new UpdateOrderStatusPresenterImp(this,
                            new ApiClient(TrackOrderActivity.this));
            Map<String, String> params = new HashMap<String, String>();
            params.put("order_id", orderId);
            params.put("status", "1");
            String string = params.toString();
            Log.d("TAG", string);
            updateOrderStatusPresenterImp.requestUpdateOrderStatus(params);

        } catch (Exception e) {
            Log.d("TAG", "sendOtp" + e.getMessage());
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(TrackOrderActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (locationPermission != PackageManager.PERMISSION_GRANTED) {
                makeLocationPermissionRequest();
            } else {
                showPolyline();
            }
        } else {
            showPolyline();
        }
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
    }
    private void showPolyline() {


        NumberFormat _format = NumberFormat.getInstance(Locale.US);
        Number number1 = null;
        Number number2 = null;
        Number number3 = null;
        Number number4 = null;
        try {
            number1 = _format.parse(restaurant.getLatitude());
            number2 = _format.parse(restaurant.getLongitude());
            number3 = _format.parse(userCurrentLatitude);
            number4 = _format.parse(userCurrentLongitude);
            restaurantLat = Double.parseDouble(number1.toString());
            restaurantLong = Double.parseDouble(number2.toString());
            userLat = Double.parseDouble(number3.toString());
            userLong = Double.parseDouble(number4.toString());
            System.err.println("Double Value is :" + userLong);
        } catch (ParseException e) {

        }

        /*double restaurantLat = Double.parseDouble(restaurant.getLatitude());
        double restaurantLong = Double.parseDouble(restaurant.getLongitude());
        double userLat = Double.parseDouble(userCurrentLatitude);
        double userLong = Double.parseDouble(userCurrentLongitude);*/

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
        mMap.setMyLocationEnabled(true);

        Polyline polyline1 = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        /*new LatLng(18.445089, 73.868980),
                        new LatLng(18.626076, 73.812157))*/
                        new LatLng(restaurantLat, restaurantLong),
                        new LatLng(userLat, userLong))
                .color(Color.RED)
                .width(8));

        ArrayList<MarkerData> markerDataList = new ArrayList<>();
        markerDataList.add(new MarkerData(restaurantLat, restaurantLong, BitmapDescriptorFactory.HUE_RED));
        markerDataList.add(new MarkerData(userLat, userLong, BitmapDescriptorFactory.HUE_CYAN));
        /*ArrayList<LatLng> latlngs = new ArrayList<>();
        latlngs.add(new LatLng(18.445089, 73.868980));
        latlngs.add(new LatLng(18.501059, 73.862686));

        for (LatLng point : latlngs) {
            createMarker(point.latitude, point.longitude,BitmapDescriptorFactory.HUE_GREEN);
        }
*/

        for (MarkerData markerData : markerDataList) {
            createMarker(markerData.getLatitude(),
                    markerData.getLongitude(), markerData.getMarkerColor());

        }

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(restaurantLat, restaurantLong), 11.0f));
    }

    protected Marker createMarker(double latitude, double longitude, float v) {

        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                //.title(title)
                //.snippet(snippet)
                .icon(BitmapDescriptorFactory.defaultMarker(v)));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showPolyline();
                } else {
                }
                return;
            }
        }
    }

    @Override
    public void showError(String error) {
        ApiClient.hideProgressBar();
        Log.d("TAG", "onMenuList: error");
        Toast.makeText(TrackOrderActivity.this, getString(R.string.error_invalid_response), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onSuccessful(ResponseSuccess responseSuccess) {
        ApiClient.hideProgressBar();
        if ("Placed".equals(responseSuccess.getMessage())) {
            checkBoxOrderPlaced.setChecked(true);
        } else if ("Confirmed".equals(responseSuccess.getMessage())) {
            checkBoxOrderPlaced.setChecked(true);
            checkBoxOrderConfirmed.setChecked(true);
        } else if ("Food is ready".equals(responseSuccess.getMessage())) {
            checkBoxOrderConfirmed.setChecked(true);
            checkBoxPreparingFood.setChecked(true);
            checkBoxFoodReady.setChecked(true);
        } else if ("Dispatched".equals(responseSuccess.getMessage())) {
            checkBoxOrderConfirmed.setChecked(true);
            checkBoxPreparingFood.setChecked(true);
            checkBoxFoodReady.setChecked(true);
            checkBoxFoodOnWay.setChecked(true);
        } else if ("Delivered".equals(responseSuccess.getMessage())) {
            checkBoxFoodDelivered.setChecked(true);
        }
    }

    @Override
    public void showErrorUpdateOrderStatus(String error) {
        ApiClient.hideProgressBar();
        Log.d("TAG", "onMenuList: error");
        Toast.makeText(TrackOrderActivity.this, getString(R.string.error_invalid_response), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onUpdateOrderStatusSuccessful(ResponseSuccess responseSuccess) {
        ApiClient.hideProgressBar();
        if ("200".equals(responseSuccess.getStatus())) {
            checkBoxOrderPlaced.setChecked(true);
            SharedPreferences.Editor editorRemoveOrder = sharedPreferences.edit();
            editorRemoveOrder.remove(AppConstants.SELECTED_RESTAURANT_DETAILS);
            editorRemoveOrder.remove(AppConstants.SELECTED_MENU_LIST);
            editorRemoveOrder.remove(AppConstants.FOOD_CART_VALUES);
            editorRemoveOrder.apply();
            Toast.makeText(TrackOrderActivity.this, responseSuccess.getMessage(), Toast.LENGTH_LONG).show();

        }
    }
}