package com.example.notes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity
{
    //public static String pw,pass="pass";
    private static final int SPLASH_DELAY = 5000; // 5 seconds


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Window window=this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));

        // Reference the VideoView from the XML layout
        VideoView videoView = findViewById(R.id.videoView);


        // Set the path or URL of the video file
        String videoPath = "android.resource://" + getPackageName() + "/" +R.raw.openvid; // Change "noteeasy" to the actual video file name


        // Set a MediaController to enable video playback controls
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);


        // Set the video URI
        videoView.setVideoURI(Uri.parse(videoPath));


        // Start playing the video
        videoView.start();


        new Handler().postDelayed(() ->
        {
            SharedPreferences preferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            boolean isFirstTimeLaunch = preferences.getBoolean("isFirstTimeLaunch", true);

            Intent i;
            if(isFirstTimeLaunch)
            {
                i = new Intent(MainActivity.this, create_pass.class);
                preferences.edit().putBoolean("isFirstTimeLaunch", false).apply();
            }
            else
            {
                i = new Intent(MainActivity.this, opening.class);
            }
            startActivity(i);
            finish();
        }, SPLASH_DELAY);
    }
}
