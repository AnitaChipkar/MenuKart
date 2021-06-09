package in.menukart.menukart.view.foodcart.trackorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import in.menukart.menukart.R;

public class OrderPlacedActivity extends AppCompatActivity {

    private AppCompatButton btnViewOrderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        btnViewOrderStatus = findViewById(R.id.btn_view_order_status);
        btnViewOrderStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentTrackOrder = new Intent(OrderPlacedActivity.this, TrackOrderActivity.class);
                startActivity(intentTrackOrder);
                finish();

            }
        });
    }

}