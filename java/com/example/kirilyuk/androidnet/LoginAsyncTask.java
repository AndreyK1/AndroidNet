package com.example.kirilyuk.androidnet;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Kirilyuk on 14.12.2015.
 */
public class LoginAsyncTask extends AsyncTask<String, Void, Boolean> {

    String charset = "UTF-8";
    Dialog loadingDialog;
    String result1;
    public MainActivity context;


    public LoginAsyncTask(MainActivity a)
    {
        this.context = a;
    }

    @Override
    protected void onPreExecute() {
        context.pDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... sURL) {
        StringBuilder sbParams = new StringBuilder();
        URL urlObj = null;
        HttpURLConnection conn = null;
        StringBuilder result = new StringBuilder();
        Log.v("My Project conn.error", "try");

        try {
           // context.loginTxt.setText(sURL[0]);
            Log.v("My Project conn.reader",String.valueOf(sURL[0]) );
            urlObj = new URL(sURL[0]);
            conn = (HttpURLConnection) urlObj.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Charset", charset);
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
           // conn.connect();

/*
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("firstParam", paramValue1));
            params.add(new BasicNameValuePair("secondParam", paramValue2));
            params.add(new BasicNameValuePair("thirdParam", paramValue3));
*/
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("email", "111")
                    .appendQueryParameter("password", "222");
            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            conn.connect();

/*
            int code = conn.getResponseCode();
            Log.v("My Project conn.error",String.valueOf(code) );
            String paramsString = sbParams.toString();

            DataOutputStream wr;
            wr = new DataOutputStream(conn.getOutputStream());
            //wr.writeBytes(paramsString);
            wr.flush();
            wr.close();
*/
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            //response from the server
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            Log.v("My Project conn.reader",String.valueOf(reader) );
           // context.loginTxt.setText(line);

        } catch (IOException e) {
            e.printStackTrace();
        }

        conn.disconnect();

        result1 = result.toString();
        return true;
    }


    protected void onPostExecute(Boolean success) {
        if (!success) {
            return;
        }
        Log.v("My Project onPostExecute.result",String.valueOf(result1) );
        StringBuilder tweetResultBuilder = new StringBuilder();
        try {
            //  ArrayList<HashMap<String, String>> uslist = new ArrayList<HashMap<String, String>>();


            JSONObject resultObject = new JSONObject(result1);
            JSONArray tweetArray = resultObject.getJSONArray("data");
         /*
            if(tweetArray.length() >0) {
                for (int t = 0; t < tweetArray.length(); t++) {

                    JSONObject tweetObject = tweetArray.getJSONObject(t);
                    ArrayList<String> us = new ArrayList<String>();
                    us.add(String.valueOf(tweetObject.get("id") + " - " + String.valueOf(tweetObject.get("email"))));
                    us.add(String.valueOf(tweetObject.get("foto")));
                    us.add(String.valueOf(tweetObject.get("foto")));
                    UsersArrays.add(us);
                }
            }else{
                context.usersEnded = true;
            }
    */
     /*
            ListView UsersList1 = (ListView) context.findViewById(R.id.listView);
            //useLadapter = new UserListAdapter((MainActivity) context, emailsArr, passesArr,fotosArr);
            // useLadapter = new UserListAdapter((MainActivity) context, emails, passes,passes);

            if(context.SkipUsers==0) {//если мы впервые загружаем список
                useLadapter = new UserListAdapter((MainActivity) context, R.layout.list_users, UsersArrays);
                //          UsersList1.setAdapter(useLadapter);
                UsersList1.setAdapter((UserListAdapter) ((BaseAdapter) useLadapter));
            }else{//если добавляем
                ((UserListAdapter)((BaseAdapter)UsersList1.getAdapter())).notifyDataSetChanged();
            }
*/
        } catch (Exception e) {
            // tweetDisplay.setText("Whoops - something went wrong!");
            e.printStackTrace();
        }

        context.pDialog.dismiss();
    }

}