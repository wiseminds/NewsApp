package com.example.android.mynewsapp;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownServiceException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * {@link QueryGuardian} is a class to query the guardian site to get a JSON response
 * and parse the response to the caller which is  NewsAsynchronousLoader class.
 * in essense this class rus in the background.
 */

public final class QueryGuardian {

    /**
     * a private constructor because no one should ever create a {@link QueryGuardian} object
     */
    private QueryGuardian() {
    }


    public static ArrayList<News> extractNews(String jsonResponse) {
        ArrayList<News> newsInstances = new ArrayList<>();
        if (jsonResponse == null) {
            return null;
        } else

            Log.v("QueryGuardian", "extractNews");
        try {
            Log.v("QueryGuardian", "extractNews try");
            JSONObject rootJSONobject = new JSONObject(jsonResponse);
            JSONObject response = rootJSONobject.getJSONObject("response");
            String status = response.getString("status");
            if (status.equalsIgnoreCase("ok")) {
                JSONArray resultsArray = response.getJSONArray("results");
                resultsArray.length();
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject results = resultsArray.getJSONObject(i);
                    String sectionName = results.getString("sectionName");
                    String webTitle = results.getString("webTitle");
                    String webUrl = results.getString("webUrl");
                    String webPublicationDate = results.getString("webPublicationDate");
                    newsInstances.add(new News(sectionName, webTitle, webUrl, webPublicationDate));
                    Log.v("QueryGuardian", resultsArray.length() + " parsin json " + sectionName + "" + i);

                }
            }
        } catch (JSONException e) {
            Log.v("QueryGuardian", "catch JsonExeption ", e);
        }
        return newsInstances;
    }

    /**
     * uses a HttpURLConnection object {@link HttpURLConnection} to query the url
     * with a GET request method to get the JSON response from the Guardian site
     * if connection status returns OK, the inputStream is parsed otherwise
     * the method returns null.
     */

    public static String makeHttpRequest(URL url, Context context)
            throws SocketTimeoutException, UnknownServiceException {

        String jsonResponse = "";
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            return "No INTERNET CONNECTION";
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            /*
             *{ inputStream = context.getApplicationContext().getAssets().open("response.txt");}
             * use the above code to test the app if there is no internet connection
             * in the asset folder a sample JSON response in included for test purpose
             * so you have to comment out @param urlConnection.connect(),
             * and urlConnection.getResponseCode(), comment out the if statement, and in
             * its place, use "jsonResponse = readFromstream(inputStream)"
             */
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(1000/*milliseconds*/);
            urlConnection.setConnectTimeout(3000/*milliseconds*/);
            Log.v("http", " connect" + url);
            if (networkInfo != null) {
                urlConnection.connect();
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.v("http", " connected, get inputStream" + url);
                    inputStream = urlConnection.getInputStream();
                    Log.v("http", " read inputStream" + url);
                    jsonResponse = readFromStream(inputStream);
                }
            }
        } catch (IOException e) {
            Log.v("http catch", " " + url, e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.v("http catch", " ", e);
                }
            }
        }
        return jsonResponse;
    }

    /*
    *reads the inputstream from the http connection and appends to a string variable
    * a string builder is used to build the string from the Buffered reader.
    * the buffered reader reads one line at a time
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = reader.readLine();
            }
        }
        if (stringBuilder.length() == 0) {
            return null;
        }
        return stringBuilder.toString();
    }
}