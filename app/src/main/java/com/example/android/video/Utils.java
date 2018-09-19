
package com.example.android.video;
import android.text.TextUtils;
import android.util.Log;

import com.example.android.video.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class Utils {

    public static final String LOG_TAG = Utils.class.getSimpleName();

    /**
     * Sample JSON response for a USGS query
     */
    private static final String SAMPLE_JSON_RESPONSE = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=json&eventtype=earthquake&orderby=time&minmag=6&limit=10";
   public static  String nextPageToken=null;
    public static String prevPageToken=null;
    /**
     * Create a private constructor because no one should ever create a {@link Utils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private Utils() {
    }

    /**
     * Return a list of {@link Video} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Video> fetchVideoData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        ArrayList<Video> Videos = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return Videos;
    }
    public static ArrayList<Video> extractFeatureFromJson(String VideoJSON) {
        if (TextUtils.isEmpty(VideoJSON)) {
            return null;
        }
        ArrayList<Video> Videos= new ArrayList<>();
        // Create an empty ArrayList that we can start adding earthquakes to

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject baseJasonObject = new JSONObject(VideoJSON);
            if(baseJasonObject.has("nextPageToken"))
            {nextPageToken=baseJasonObject.getString("nextPageToken");

            }
            if(baseJasonObject.has("prevPageToken"))
            {prevPageToken=baseJasonObject.getString("prevPageToken");

            }
            JSONArray VideoArray = baseJasonObject.optJSONArray("items");
            for (int i = 0; i < VideoArray.length(); i++) {
                JSONObject currentItem = VideoArray.optJSONObject(i);
                JSONObject properties = currentItem.getJSONObject("id");
                String id=properties.getString("videoId");
                JSONObject snippet=currentItem.getJSONObject("snippet");
                JSONObject thumbnails=snippet.getJSONObject("thumbnails");
                String descrition= snippet.getString("description");
                JSONObject defaul=thumbnails.getJSONObject("high");
                String   imag=defaul.getString("url");
                String title = (snippet.getString("title"));

                Videos.add(new Video(id,title,imag,descrition));
            }

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing theVideo JSON results", e);
        }

        // Return the list of earthquakes
        return Videos;
    }

    private static URL createUrl(String url) {
        URL a = null;
        try {
            a = new URL(url);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return a;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponce = "";
        InputStream inputstream = null;
        HttpURLConnection urlConnection = null;
        if (url == null)
            return null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputstream = urlConnection.getInputStream();
                jsonResponce = readFromStream(inputstream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException exception) {
            Log.e(LOG_TAG, "Error response code: " + exception);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputstream != null) {
                inputstream.close();
            }
        }
        return jsonResponce;

    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        InputStreamReader inputStreamReader=null;

        if (inputStream != null) {
            inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));}
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine();
        while(line != null) {
            output.append(line);
            line = bufferedReader.readLine();
        }
        return output.toString();
    }
}















