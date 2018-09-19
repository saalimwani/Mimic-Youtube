package com.example.android.video;

/**
 * Created by muhammad saalim wani on 30-12-2016.
 */

public class
Video {


        String mUrl;
        String mTitle;
        String mImage;
        String mDescription;
//        String mChannel="badminton crazy";
        public Video(String url,String title,String image,String description )
        {
            mUrl=url;
            mTitle=title;
            mImage=image;
                mDescription=description;
        }


        public String getmUrl()
        {return  mUrl;}
        public String getmTitle()
        {return mTitle;}
        public String getmImage()
        {return  mImage;}
        public String getmDescription()
        {return  mDescription;}
    public String getmChannel()
    {return  "";}
}



