package com.example.maptracking.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.example.maptracking.R;

public class RoutesListActivity extends AppCompatActivity {
    String PERMISSIONS[]=new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.CALL_PHONE
            ,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_list);
        requestAppPermissions();
    }

    private void requestAppPermissions() {
        int isPermissionGranted= PackageManager.PERMISSION_GRANTED;
        boolean isPermissionRatinale=false;
        for(String permission:PERMISSIONS){
            isPermissionGranted+= ActivityCompat.checkSelfPermission(getApplicationContext(),permission);
            isPermissionRatinale=ActivityCompat.shouldShowRequestPermissionRationale(this,permission);
        }
        if (isPermissionGranted!=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,PERMISSIONS, 100);
        }
    }

    public void moveToRoute1(View view) {
        Intent intent=new Intent(this, MainActiviy.class);
        startActivity(intent);
    }
}
