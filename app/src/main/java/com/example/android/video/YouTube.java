package com.example.android.video;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by muhammad saalim wani on 30-12-2016.
 */

public class YouTube extends YouTubeBaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        try {
//            ActionBar actionBar = getActionBar();
//            // add the custom view to the action bar
//            actionBar.setCustomView(R.layout.toolbar);
//        }
//        catch (NullPointerException n){
//
//        }

       final  YouTubePlayerView youTubePlayerView;
       youTubePlayerView=(YouTubePlayerView)findViewById(R.id.youtube_view);

      //  Button play=(Button)findViewById(R.id.button);
      final  String text = getIntent().getStringExtra("keyName");
        String title=getIntent().getStringExtra("title");
        String description=getIntent().getStringExtra("description");
        TextView textView1=(TextView)findViewById(R.id.textView);
        textView1.setText(title);
        TextView textView2=(TextView)findViewById(R.id.textView2);
        textView2.setText(description);
        // UNIVERSAL IMAGE LOADER SETUP


        // Initialize ImageLoader with configuration.


        final YouTubePlayer.OnInitializedListener onInitializedListener=new YouTubePlayer.OnInitializedListener() {
        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

            youTubePlayer.loadVideo(text);
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        }

    };
youTubePlayerView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            youTubePlayerView.initialize(Player.API_KEY,onInitializedListener);

        }
    });



    }






}
