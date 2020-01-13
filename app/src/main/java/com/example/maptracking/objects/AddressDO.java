package com.example.maptracking.objects;

import java.io.Serializable;

public class AddressDO implements Serializable {
    public double latitude;
    public double longitude;
    public String mobileNumber;
    public String address;
    public String time;
    public String imagePath;
    public String audioPath;
    public String videoPath;
    public boolean isDelivered;
}
