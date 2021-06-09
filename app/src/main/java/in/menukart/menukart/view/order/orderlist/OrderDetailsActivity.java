package in.menukart.menukart.view.order.orderlist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

import in.menukart.menukart.R;
import in.menukart.menukart.entities.order.OrderData;
import in.menukart.menukart.entities.order.OrderList;
import in.menukart.menukart.util.AppConstants;

public class OrderDetailsActivity extends AppCompatActivity {

    OrderData orderData;
    List<OrderList> orderLists;
    RecyclerView rcvOrderMenus;
    AppCompatImageView imgRestaurant;
    AppCompatTextView textResMenuName, textCartTotal, textTax,
            textDeliveryCharge, textPromoDiscount, textSubTotal,
            textPaymentMethod, textOrderStatus, textFromAddress, textToAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderData = (OrderData) bundle.getSerializable(AppConstants.ORDER_SUMMARY);
        }
        initOrderSummaryViews();
    }

    private void initOrderSummaryViews() {
        imgRestaurant = findViewById(R.id.iv_restaurant_promo);
        textResMenuName = findViewById(R.id.tv_restaurant_menu_name);
        textCartTotal = findViewById(R.id.tv_cart_total);
        textTax = findViewById(R.id.tv_tax);
        textDeliveryCharge = findViewById(R.id.tv_delivery_charge);
        textPromoDiscount = findViewById(R.id.tv_promo_discount);
        textSubTotal = findViewById(R.id.tv_sub_total);
        textPaymentMethod = findViewById(R.id.tv_payment_method);
        textOrderStatus = findViewById(R.id.tv_order_status);
        textFromAddress = findViewById(R.id.tv_from_address);
        textToAddress = findViewById(R.id.tv_to_address);
        rcvOrderMenus = findViewById(R.id.recycler_view_menus);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvOrderMenus.setLayoutManager(layoutManager);

        setOrderSummaryData();

    }

    @SuppressLint("SetTextI18n")
    private void setOrderSummaryData() {

        Glide
                .with(OrderDetailsActivity.this)
                .load(orderData.getRestaurant_logo())
                .centerCrop()
                // .(R.drawable.ic_loader_food)
                .into(imgRestaurant);


        textResMenuName.setText(orderData.getRestaurant_name());
        double subTotal = Double.parseDouble(orderData.getSubtotal());
        textCartTotal.setText("\u20B9 " + new DecimalFormat("##.##").format(subTotal));
        String tax;
        try {
            double value = Double.parseDouble(orderData.getSgst()) + Double.parseDouble(orderData.getCgst());
            tax = String.valueOf(value);
        } catch (NumberFormatException ex) {
            //either a or b is not a number
            tax = "Invalid input";
        }
        textTax.setText("\u20B9 " + tax);
        double deliveryCharges = Double.parseDouble(orderData.getCharges());
        textDeliveryCharge.setText("\u20B9 " + new DecimalFormat("##.##").format(deliveryCharges));
        textPromoDiscount.setText("- \u20B9 " + "0.00");
        double allTotal = Double.parseDouble(orderData.getTotalall());
        textSubTotal.setText("\u20B9 " + allTotal);
        textPaymentMethod.setText("Payment - " + orderData.getPayment_type());
        textOrderStatus.setText("Status - " + orderData.getOrder_status());
        textFromAddress.setText(orderData.getFrom_address());
        textToAddress.setText(orderData.getTo_delivery_address());
        orderLists = orderData.getMenus();
        OrderDetailsAdapter orderSummaryAdapter = new OrderDetailsAdapter(orderLists, OrderDetailsActivity.this);
        rcvOrderMenus.setAdapter(orderSummaryAdapter);
        orderSummaryAdapter.notifyDataSetChanged();


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
}