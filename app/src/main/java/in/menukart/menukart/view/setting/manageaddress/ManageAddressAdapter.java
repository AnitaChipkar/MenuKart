package in.menukart.menukart.view.setting.manageaddress;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.menukart.menukart.R;
import in.menukart.menukart.entities.setting.UserAddress;


public class ManageAddressAdapter extends RecyclerView.Adapter<ManageAddressAdapter.ViewHolder> {

    private Context context;
    private List<UserAddress> userAddresses;

    public ManageAddressAdapter(Context context, List<UserAddress> userAddresses) {
        this.context = context;
        this.userAddresses = userAddresses;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_address_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final UserAddress userAddress = userAddresses.get(position);


        holder.textAddressTitle.setText(userAddress.getTitle());
        holder.textAddress.setText(userAddress.getAddress());
        holder.imgEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("userAddress", userAddress);
                Intent intentEditAddress = new Intent(context, MapsActivity.class);
                intentEditAddress.putExtras(bundle);
                context.startActivity(intentEditAddress);
                ((ManageAddressActivity) context).finish();
            }
        });
        holder.checkBoxSelectAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Intent intent = new Intent();
                    intent.putExtra("SELECTED_ADDRESS", holder.textAddress.getText().toString());
                    ((ManageAddressActivity) context).setResult(2, intent);
                    ((ManageAddressActivity) context).finish();//finishing activity
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        if (userAddresses != null) {
            return userAddresses.size();
        }
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public AppCompatImageView imgEditAddress;
        public AppCompatTextView textAddressTitle, textAddress;
        public AppCompatCheckBox checkBoxSelectAddress;

        public ViewHolder(View itemView) {
            super(itemView);

            imgEditAddress = itemView.findViewById(R.id.iv_edit_address);
            textAddressTitle = itemView.findViewById(R.id.tv_address_title);
            textAddress = itemView.findViewById(R.id.tv_address);
            checkBoxSelectAddress = itemView.findViewById(R.id.checkbox_select_address);


        }
    }

}
