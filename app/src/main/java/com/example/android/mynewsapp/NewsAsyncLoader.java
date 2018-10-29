package com.example.android.mynewsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownServiceException;
import java.util.ArrayList;

import static android.R.attr.y;


/**
 * extends {@link  android.support.v4.content.AsyncTaskLoader}
 */

public class NewsAsyncLoader extends
        android.support.v4.content.AsyncTaskLoader<ArrayList<News>> {
    private static URL url;
    public NewsAsyncLoader(Context context) {
        super(context);
    }

    /*
    * {@link setURL } is called to Create a URL object from a url string
    * if the string does not fit a url format, an exception is thrown{@link MalformedURLException}
    */
    public static void setURL(String Url) {
        try {
            url = new URL(Url);
            Log.v("AsyncLoader", "loadinBackground" + url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * runs on a background thread and returns to onLoadFinished in the main thread
     * {@link ConnectivityManager} Class that answers queries about the state of network connectivity. It also
     * notifies applications when network connectivity changes.
     * Describes the status of a network interface.
     * <p> {@link ConnectivityManager#getActiveNetworkInfo()} is used to get an instance that represents
     * the current network connection.
     * it queries the url with the get method to get a JSON string which is parsed and stored in an array
     * and retured back.
     * {@link QueryGuardian#makeHttpRequest(URL)} throws 2 exceptions which are caught here
     */
    @Override
    public ArrayList<News> loadInBackground() {
        Log.v("AsyncLoader", "loadinBackground");
        ArrayList<News> newsArrayList = new ArrayList<>();
        ConnectivityManager connectivityManager = (ConnectivityManager)
              getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        String jsonResponse;
        if (networkInfo == null){
            return null;
        } else
        if (networkInfo != null ) {

            try {
                jsonResponse = QueryGuardian.makeHttpRequest(url);
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
                Log.v("QueryGuardian", "makeHttpRequest ", e);
                return new ArrayList<News>();
            } catch (UnknownServiceException e) {
                e.printStackTrace();
                Log.v("QueryGuardian", "protocol doesn't support input", e);
                return new ArrayList<News>();
            }
            Log.v("QueryGuardian", "makeHttpRequest " + jsonResponse);
            if (jsonResponse != null) {
                newsArrayList = QueryGuardian.extractNews(jsonResponse);
            } else if (jsonResponse == null) {
                newsArrayList = new ArrayList<News>();
            }

    }
        return newsArrayList;
    }
}
