package com.example.maptracking.utilities;

import android.os.Environment;

public class FileUtils {
    private final static String rootFolderName="MyMapTrack";
    private final static String imagesFolderName="Images";
    private final static String audioFolderName="Audio";
    private final static String videoFolderName="Videos";
    public final static String IMG_JGP_EXTENSION=".jpg";
    public final static String VIDEO_EXTENSION=".mp4";
    public final static String AUDIO_EXTENSION=".mp3";  //3gp   mp3
    public static String getRootDirectoryForImages(){
        return Environment.getExternalStorageDirectory()+"/"+rootFolderName+"/"+imagesFolderName+"/";
    }
    public static String getRootDirectoryForAudio(){
        return Environment.getExternalStorageDirectory()+"/"+rootFolderName+"/"+audioFolderName+"/";
    }
    public static String getRootDirectoryForVideo(){
        return Environment.getExternalStorageDirectory()+"/"+rootFolderName+"/"+videoFolderName+"/";
    }
}

