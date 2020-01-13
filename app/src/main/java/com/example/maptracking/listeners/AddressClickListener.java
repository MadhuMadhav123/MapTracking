package com.example.maptracking.listeners;

import com.example.maptracking.objects.AddressDO;

public interface AddressClickListener {
    void onAddressClick(AddressDO addressDO, int position);
    void onPhoneCall(AddressDO addressDO,int position,int type,int isView);
}
