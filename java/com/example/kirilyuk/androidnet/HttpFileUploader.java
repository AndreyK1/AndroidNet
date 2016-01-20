package com.example.kirilyuk.androidnet;

import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Kirilyuk on 11.01.2016.
 */
public class HttpFileUploader  extends AsyncTask<String, Void, Boolean> {

    String charset = "UTF-8";
    Dialog loadingDialog;
    String result1;
    public MainActivity context;
    String filepath;


    public HttpFileUploader(MainActivity a, String filepath)
    {

        this.context = a;
        this.filepath=filepath;
    }

    @Override
    protected void onPreExecute() {

        //context.pDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... sURL) {



        try {
            // Set your file path here
            FileInputStream fstrm = new FileInputStream(filepath);
            Log.v("My project file.getAbsolutePath()", filepath);

            // Set your server page url (and the file title/description)
            HttpFileUpload hfu = new HttpFileUpload(context, sURL[0], "my file title",context.user.get(0));

            hfu.Send_Now(fstrm);

        } catch (FileNotFoundException e) {

            // Error: File not found
        }
    /*
        StringBuilder sbParams = new StringBuilder();
        URL urlObj = null;
        HttpURLConnection conn = null;
        StringBuilder result = new StringBuilder();
        Log.v("My Project conn.error", "try");

        try {
            Log.v("My project URL", sURL[0]);
            urlObj = new URL(sURL[0]);
            conn = (HttpURLConnection) urlObj.openConnection();
            //       conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept-Charset", charset);
            //String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImRkZDEiLCJwYXNzd29yZCI6InNkc2QiLCJpYXQiOjE0NDM1OTkwNzl9._MR8W6TNYyLhvesnWar740plCEQwtiAcp2U5yvjJ84w";
            String accessToken = context.user.get(3);
            //conn.setRequestProperty("authorization", "Token token="
            conn.setRequestProperty("authorization", "Bearer "
                    + accessToken);
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.connect();


            int responseCode = conn.getResponseCode();
            Log.v("My Project connResponse Code :", String.valueOf(responseCode));
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try {
                    //response from the server
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    Log.v("My Project conn.reader",String.valueOf(reader) );

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        conn.disconnect();

        result1 = result.toString();

        */
        return true;
    }


    protected void onPostExecute(Boolean success) {
        if (!success) {
            return;
        }
       /*
        Log.v("My Project onPostExecute.result",String.valueOf(result1) );
        StringBuilder tweetResultBuilder = new StringBuilder();
        try {
            JSONObject resultObject = new JSONObject(result1);
            JSONObject tweetObject = resultObject.getJSONObject("data");
            //JSONArray tweetArray = resultObject.getJSONArray("data");
            Log.v("My Project tweetObject.user",String.valueOf(tweetObject.get("email")) );
            if(tweetObject.length() >0) {
                context.user.clear();
                context.user.add(String.valueOf(tweetObject.get("id")));
                context.user.add(String.valueOf(tweetObject.get("email")));
                context.user.add(String.valueOf(tweetObject.get("foto")));
                context.user.add(String.valueOf(tweetObject.get("token")));
                Log.v("My Project context.user",String.valueOf(context.user.get(0)) );

            }else{
                context.usersEnded = true;
            }


        } catch (Exception e) {
            // tweetDisplay.setText("Whoops - something went wrong!");
            e.printStackTrace();
        }
        context.ShowAutorizedUser();
        context.pDialog.dismiss();
        */
    }

}