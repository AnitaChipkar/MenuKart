package in.menukart.menukart.view.foodcart.trackorder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.easebuzz.payment.kit.PWECouponsActivity;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import datamodels.PWEStaticDataModel;
import in.menukart.menukart.R;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.MarkerData;
import in.menukart.menukart.entities.explore.Restaurant;
import in.menukart.menukart.entities.foodcart.FoodCart;
import in.menukart.menukart.entities.foodcart.SaveOrder;
import in.menukart.menukart.entities.foodcart.UserDetails;
import in.menukart.menukart.entities.order.RestaurantMenu;
import in.menukart.menukart.presenter.foodcart.trackorder.SaveOrderPresenterImp;
import in.menukart.menukart.util.AppConstants;
import in.menukart.menukart.view.setting.manageaddress.ManageAddressActivity;

public class OrderSummaryActivity extends AppCompatActivity implements OnMapReadyCallback, SaveOrderView {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 101;
    private static String hashSHA512;
    AppCompatTextView textFromSummaryAddress, textToSummaryAddress;
    SharedPreferences sharedPreferences;
    AppCompatButton btnCompleteOrder, btnChangeLocation;
    Context context;
    Gson gson = new Gson();
    RadioGroup rgPaymentGroup;
    RadioButton rbOnlinePay;
    String selectedPaymentOption, userCurrentLatitude,
            userCurrentLongitude, userCurrentCity;
    double restaurantLat, restaurantLong, userLat, userLong;
    String taxId;
    private GoogleMap mMap;
    private String TAG = "MAP";
    private List<RestaurantMenu> selectedRestaurantMenus;
    private UserDetails userDetails;
    private Restaurant restaurant;
    private FoodCart foodCart;

    public static String get_SHA_512_SecurePassword(String input) {
        try {
            // getInstance() method is called with algorithm SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            hashSHA512 = hashtext;
            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private void makeLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        context = OrderSummaryActivity.this;
        gson = new Gson();
        sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String jsonRestaurantDetails = sharedPreferences.getString(AppConstants.SELECTED_RESTAURANT_DETAILS, null);
        String jsonUserDetails = sharedPreferences.getString(AppConstants.USER_DETAILS, null);
        String jsonFoodCartValues = sharedPreferences.getString(AppConstants.FOOD_CART_VALUES, null);
        String userCurrentAddress = sharedPreferences.getString(AppConstants.USER_CURRENT_ADDRESS, null);
        userCurrentCity = sharedPreferences.getString(AppConstants.USER_CURRENT_CITY, null);
        userCurrentLatitude = sharedPreferences.getString(AppConstants.USER_CURRENT_LATITUDE, null);
        userCurrentLongitude = sharedPreferences.getString(AppConstants.USER_CURRENT_LONGITUDE, null);
        restaurant = gson.fromJson(jsonRestaurantDetails, Restaurant.class);
        userDetails = gson.fromJson(jsonUserDetails, UserDetails.class);
        foodCart = gson.fromJson(jsonFoodCartValues, FoodCart.class);
        selectedRestaurantMenus = getArrayList(AppConstants.SELECTED_MENU_LIST);
        textFromSummaryAddress = findViewById(R.id.tv_from_summary_address);
        textToSummaryAddress = findViewById(R.id.tv_to_summary_address);
        rgPaymentGroup = findViewById(R.id.rg_payment_option);
        rbOnlinePay = findViewById(R.id.rb_pay_online);
        btnCompleteOrder = findViewById(R.id.btn_complete_order);
        btnChangeLocation = findViewById(R.id.btn_change_location);
        textFromSummaryAddress.setText(restaurant.getRestaurant_address());
        textToSummaryAddress.setText(userCurrentAddress);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_order_summary);
        mapFragment.getMapAsync(this);

        btnCompleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedRadioButtonID = rgPaymentGroup.getCheckedRadioButtonId();
                if (ApiClient.isConnectedToInternet(context)) {
                    // If nothing is selected from Radio Group, then it return -1
                    if (selectedRadioButtonID != -1) {

                        RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
                        selectedPaymentOption = selectedRadioButton.getText().toString();
                        callOrderComplete();
                    } else {
                        Toast.makeText(OrderSummaryActivity.this, "Please Select Payment Option", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    ApiClient.openAlertDialogWithPositive(context, getString(R.string.error_check_network),
                            getString(R.string.dialog_label_ok));
                }
            }
        });
        Random rand = new Random();
        int randomNumber = rand.nextInt(100);
        taxId = String.valueOf(randomNumber);
        rbOnlinePay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    btnCompleteOrder.setVisibility(View.INVISIBLE);
                    callPaymentGateway(taxId);
                }

            }
        });
       /* rgPaymentGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                if (checkId == R.id.rb_pay_online)
                {
                    callPaymentGateway();
                }
               *//* switch (checkId) {
                    case R.id.rb_pay_online:
                        callPaymentGateway();
                        break;
                    default:
                        break;
                }*//*

            }
        });*/
        btnChangeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentChangeAddress = new Intent(OrderSummaryActivity.this, ManageAddressActivity.class);
                startActivityForResult(intentChangeAddress, 2);
            }
        });
    }

    private void callPaymentGateway(String taxId) {
        //key|txnid|amount|productinfo|firstname|email_id|udf1|udf2|udf3|udf4|udf5||||||salt|key
        double amount = Double.parseDouble(foodCart.getCartSubTotal());
        String hashSequence = "N7Q2112ME8|" + taxId + "|" + amount +
                "|" + restaurant.getRestaurant_name() + "|" + userDetails.getFname() +
                "|" + userDetails.getEmail() + "|udf1|udf2|udf3|udf4|udf5||||||4ACE76HEX3|N7Q2112ME8";
        get_SHA_512_SecurePassword(hashSequence);


        Intent intentProceed = new Intent(OrderSummaryActivity.this, PWECouponsActivity.class);
        intentProceed.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // This is mandatory flag
        intentProceed.putExtra("txnid", taxId);
        intentProceed.putExtra("amount", amount);
        intentProceed.putExtra("productinfo", restaurant.getRestaurant_name());
        intentProceed.putExtra("firstname", userDetails.getFname());
        intentProceed.putExtra("email", userDetails.getEmail());
        intentProceed.putExtra("phone", userDetails.getMobileno());
        intentProceed.putExtra("key", "N7Q2112ME8");
        intentProceed.putExtra("udf1", "udf1");
        intentProceed.putExtra("udf2", "udf2");
        intentProceed.putExtra("udf3", "udf3");
        intentProceed.putExtra("udf4", "udf4");
        intentProceed.putExtra("udf5", "udf5");
        intentProceed.putExtra("address1", userCurrentCity);
       /* intentProceed.putExtra("address2", "address2");
        intentProceed.putExtra("city", "Pune");
        intentProceed.putExtra("state", "State");
        intentProceed.putExtra("country", "Country");
        intentProceed.putExtra("zipcode", "Zipcode");*/
        intentProceed.putExtra("hash", hashSHA512);
        intentProceed.putExtra("unique_id", userDetails.getUser_id());
        intentProceed.putExtra("pay_mode", "test");
       /* intentProceed.putExtra("sub_merchant_id", "Pass sub merchant " +
                "id If you are using a Sub-Aggregator feature . " +
                "Do not pass this parameter if the Sub-Aggregator " +
                "feature is not enabled.");*/
        startActivityForResult(intentProceed, PWEStaticDataModel.PWE_REQUEST_CODE);
    }

    private void callOrderComplete() {
        ApiClient.showProgressBar(OrderSummaryActivity.this);
        try {
            SaveOrderPresenterImp saveOrderPresenterImp =
                    new SaveOrderPresenterImp(this,
                            new ApiClient(OrderSummaryActivity.this));
            Map<String, String> params = new HashMap<String, String>();
            ArrayList<JSONObject> list = new ArrayList<JSONObject>();

            for (RestaurantMenu restaurantMenu : selectedRestaurantMenus) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("restaurant_id", restaurantMenu.getRestaurant_id());
                jsonObject.put("menu_id", restaurantMenu.getMenu_id());
                jsonObject.put("menu_name", restaurantMenu.getMenu_name());
                jsonObject.put("menu_price", restaurantMenu.getMenu_price());
                jsonObject.put("qty", foodCart.getQty());
                jsonObject.put("cgst", "2.5");
                jsonObject.put("sgst", "2.5");
                //  jsonObject.put("variation", restaurantMenu.getVariation());
                jsonObject.put("menu_variation", "");
                list.add(jsonObject);
            }

            JSONArray jsArray = new JSONArray(list);
            params.put("order", jsArray.toString());
            params.put("user_id", userDetails.getUser_id());
            params.put("cart_subtotal", foodCart.getCartSubTotal());
            params.put("total_cgst", "2.50");
            params.put("total_sgst", "2.50");
            params.put("payment_type", selectedPaymentOption);
            params.put("delivery_address", "Pune");
            params.put("order_type", "takeout");
            params.put("charges", foodCart.getCharges());
            params.put("total", foodCart.getTotal());
            params.put("transaction_id", "");
            params.put("easepayid", "");

            String string = params.toString();
            Log.d("TAG", string);
            saveOrderPresenterImp.requestSaveOrder(params);

        } catch (Exception e) {
            Log.d("TAG", "OrderSummary" + e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    public List<RestaurantMenu> getArrayList(String key) {
        SharedPreferences prefs = getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<RestaurantMenu>>() {
        }.getType();
        return gson.fromJson(json, type);
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
        Toast.makeText(OrderSummaryActivity.this, getString(R.string.error_invalid_response), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onSuccess(SaveOrder saveOrder) {
        ApiClient.hideProgressBar();
        if (saveOrder.getOrder_id() != null) {
            Toast.makeText(OrderSummaryActivity.this, "Your Order Saved Successfully!", Toast.LENGTH_SHORT).show();
            Intent intentPlaceOrder = new Intent(OrderSummaryActivity.this, OrderPlacedActivity.class);
            startActivity(intentPlaceOrder);
            finish();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(AppConstants.ORDER_ID, saveOrder.getOrder_id());
            editor.putString(AppConstants.ORDER_STATUS, saveOrder.getStatus());
            editor.apply();


        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            String message = data.getStringExtra("SELECTED_ADDRESS");
            textToSummaryAddress.setText(message);

        }
        if (data != null) {
            if (requestCode == PWEStaticDataModel.PWE_REQUEST_CODE) {
                String result = data.getStringExtra("result");
                String payment_response = data.getStringExtra("payment_response");
                try {
                    if ("payment_successfull".equals(result)) {
                        Toast.makeText(OrderSummaryActivity.this, result, Toast.LENGTH_SHORT).show();
                        btnCompleteOrder.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(OrderSummaryActivity.this, result, Toast.LENGTH_SHORT).show();
                        btnCompleteOrder.setVisibility(View.INVISIBLE);
                    }

// Handle response here
                } catch (Exception e) {
// Handle exception here
                }
            }
        }
    }

}