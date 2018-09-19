package com.example.android.video;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Bundle;


import android .support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;//AIzaSyAhA7kffO3zHjtySFG77ppjQaRCX-8CQEc

public class MainActivity extends AppCompatActivity {
//    YouTubePlayerView youTubePlayerView;
    Button play;
  //  String s="&q=Mufti+Taqi+Usmani";
    VideoAdapter Adapter;

  //  https://www.googleapis.com/youtube/v3/channels?part=ContentDetails&forUsername=GoogleDevelopers&key={AIzaSyAhA7kffO3zHjtySFG77ppjQaRCX-8CQEc}
   String USGS_REQUEST_URL=" https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCe_vXdMrHHseZ_esYUskSBw&maxResults=49&key=AIzaSyAhA7kffO3zHjtySFG77ppjQaRCX-8CQEc";
    static ArrayList<Video> VideoArray = new ArrayList<Video>();
    YouTubePlayer.OnInitializedListener onInitializedListener;//tell me if youTUBE PLAYER IS INTILIZED

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_list);

        ListView listView = (ListView) findViewById(R.id.list);
         Adapter = new VideoAdapter(this,new ArrayList<Video>());
        listView.setAdapter(Adapter);
        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Video current = Adapter.getItem(position);//earthquake.get(position)
//
//            }
//        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Video current = Adapter.getItem(position);//earthquake.get(position)
                Intent i = new Intent(MainActivity.this, YouTube.class);
                i.putExtra("keyName", current.getmUrl());
                i.putExtra("description",current.getmDescription());
                i.putExtra("title",current.getmTitle());
                startActivity(i);

            }


        });

        Button home=(Button)findViewById(R.id.Home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,about.class);
                startActivity(i);

            }
        });
        Button latest=(Button)findViewById(R.id.latest);
        latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,latest.class);
                startActivity(i);

            }
        });
        VideoAsyncTask task = new VideoAsyncTask();
        task.execute(USGS_REQUEST_URL);





        ActionBar actionBar =getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        // actionBar.setIcon(R.drawable.ic_action_search);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.search, null);

        actionBar.setCustomView(v);
        final  EditText editText = (EditText) findViewById(R.id.search_query);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // performSearch();
                    String a = editText.getText().toString();
                    final   String query =a.replace(" ","+");
                    new VideoAsyncTask().execute(USGS_REQUEST_URL+"&q="+query);
                    return true;
                }
                return false;
            }
        });
        editText.setVisibility(View.INVISIBLE);
        final ImageView searchIcon=(ImageView) findViewById(R.id.search);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setVisibility(View.VISIBLE);
            }
        });

//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView absListView, int i) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if ((firstVisibleItem + visibleItemCount) == totalItemCount && firstVisibleItem != 0) {
//                    VideoAsyncTask task3 = new VideoAsyncTask();
//                    task3.execute(USGS_REQUEST_URL + "&pageToken=" + Utils.nextPageToken);
//                }
//            }
//        });

//

     listView.setOnScrollListener(new AbsListView.OnScrollListener(){
         private int  mLastFirstVisibleItem;
         private int mfirstVisibleItem;
         private int mVisbleItemCount;
         private int mTotalitemCount;
         boolean   mIsScrollingUp;
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if((mfirstVisibleItem+mVisbleItemCount)==mTotalitemCount&&firstVisibleItem!=0) {
                    VideoAsyncTask task3 = new VideoAsyncTask();
                    new VideoAsyncTask().execute(USGS_REQUEST_URL + "&pageToken=" + Utils.nextPageToken);
                }
                // TODO Auto-generated method stub
//                mfirstVisibleItem=firstVisibleItem;
//                mfirstVisibleItem=visibleItemCount;
//                mTotalitemCount=totalItemCount;
//                final ListView lw =(ListView) findViewById(R.id.list);
//
//
//
//
//                if (view.getId() == lw.getId()) {
//                    final int currentFirstVisibleItem = lw.getFirstVisiblePosition();
//                    if (currentFirstVisibleItem > mLastFirstVisibleItem) {
//                        mIsScrollingUp = false;
//                        Log.i("a", "scrolling down...");
//
//                    } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
//                        mIsScrollingUp = true;
//                        Log.i("a", "scrolling up...");
//                        VideoAsyncTask task3 = new VideoAsyncTask();
//                        task3.execute(USGS_REQUEST_URL + "&pageToken=" + Utils.prevPageToken);
//                    }
//
//                    mLastFirstVisibleItem = currentFirstVisibleItem;
//                }
            }
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub

            }
        });

//    aLTERNATIVE     editText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(query!=null)
//                    new VideoAsyncTask().execute(USGS_REQUEST_URL+"&q="+query);
//
//
//            }
//        });

        }
    private class VideoAsyncTask extends AsyncTask<String, Void, ArrayList<Video>>

    {

        @Override
        protected ArrayList<Video> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            return Utils.fetchVideoData(urls[0]);

        }

        @Override
        protected void onPostExecute(ArrayList<Video> data) {
            Adapter.clear();
            if (data != null && !data.isEmpty()) {
                {
                    Adapter.addAll(data);
                }
            }
            // private void updateUi(ArrayList<Earthquake> earthquake) {


        }
    }
    }

