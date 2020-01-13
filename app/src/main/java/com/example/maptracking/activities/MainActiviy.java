package com.example.maptracking.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maptracking.BuildConfig;
import com.example.maptracking.listeners.AddressClickListener;
import com.example.maptracking.objects.AddressDO;
import com.example.maptracking.adapters.AddressListAdapter;
import com.example.maptracking.common.AppConstants;
import com.example.maptracking.utilities.CalendarUtils;
import com.example.maptracking.R;
import com.example.maptracking.utilities.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class MainActiviy extends AppCompatActivity {
    TextView tvHeader;
    RecyclerView rvAddressList;
    AddressListAdapter addressListAdapter;
    Vector<AddressDO>vecAddress=new Vector<>();
    private File fileSave;
    Uri imageUri;
    String selectedImagePath;
    String audioSavePath;
    int selectedPosition;
    MediaPlayer mediaPlayer;
    MediaRecorder mediaRecorder; //Added Comment
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControls();
        setDateToHeader();
    }
    private void initControls() {
        tvHeader=findViewById(R.id.tvHeader);
        rvAddressList=findViewById(R.id.rvAddressList);
        rvAddressList.setHasFixedSize(true);
        prepareData();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rvAddressList.setLayoutManager(linearLayoutManager);

        addressListAdapter=new AddressListAdapter(getApplicationContext(),vecAddress, new AddressClickListener() {
            @Override
            public void onAddressClick(AddressDO addressDO,int possition) {
                moveToMap(addressDO);
            }

            @Override
            public void onPhoneCall(AddressDO addressDO,int position,int type,int isView) {
                selectedPosition=position;
                switch (type){
                    case AppConstants.PHONE:
                        makePhoneCall(addressDO.mobileNumber);
                        break;
                    case AppConstants.CAMERA_IMAGE:
                        if (isView==AppConstants.VIEW_FILE)
                            movieToVoewFile(addressDO,true);
                        else
                            pickImage();
                        break;
                    case AppConstants.AUDIO:
                        if (isView==AppConstants.CAPTURE_FILE)
                            recordAudio();
                        else if(isView==AppConstants.STOP_RECORDING){
                            mediaRecorder.stop();
                            showToast("Recording Stopped...");
                            vecAddress.get(selectedPosition).audioPath=audioSavePath;
                            addressListAdapter.refresh(selectedPosition);
                        }
                        else if (isView==AppConstants.VIEW_FILE)
                            playAudio(addressDO.audioPath);
                        break;
                    case AppConstants.VIDEO:
                        if (isView==AppConstants.VIEW_FILE)
                            movieToVoewFile(addressDO,false);
                        else
                            pickVideo();
                        break;
                }
            }
        });
        rvAddressList.setAdapter(addressListAdapter);
    }

    private void playAudio(String audioPath) {
        mediaPlayer=new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioPath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void movieToVoewFile(AddressDO addressDO,boolean isImagePath) {
        Intent intent=new Intent(this,ViewImageActivity.class);
        if(isImagePath)
            intent.putExtra(AppConstants.AddressKey,addressDO.imagePath);
        else
            intent.putExtra(AppConstants.videoPath,addressDO.videoPath);
        startActivity(intent);
    }

    private void pickVideo() {
        String imageNam = "" + System.currentTimeMillis() + FileUtils.VIDEO_EXTENSION;
        String rootDirectory= FileUtils.getRootDirectoryForVideo();
        File fileRoot=new File(rootDirectory);
        if (!fileRoot.exists())
            fileRoot.mkdirs();
        fileSave = new File(fileRoot.getPath(), imageNam);
        Intent intent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        imageUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", fileSave);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent,AppConstants.VIDEO);
    }
    private void pickImage() {
        String imageNam = "" + System.currentTimeMillis() + FileUtils.IMG_JGP_EXTENSION;
        String rootDirectory= FileUtils.getRootDirectoryForImages();
        File fileRoot=new File(rootDirectory);
        if (!fileRoot.exists())
            fileRoot.mkdirs();
        fileSave = new File(fileRoot.getPath(), imageNam);
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", fileSave);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent,AppConstants.CAMERA_IMAGE);
    }
    private void recordAudio(){
        String rootPath=FileUtils.getRootDirectoryForAudio();
        audioSavePath=rootPath+System.currentTimeMillis() + FileUtils.AUDIO_EXTENSION;
        File fileRoot=new File(rootPath);
        if (!fileRoot.exists())
            fileRoot.mkdirs();
        initMediaRecorder(audioSavePath);
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            showToast("Recording started...Speak");
        } catch (IOException e) {
            e.printStackTrace();
            showToast("Error in starting Media Recorder");
        }
    }
    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
    private void initMediaRecorder(String audioSavePath){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(audioSavePath);
    }
    private void makePhoneCall(String mobileNumber) {
        Intent intent=new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+mobileNumber));
        startActivity(intent);
    }

    private void setDateToHeader() {
        tvHeader.setText(CalendarUtils.getCurrentDateAndtime(CalendarUtils.DATE_MONTH_FULL_YEAR));
    }
    public void moveToMap(AddressDO addressDO) {
        Intent intent=new Intent(this,MapActivity.class);
        Bundle b=new Bundle();
        b.putSerializable(AppConstants.AddressKey,addressDO);
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case AppConstants.CAMERA_IMAGE:
                    selectedImagePath=fileSave.getPath();
                    vecAddress.get(selectedPosition).imagePath=selectedImagePath;
                    addressListAdapter.refresh(selectedPosition);
                    break;
                case AppConstants.VIDEO:
                    selectedImagePath=fileSave.getPath();
                    vecAddress.get(selectedPosition).videoPath=selectedImagePath;
                    addressListAdapter.refresh(selectedPosition);
                    break;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
        }
        if(mediaRecorder!=null){
            mediaRecorder.release();
            mediaRecorder=null;
        }
    }

    private void prepareData() {
        AddressDO addressDO1=new AddressDO();
        addressDO1.latitude=17.3616;
        addressDO1.longitude=78.4747;
        addressDO1.address="Charminar";
        addressDO1.isDelivered=true;
        addressDO1.time="1578391670783";
        addressDO1.mobileNumber="8179983589";

        AddressDO addressDO2=new AddressDO();
        addressDO2.latitude=17.2641;
        addressDO2.longitude=78.6816;
        addressDO2.address="Ramogifilmcity";
        addressDO2.mobileNumber="8179983589";

        AddressDO addressDO3=new AddressDO();
        addressDO3.latitude=17.4851;
        addressDO3.longitude=78.4116;
        addressDO3.address="Kukatpally";
        addressDO3.mobileNumber="8179983589";

        AddressDO addressDO4=new AddressDO();
        addressDO4.latitude=17.5287;
        addressDO4.longitude=78.2641;
        addressDO4.address="Patancheru Bus Terminal";
        addressDO4.mobileNumber="8179983589";

        AddressDO addressDO5=new AddressDO();
        addressDO5.latitude=17.3616;
        addressDO5.longitude=78.4747;
        addressDO5.address="Charminar";
        addressDO5.mobileNumber="8179983589";

        AddressDO addressDO6=new AddressDO();
        addressDO6.latitude=17.2641;
        addressDO6.longitude=78.6816;
        addressDO6.address="Ramogifilmcity";
        addressDO6.mobileNumber="8179983589";

        AddressDO addressDO7=new AddressDO();
        addressDO7.latitude=17.4851;
        addressDO7.longitude=78.4116;
        addressDO7.address="Kukatpally";
        addressDO7.mobileNumber="8179983589";

        AddressDO addressDO8=new AddressDO();
        addressDO8.latitude=17.5287;
        addressDO8.longitude=78.2641;
        addressDO8.isDelivered=false;
        addressDO8.time="1578393731950";
        addressDO8.address="Patancheru Bus Terminal";
        addressDO8.mobileNumber="8179983589";

        vecAddress.add(addressDO1);
        vecAddress.add(addressDO2);
        vecAddress.add(addressDO3);
        vecAddress.add(addressDO4);
        vecAddress.add(addressDO5);
        vecAddress.add(addressDO6);
        vecAddress.add(addressDO7);
        vecAddress.add(addressDO8);
    }
}
