package com.example.maptracking.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maptracking.R;
import com.example.maptracking.AddressClickListener;
import com.example.maptracking.AddressDO;

import java.util.Vector;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.MyViewHolder> {
    CardView.LayoutParams paramsParent;
    AddressClickListener addressClickListener;
    Vector<AddressDO>vecAddressDO;
    Context context;
    int selectedPos=-1;
    public AddressListAdapter(Context context,Vector<AddressDO>vecAddressDO, AddressClickListener addressClickListener){
        this.vecAddressDO=vecAddressDO;
        this.addressClickListener=addressClickListener;
        this.context=context;
        paramsParent=new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300);
        paramsParent.setMargins(20,20,20,0);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.address_list_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        if (vecAddressDO!=null && vecAddressDO.size()>0) {
            final AddressDO addressDO=vecAddressDO.get(position);
            holder.tvAddress.setText(addressDO.address);
            Log.e("position","position "+position);
            /*if (selectedPos==position || vecAddressDO.get(position).isChange)
                holder.rlChild.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            else
                holder.rlChild.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));*/

            holder.ivMapIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPos=position;
                    Log.e("position","position "+selectedPos);
                    addressClickListener.onAddressClick(addressDO,selectedPos);
                }
            });
            holder.btnDelivered.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.tvTextClock.setVisibility(View.GONE);
                    holder.tvTime.setVisibility(View.VISIBLE);
                    holder.tvTime.setText(holder.tvTextClock.getText());
                }
            });
        }
    }
    public void refresh(){
        notifyItemChanged(selectedPos);
    }


    @Override
    public int getItemCount() {
        return vecAddressDO!=null?vecAddressDO.size():0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAddress,tvTime;
        public LinearLayout rlChild;
        public ImageView ivMapIcon,ivPhoneCall;
        public Button btnDelivered,btnNotDelivered;
        private TextClock tvTextClock;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAddress=itemView.findViewById(R.id.tvAddress);
            tvTime=itemView.findViewById(R.id.tvTime);
            rlChild=itemView.findViewById(R.id.rlChild);
            ivMapIcon=itemView.findViewById(R.id.ivMapIcon);
            ivPhoneCall=itemView.findViewById(R.id.ivPhoneCall);

            btnDelivered=itemView.findViewById(R.id.btnDelivered);
            btnNotDelivered=itemView.findViewById(R.id.btnNotDelivered);
            tvTextClock=itemView.findViewById(R.id.tvTextClock);

            itemView.setLayoutParams(paramsParent);
        }
    }
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         