package in.menukart.menukart.view.order.orderlist;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import in.menukart.menukart.R;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.foodcart.UserDetails;
import in.menukart.menukart.entities.order.OrderData;
import in.menukart.menukart.entities.order.OrderList;
import in.menukart.menukart.entities.order.OrderMenu;
import in.menukart.menukart.presenter.order.orderlist.OrderPresenterImp;
import in.menukart.menukart.util.AppConstants;

public class OrdersFragment extends Fragment implements OrderListView {
    RecyclerView rcvOrderList;
    View root;
    List<OrderData> orderDataList;
    List<OrderList> orderLists;
    OrderData orderData1;
    AppCompatTextView textNoOrders;
    String userId;
    private String TAG = "MainActivity";
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_orders, container, false);
        initOrderListViews();

        return root;
    }

    private void initOrderListViews() {
        context = getActivity();
        textNoOrders = root.findViewById(R.id.tv_no_order_history);
        rcvOrderList = root.findViewById(R.id.recycler_view_all_orders);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvOrderList.setLayoutManager(layoutManager);
        if (ApiClient.isConnectedToInternet(context)) {
            getListOfOrderData();
        } else {
            ApiClient.openAlertDialogWithPositive(context, getString(R.string.error_check_network),
                    getString(R.string.dialog_label_ok));
        }


    }

    private void getListOfOrderData() {
        ApiClient.showProgressBar(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(AppConstants.USER_DETAILS, null);
        UserDetails userDetails = gson.fromJson(json, UserDetails.class);
        if (userDetails != null) {
            userId = userDetails.getUser_id();
        }

        //  String orderUserId = sharedPreferences.getString(AppConstants.USER_ID, null);
        OrderPresenterImp orderPresenterImp = new OrderPresenterImp(this,
                new ApiClient(context));
        orderPresenterImp.requestOrderList(userId);//20


    }

    @Override
    public void showError(String error) {
        ApiClient.hideProgressBar();
        Log.d(TAG, "onOrderList: error");
        Toast.makeText(context, getString(R.string.error_invalid_response), Toast.LENGTH_LONG).show();


    }

    @Override
    public void onSuccessfulOrderList(OrderMenu orderMenu) {
        ApiClient.hideProgressBar();

        orderDataList = new ArrayList<>();
        orderLists = new ArrayList<>();
        if (orderMenu.getData() != null) {
            orderDataList.addAll(orderMenu.getData());
            for (int i = 0; i < orderDataList.size(); i++) {
                OrderData orderData = orderDataList.get(i);

                orderData1 = new OrderData();
                orderData1.setTransaction_date(orderData.getTransaction_date());
                orderData1.setRestaurant_name(orderData.getRestaurant_name());
                orderLists.addAll(orderData.getMenus());
            }

            OrderAdapter orderAdapter = new OrderAdapter(orderDataList, orderLists, context);
            rcvOrderList.setAdapter(orderAdapter);
            orderAdapter.notifyDataSetChanged();


        }
        if (orderDataList.size() == 0) {
            textNoOrders.setVisibility(View.VISIBLE);
        } else {
            textNoOrders.setVisibility(View.GONE);

        }

    }

}