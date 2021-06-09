package in.menukart.menukart.view.order.orderlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

import in.menukart.menukart.R;
import in.menukart.menukart.entities.order.OrderData;
import in.menukart.menukart.entities.order.OrderList;
import in.menukart.menukart.util.AppConstants;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    Context context;
    private List<OrderData> orderDataList;
    private List<OrderList> orderLists;


    public OrderAdapter(List<OrderData> orderDataList,
                        List<OrderList> orderLists, Context context) {
        this.orderDataList = orderDataList;
        this.orderLists = orderLists;
        this.context = context;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item, parent, false);
        OrderViewHolder orderViewHolder = new OrderViewHolder(view);
        return orderViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(OrderViewHolder holder, final int position) {
        final OrderData orderData = orderDataList.get(position);

        Glide
                .with(context)
                .load(orderData.getRestaurant_logo())
                .centerCrop()
                // .placeholder(R.drawable.ic_loader_food)
                .into(holder.imgResOrderLogo);

        holder.textResOrderName.setText(orderData.getRestaurant_name());
        double subTotal = Double.parseDouble(orderData.getSubtotal());
        holder.textResOrderAmount.setText("\u20B9 " + new DecimalFormat("##.##").format(subTotal));
        holder.textResMenuItemCount.setText("Items : " + String.valueOf(orderDataList.get(position).getMenus().size()));
        holder.textResOrderDateTime.setText(orderData.getTransaction_date());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent intentOrderSummary = new Intent(context, OrderDetailsActivity.class);
                bundle.putSerializable(AppConstants.ORDER_SUMMARY, orderData);
                intentOrderSummary.putExtras(bundle);
                context.startActivity(intentOrderSummary);

            }
        });

    }

    @Override
    public int getItemCount() {
        return orderDataList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imgResOrderLogo;
        AppCompatTextView textResOrderName, textResOrderAmount,
                textResMenuItemCount, textResOrderDateTime;

        public OrderViewHolder(View view) {
            super(view);
            imgResOrderLogo = view.findViewById(R.id.iv_restaurant_order);
            textResOrderName = view.findViewById(R.id.tv_restaurant_order_name);
            textResOrderAmount = view.findViewById(R.id.tv_restaurant_order_amount);
            textResMenuItemCount = view.findViewById(R.id.tv_restaurant_menu_item_count);
            textResOrderDateTime = view.findViewById(R.id.tv_restaurant_order_date_time);
        }
    }
}
