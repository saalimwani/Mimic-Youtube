package com.example.android.video;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayer;

import java.util.ArrayList;

/**
 * Created by muhammad saalim wani on 05-01-2017.
 */

public class about  extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);
  Button Video=(Button)findViewById(R.id.video);
  Video.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          Intent i = new Intent(about.this, MainActivity.class);
          startActivity(i);

      }
  });
        Button latest=(Button)findViewById(R.id.latest);
        latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(about.this,latest.class);
                startActivity(i);

            }
        });

//       final TextView textView=(TextView) findViewById(R.id.geekText);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String url="http:www.geeksforgeeks.org";
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
//
//            }
//        });


    }

}
