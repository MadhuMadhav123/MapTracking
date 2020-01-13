package com.example.maptracking.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maptracking.common.AppConstants;
import com.example.maptracking.utilities.CalendarUtils;
import com.example.maptracking.R;
import com.example.maptracking.listeners.AddressClickListener;
import com.example.maptracking.objects.AddressDO;

import org.w3c.dom.Text;

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
            if(!TextUtils.isEmpty(addressDO.time))
                handleButtonClick(holder,addressDO.isDelivered,addressDO.time);
            else{
                holder.tvTextClock.setVisibility(View.VISIBLE);
                holder.tvTime.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(addressDO.imagePath))
                holder.btnPickImage.setText(context.getResources().getString(R.string.view_file));
            else
                holder.btnPickImage.setText(context.getResources().getString(R.string.take_photo));

            if (!TextUtils.isEmpty(addressDO.videoPath))
                holder.btnRecordVideo.setText(context.getResources().getString(R.string.view_file));
            else
                holder.btnRecordVideo.setText(context.getResources().getString(R.string.take_video));

            if (!TextUtils.isEmpty(addressDO.audioPath))
                holder.btnRecordAudio.setText(context.getResources().getString(R.string.play_audio));
            else if (holder.btnPickImage.getText().toString().equalsIgnoreCase(context.getResources().getString(R.string.stop_recording)))
                ;
            else
                holder.btnRecordAudio.setText(context.getResources().getString(R.string.capture_audio));
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
                    vecAddressDO.get(position).time= String.valueOf(CalendarUtils.getCurrentDataAndTime());
                    handleButtonClick(holder,true,vecAddressDO.get(position).time);
                }
            });
            holder.btnNotDelivered.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vecAddressDO.get(position).time= String.valueOf(CalendarUtils.getCurrentDataAndTime());
                    handleButtonClick(holder,false,vecAddressDO.get(position).time);
                }
            });
            holder.ivPhoneCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addressClickListener.onPhoneCall(addressDO,position,AppConstants.PHONE,AppConstants.CAPTURE_FILE);
                }
            });
            holder.btnPickImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(addressDO.imagePath))
                        addressClickListener.onPhoneCall(addressDO,position,AppConstants.CAMERA_IMAGE,AppConstants.CAPTURE_FILE);
                    else
                        addressClickListener.onPhoneCall(addressDO,position,AppConstants.CAMERA_IMAGE,AppConstants.VIEW_FILE);
                }
            });
            holder.btnRecordAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(TextUtils.isEmpty(addressDO.audioPath) && holder.btnRecordAudio.getText().toString().equalsIgnoreCase(context.getResources().getString(R.string.capture_audio))) {
                        holder.btnRecordAudio.setText(context.getResources().getString(R.string.stop_recording));
                        addressClickListener.onPhoneCall(addressDO, position, AppConstants.AUDIO, AppConstants.CAPTURE_FILE);
                    }
                    else if(holder.btnRecordAudio.getText().toString().equalsIgnoreCase(context.getResources().getString(R.string.stop_recording))) {
                        holder.btnRecordAudio.setText(context.getResources().getString(R.string.play_audio));
                        addressClickListener.onPhoneCall(addressDO, position, AppConstants.AUDIO, AppConstants.STOP_RECORDING);
                    }
                    else if(!TextUtils.isEmpty(addressDO.audioPath))
                        addressClickListener.onPhoneCall(addressDO,position,AppConstants.AUDIO,AppConstants.VIEW_FILE);
                }
            });
            holder.btnRecordVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(addressDO.videoPath))
                        addressClickListener.onPhoneCall(addressDO,position,AppConstants.VIDEO,AppConstants.CAPTURE_FILE);
                    else
                        addressClickListener.onPhoneCall(addressDO,position,AppConstants.VIDEO,AppConstants.VIEW_FILE);
                }
            });
        }
    }

    private void handleButtonClick(MyViewHolder holder, boolean isDeliveried, String time) {
        holder.tvTextClock.setVisibility(View.GONE);
        holder.tvTime.setVisibility(View.VISIBLE);
        holder.tvTime.setText(CalendarUtils.getData(Long.parseLong(time),CalendarUtils.HOUR_MIN_AM_PM));
        if(isDeliveried) {
            holder.btnNotDelivered.setVisibility(View.GONE);
            holder.btnDelivered.setVisibility(View.VISIBLE);
            holder.btnDelivered.setEnabled(false);
        }
        else{
            holder.btnDelivered.setVisibility(View.GONE);
            holder.btnNotDelivered.setVisibility(View.VISIBLE);
            holder.btnNotDelivered.setEnabled(false);
        }
    }

    public void refresh(int position){
        selectedPos=position;
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
        public Button btnPickImage,btnRecordAudio,btnRecordVideo;
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

            btnPickImage=itemView.findViewById(R.id.btnPickImage);
            btnRecordAudio=itemView.findViewById(R.id.btnRecordAudio);
            btnRecordVideo=itemView.findViewById(R.id.btnRecordVideo);

            itemView.setLayoutParams(paramsParent);
        }
    }
}
