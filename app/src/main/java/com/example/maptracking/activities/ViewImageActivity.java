package com.example.maptracking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.maptracking.R;
import com.example.maptracking.common.AppConstants;
import com.example.maptracking.utilities.StringUtils;

import java.io.File;

public class ViewImageActivity extends AppCompatActivity {
    ImageView ivFile;
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        initControls();
    }

    private void initControls() {
        ivFile=findViewById(R.id.ivFile);
        videoView=findViewById(R.id.videoView);
        String imagePath=getIntent().getStringExtra(AppConstants.AddressKey);
        String videoPath=getIntent().getStringExtra(AppConstants.videoPath);
        if( !StringUtils.isNull(imagePath) && !TextUtils.isEmpty(imagePath)) {
            File imgFile = new File(imagePath);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                ivFile.setImageBitmap(myBitmap);
            }
        }
        else if(!StringUtils.isNull(videoPath) && !TextUtils.isEmpty(videoPath)){
            ivFile.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);

            MediaController mediaController=new MediaController(this);
            mediaController.setAnchorView(videoView);
            Uri uri=Uri.parse(videoPath);

            videoView.setMediaController(mediaController);
            videoView.setVideoURI(uri);
            videoView.requestFocus();
            videoView.start();
        }
    }
}
