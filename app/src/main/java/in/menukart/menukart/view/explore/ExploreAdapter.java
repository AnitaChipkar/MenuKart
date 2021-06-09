package in.menukart.menukart.view.explore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import in.menukart.menukart.R;
import in.menukart.menukart.entities.explore.Restaurant;
import in.menukart.menukart.util.AppConstants;
import in.menukart.menukart.view.order.menu.MenuActivity;


public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<Restaurant> restaurantList;
    private List<Restaurant> restaurantListFiltered;

    public ExploreAdapter(Context context, List<Restaurant> restaurantList) {
        this.context = context;
        this.restaurantList = restaurantList;
        this.restaurantListFiltered = restaurantList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(restaurantListFiltered.get(position));

        final Restaurant restaurants = restaurantListFiltered.get(position);

        holder.textRestaurantName.setText(restaurants.getRestaurant_name());
        holder.textRestaurantType.setText(restaurants.getRestaurant_foodtype());
        holder.textDeliveryTime.setText(restaurants.getRestauran_delivery_min_time() + " Min");

        Glide
                .with(context)
                .load(restaurants.getRestaurant_logo())
                .centerCrop()
                //  .placeholder(R.drawable.ic_no_food)
                .into(holder.imgRestaurantPromo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MenuActivity.class);
                intent.putExtra(AppConstants.RESTAURANT_ID, restaurants.getRestaurant_id());
                intent.putExtra(AppConstants.RESTAURANT_NAME, restaurants.getRestaurant_name());
                context.startActivity(intent);
                SharedPreferences prefs = context.getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(restaurants);
                editor.putString(AppConstants.SELECTED_RESTAURANT_DETAILS, json);
                editor.apply();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (restaurantListFiltered != null) {
            return restaurantListFiltered.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    restaurantListFiltered = restaurantList;
                } else {
                    List<Restaurant> filteredList = new ArrayList<>();
                    for (Restaurant row : restaurantList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getRestaurant_name().toLowerCase().contains(charString.toLowerCase())) //row.getRestaurant_foodtype().contains(charSequence)
                        {
                            filteredList.add(row);
                        }
                    }

                    restaurantListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = restaurantListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                restaurantListFiltered = (ArrayList<Restaurant>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public AppCompatImageView imgRestaurantPromo;
        public AppCompatTextView textRestaurantName, textRestaurantType, textDeliveryTime;

        public ViewHolder(View itemView) {
            super(itemView);

            imgRestaurantPromo = itemView.findViewById(R.id.iv_restaurant_promo);
            textRestaurantName = itemView.findViewById(R.id.tv_restaurant_name);
            textRestaurantType = itemView.findViewById(R.id.tv_restaurant_type);
            textDeliveryTime = itemView.findViewById(R.id.tv_delivery_time);


        }
    }

}
