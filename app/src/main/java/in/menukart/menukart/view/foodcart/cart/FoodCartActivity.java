package in.menukart.menukart.view.foodcart.cart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.List;

import in.menukart.menukart.R;
import in.menukart.menukart.entities.foodcart.FoodCart;
import in.menukart.menukart.entities.order.RestaurantMenu;
import in.menukart.menukart.util.AppConstants;
import in.menukart.menukart.view.foodcart.signup.LoginActivity;

public class FoodCartActivity extends AppCompatActivity {

    List<RestaurantMenu> restaurantMenuList;
    Context context;
    RecyclerView rcvFoodCart;
    AppCompatEditText editPromoCode;
    AppCompatButton btnApplyPromo, btnProceedToCheckout;
    AppCompatTextView textFoodCartTotal, textFoodTax,
            textFoodDeliveryCharges, textPromoDiscount,
            textSubTotal;
    String foodCartTotal, foodCartTax, foodDeliveryCharges, foodPromoDisCount, foodSubTotal;
    double total = 0;
    double gst = 5.0;
    String menuAddedQty = "1";
    String menuRemovedQty = "1";

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_cart);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        context = FoodCartActivity.this;
        restaurantMenuList = (List<RestaurantMenu>) getIntent().
                getSerializableExtra("selectedMenus");

        initFoodCartViews();
        foodCartItemsData();


    }

    @SuppressLint("SetTextI18n")
    private void initFoodCartViews() {
        editPromoCode = findViewById(R.id.et_food_promo_code);
        textFoodCartTotal = findViewById(R.id.tv_food_cart_total);
        textFoodTax = findViewById(R.id.tv_food_cart_tax);
        textFoodDeliveryCharges = findViewById(R.id.tv_food_delivery_charge);
        textPromoDiscount = findViewById(R.id.tv_food_promo_discount);
        textSubTotal = findViewById(R.id.tv_food_cart_sub_total);
        btnApplyPromo = findViewById(R.id.btn_promo_code_apply);
        btnProceedToCheckout = findViewById(R.id.btn_proceed_to_checkout);

        if (restaurantMenuList != null) {
            for (int i = 0; i < restaurantMenuList.size(); i++) {
                total = total + Double.parseDouble(restaurantMenuList.get(i).getMenu_price());
            }
            foodCartTotal = String.valueOf(total);
            textFoodCartTotal.setText("\u20B9 " + String.valueOf(total));
            double amount = total;
            double tax = (amount / 100.0f) * gst;
            foodCartTax = new DecimalFormat("##.##").format(tax);
            textFoodTax.setText("\u20B9 " + new DecimalFormat("##.##").format(tax));
            foodDeliveryCharges = "0.00";
            textFoodDeliveryCharges.setText("\u20B9 0.00");
            foodPromoDisCount = "-0.00";
            textPromoDiscount.setText("\u20B9 -0.00");
            double totalAmount = total + tax;
            foodSubTotal = String.valueOf(totalAmount);
            textSubTotal.setText("\u20B9 " + String.valueOf(totalAmount));

        }

        btnProceedToCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveArrayList(restaurantMenuList, AppConstants.SELECTED_MENU_LIST);
                FoodCart foodCart = new FoodCart();
                foodCart.setCartSubTotal(foodCartTotal);
                foodCart.setTotalCgst("2.50");
                foodCart.setTotalSgst("2.50");
                foodCart.setCharges(foodDeliveryCharges);
                foodCart.setTotal(foodSubTotal);
                if (!"1".equals(menuAddedQty)) {
                    foodCart.setQty(menuAddedQty);
                } else if (!"1".equals(menuRemovedQty)) {
                    foodCart.setQty(menuRemovedQty);
                } else {
                    foodCart.setQty("1");
                }

                Gson gson = new Gson();
                String json = gson.toJson(foodCart);
                SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(AppConstants.FOOD_CART_VALUES, json);
                editor.apply();
                Intent intentLogin = new Intent(FoodCartActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
            }
        });


    }

    private void foodCartItemsData() {
        rcvFoodCart = findViewById(R.id.recycler_view_food_cart);
        //  rcvFoodCart.addItemDecoration(new DividerItemDecoration(FoodCartActivity.this, LinearLayoutManager.VERTICAL));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        rcvFoodCart.setLayoutManager(linearLayoutManager);
        rcvFoodCart.setHasFixedSize(true);

        FoodCartAdapter foodCartAdapter = new FoodCartAdapter(restaurantMenuList, context);
        rcvFoodCart.setAdapter(foodCartAdapter);
        foodCartAdapter.notifyDataSetChanged();


    }

    public void saveArrayList(List<RestaurantMenu> list, String key) {
        SharedPreferences prefs = getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();

    }

    @SuppressLint("SetTextI18n")
    public void setDataOnAddItem(String addedMenuPrice, String addedMenuQty) {
        total = total + Integer.parseInt(addedMenuPrice);
        foodCartTotal = String.valueOf(total);
        textFoodCartTotal.setText("\u20B9 " + String.valueOf(total));
        double amount = total;
        double tax = (amount / 100.0f) * gst;
        foodCartTax = new DecimalFormat("##.##").format(tax);
        textFoodTax.setText("\u20B9 " + new DecimalFormat("##.##").format(tax));
        foodDeliveryCharges = "0.00";
        textFoodDeliveryCharges.setText("\u20B9 0.00");
        foodPromoDisCount = "-0.00";
        textPromoDiscount.setText("\u20B9 -0.00");
        double totalAmount = total + tax;
        foodSubTotal = String.valueOf(totalAmount);
        textSubTotal.setText("\u20B9 " + String.valueOf(totalAmount));
        menuAddedQty = addedMenuQty;

    }

    @SuppressLint("SetTextI18n")
    public void setDataOnRemoveItem(String removedMenuPrice, String removedMenuQty) {
        total = total - Integer.parseInt(removedMenuPrice);
        foodCartTotal = String.valueOf(total);
        textFoodCartTotal.setText("\u20B9 " + String.valueOf(total));
        double amount = total;
        double tax = (amount / 100.0f) * gst;
        foodCartTax = new DecimalFormat("##.##").format(tax);
        textFoodTax.setText("\u20B9 " + new DecimalFormat("##.##").format(tax));
        foodDeliveryCharges = "0.00";
        textFoodDeliveryCharges.setText("\u20B9 0.00");
        foodPromoDisCount = "-0.00";
        textPromoDiscount.setText("\u20B9 -0.00");
        double totalAmount = total + tax;
        foodSubTotal = String.valueOf(totalAmount);
        textSubTotal.setText("\u20B9 " + String.valueOf(totalAmount));
        menuRemovedQty = removedMenuQty;

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


}