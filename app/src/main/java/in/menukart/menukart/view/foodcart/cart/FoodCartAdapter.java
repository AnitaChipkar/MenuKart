package in.menukart.menukart.view.foodcart.cart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import in.menukart.menukart.R;
import in.menukart.menukart.entities.order.RestaurantMenu;

public class FoodCartAdapter extends RecyclerView.Adapter<FoodCartAdapter.FoodCartViewHolder> {
    Context context;
    private List<RestaurantMenu> restaurantFoodCartMenus;


    public FoodCartAdapter(List<RestaurantMenu> restaurantFoodCartMenus, Context context) {
        this.restaurantFoodCartMenus = restaurantFoodCartMenus;
        this.context = context;
    }

    @Override
    public FoodCartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_cart_list_item, parent, false);
        FoodCartViewHolder orderViewHolder = new FoodCartViewHolder(view);
        return orderViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final FoodCartViewHolder holder, final int position) {
        final RestaurantMenu restaurantMenu = restaurantFoodCartMenus.get(position);
        String imgUrl = "http://admin.menukart.online/uploads/menu/" + restaurantFoodCartMenus.get(position).getMenu_logo();


        Glide
                .with(context)
                .load(imgUrl)
                .centerCrop()
                // .placeholder(R.drawable.ic_loader_food)
                .into(holder.imgFoodCart);

        holder.textFoodCartName.setText(restaurantMenu.getMenu_name());
        holder.textFoodCartPrice.setText("\u20B9 " + Double.parseDouble(restaurantMenu.getMenu_price()));
        holder.textFoodCartCount.setText("1");
        holder.textAddFoodCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int addCount = Integer.parseInt(holder.textFoodCartCount.getText().toString());
                addCount++;
                holder.textFoodCartCount.setText(String.valueOf(addCount));
                double foodAddPrice = addCount * Double.parseDouble(restaurantMenu.getMenu_price());
                holder.textFoodCartPrice.setText("\u20B9 " + String.valueOf(foodAddPrice));

                if (context instanceof FoodCartActivity) {
                    ((FoodCartActivity) context).setDataOnAddItem(restaurantMenu.getMenu_price(), holder.textFoodCartCount.getText().toString());
                }

            }
        });
        holder.textRemoveFoodCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int removeCount = Integer.parseInt(holder.textFoodCartCount.getText().toString());
                if (removeCount > 1) {
                    removeCount--;
                    holder.textFoodCartCount.setText(String.valueOf(removeCount));
                    double foodRemovePrice = removeCount * Double.parseDouble(restaurantMenu.getMenu_price());
                    holder.textFoodCartPrice.setText("\u20B9 " + String.valueOf(foodRemovePrice));

                    if (context instanceof FoodCartActivity) {
                        ((FoodCartActivity) context)
                                .setDataOnRemoveItem(restaurantMenu.getMenu_price(), holder.textFoodCartCount.getText().toString());
                    }
                }


            }
        });
        holder.imgFoodCartClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAt(position);

            }
        });

    }

    private void removeAt(int position) {
        restaurantFoodCartMenus.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, restaurantFoodCartMenus.size());
    }

    @Override
    public int getItemCount() {
        return restaurantFoodCartMenus.size();
    }

    public class FoodCartViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imgFoodCart, imgFoodCartClose;
        AppCompatTextView textFoodCartName, textFoodCartPrice,
                textAddFoodCart, textFoodCartCount, textRemoveFoodCart;
        RelativeLayout rlFoodCartAddRemove;

        public FoodCartViewHolder(View view) {
            super(view);
            imgFoodCart = view.findViewById(R.id.iv_food_cart_image);
            imgFoodCartClose = view.findViewById(R.id.iv_food_cart_close);
            rlFoodCartAddRemove = view.findViewById(R.id.rl_food_cart_add_remove);
            textFoodCartName = view.findViewById(R.id.tv_food_cart_name);
            textFoodCartPrice = view.findViewById(R.id.tv_food_cart_price);
            textAddFoodCart = view.findViewById(R.id.tv_add_food_cart);
            textFoodCartCount = view.findViewById(R.id.tv_food_item_count);
            textRemoveFoodCart = view.findViewById(R.id.tv_food_cart_remove);
        }
    }
}
