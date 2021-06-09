package in.menukart.menukart.view.order.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import in.menukart.menukart.R;
import in.menukart.menukart.entities.order.RestaurantMenu;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.CategoryViewHolder> {
    Context context;
    private List<RestaurantMenu> restaurantMenus;
    private boolean checkValue = false;

    public MenuAdapter(List<RestaurantMenu> restaurantMenus, Context context) {
        this.restaurantMenus = restaurantMenus;
        this.context = context;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_item, parent, false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(groceryProductView);
        return categoryViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, final int position) {
        String imgUrl = "http://admin.menukart.online/uploads/menu/" + restaurantMenus.get(position).getMenu_logo();


        Glide
                .with(context)
                .load(imgUrl)
                .centerCrop()
                // .placeholder(R.drawable.ic_loader_food)
                .into(holder.imgMenu);

        holder.textMenuName.setText(restaurantMenus.get(position).getMenu_name());
        holder.textMenuVegNonVeg.setText(restaurantMenus.get(position).getMenu_foodtype());
        holder.textMenuCost.setText("\u20B9 " + restaurantMenus.get(position).getMenu_price());
        holder.btnAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValue = true;
                if (context instanceof MenuActivity) {
                    ((MenuActivity) context).
                            addDataOnMenuSelection(restaurantMenus.get(position));
                    holder.btnAddMenu.setClickable(false);
                    holder.btnAddMenu.setText("Added");
                    holder.btnAddMenu.setTextColor(context.getResources().getColor(R.color.colorGray));
                  //  holder.btnAddMenu.setBackgroundColor(context.getResources().getColor(R.color.colorGray));
                    holder.btnAddMenu.setBackgroundDrawable(context.
                            getResources().getDrawable(R.drawable.bg_rounded_gray_button));


                }
            }
        });
       /* if (checkValue) {
            holder.btnAddMenu.setClickable(false);
            holder.btnAddMenu.setBackgroundColor(context.getResources().getColor(R.color.colorGray));
        }*/


       /* holder.textAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int addCount = Integer.parseInt(holder.textMenuCount.getText().toString());
                addCount++;
                holder.textMenuCount.setText(String.valueOf(addCount));

            }
        });
        holder.textRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int removeCount = Integer.parseInt(holder.textMenuCount.getText().toString());
                removeCount--;
                holder.textMenuCount.setText(String.valueOf(removeCount));

            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return restaurantMenus.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imgMenu;
        AppCompatTextView textMenuName, textMenuVegNonVeg, textMenuCost,
                textAdd, textMenuCount, textRemove;
        AppCompatButton btnAddMenu;
        RelativeLayout rlMenuAddRemoveItem;

        public CategoryViewHolder(View view) {
            super(view);
            imgMenu = view.findViewById(R.id.iv_menu_promo);
            textMenuName = view.findViewById(R.id.tv_menu_name);
            textMenuVegNonVeg = view.findViewById(R.id.tv_menu_veg_non_veg);
            textMenuCost = view.findViewById(R.id.tv_menu_cost);
            btnAddMenu = view.findViewById(R.id.btn_menu_add);
             /*btnAddMenu.setVisibility(View.VISIBLE);
            rlMenuAddRemoveItem = view.findViewById(R.id.rl_menu_add_remove_item);
            rlMenuAddRemoveItem.setVisibility(View.GONE);
            textAdd = view.findViewById(R.id.tv_menu_add);
            textMenuCount = view.findViewById(R.id.tv_menu_item_count);
            textRemove = view.findViewById(R.id.tv_menu_remove);*/
        }
    }
}
