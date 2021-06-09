package in.menukart.menukart.view.order.orderlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.menukart.menukart.R;
import in.menukart.menukart.entities.order.OrderList;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.OrderViewHolder> {
    Context context;
    private List<OrderList> orderLists;


    public OrderDetailsAdapter(List<OrderList> orderLists, Context context) {
        this.orderLists = orderLists;
        this.context = context;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details_list_item, parent, false);
        OrderViewHolder orderViewHolder = new OrderViewHolder(view);
        return orderViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(OrderViewHolder holder, final int position) {
        final OrderList orderList = orderLists.get(position);


        holder.textMenuName.setText(orderList.getMenu_name());
        holder.textMenuPrice.setText("\u20B9 " + orderList.getMenu_price());
        holder.textMenuQuantity.setText(" X " + orderList.getQty());


    }

    @Override
    public int getItemCount() {
        return orderLists.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView textMenuName, textMenuPrice, textMenuQuantity;

        public OrderViewHolder(View view) {
            super(view);
            textMenuName = view.findViewById(R.id.tv_menu_name);
            textMenuPrice = view.findViewById(R.id.tv_menu_price);
            textMenuQuantity = view.findViewById(R.id.tv_menu_quantity);
        }
    }
}
