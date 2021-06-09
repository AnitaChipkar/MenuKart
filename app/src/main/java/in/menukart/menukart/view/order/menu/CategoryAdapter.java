package in.menukart.menukart.view.order.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import in.menukart.menukart.R;
import in.menukart.menukart.entities.order.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Context context;
    int index;
    private List<Category> categories;

    public CategoryAdapter(List<Category> categoryList, Context context) {
        this.categories = categoryList;
        this.context = context;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, final int position) {
        String imgUrl = "http://admin.menukart.online/uploads/category/" + categories.get(position).getCategory_img();


        Glide
                .with(context)
                .load(imgUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_loader_food)
                .into(holder.imgCategory);

        holder.textCategory.setText(categories.get(position).getCategory_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof MenuActivity) {
                    ((MenuActivity) context).setDataOnCategorySelection(categories.get(position).getId());
                }
                index = position;
                notifyDataSetChanged();

            }
        });

        if (index == position) {
            holder.viewSelectedCategory.setVisibility(View.VISIBLE);
        } else {
            holder.viewSelectedCategory.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imgCategory;
        AppCompatTextView textCategory;
        View viewSelectedCategory;

        public CategoryViewHolder(View view) {
            super(view);
            imgCategory = view.findViewById(R.id.iv_category);
            textCategory = view.findViewById(R.id.tv_category);
            viewSelectedCategory = view.findViewById(R.id.view_selected_category);
            viewSelectedCategory.setVisibility(View.GONE);

        }
    }
}
