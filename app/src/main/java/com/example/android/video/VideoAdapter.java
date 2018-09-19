package com.example.android.video;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by muhammad saalim wani on 30-12-2016.
 */

public class VideoAdapter extends ArrayAdapter<Video> {


    public VideoAdapter(Context context, ArrayList<Video> videos) {
        super(context, 0, videos);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View lisItemView = convertView;
        Video currentVideo = getItem(position);



        if (lisItemView == null) {
            lisItemView = LayoutInflater.from(getContext()).inflate(R.layout.video_list_item, parent, false);


        }

        TextView textView = (TextView) lisItemView.findViewById(R.id.title);

        textView.setText(currentVideo.getmTitle()+currentVideo.getmChannel()
        );
         ImageView imageView= (ImageView) lisItemView.findViewById(R.id.thumnail);
        Picasso.with(getContext())
                .load(currentVideo.getmImage())
                .resize(320,180)
                .centerCrop()
                .into(imageView);
 //       Picasso.with(getContext()).load(currentVideo.getmImage()).into(imageView);




    return lisItemView;

    }


}
