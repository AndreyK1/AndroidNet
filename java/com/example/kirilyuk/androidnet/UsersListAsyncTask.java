package com.example.kirilyuk.androidnet;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.*;
import java.io.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kirilyuk on 05.12.2015.
 */
public class UsersListAsyncTask extends AsyncTask<String, Void, Boolean> {

        String charset = "UTF-8";
        Dialog loadingDialog;
        String result1;
        public MainActivity context;
       UserListAdapter useLadapter;
    //   ArrayList<ArrayList<String>> UsersArrays = new ArrayList<ArrayList<String>>();
    ArrayList<UserListModel> UsersArrays; // = new ArrayList<UserListModel>();

      //  public UsersListAsyncTask(MainActivity a,UserListAdapter useLadapter,ArrayList<ArrayList<String>> UsersArrays)
      public UsersListAsyncTask(MainActivity a,UserListAdapter useLadapter,ArrayList<UserListModel> UsersArrays)
        {
            this.context = a;
            this.useLadapter = useLadapter;
            this.UsersArrays = UsersArrays;
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
                urlObj = new URL(sURL[0]);
                conn = (HttpURLConnection) urlObj.openConnection();
                //       conn.setDoOutput(true);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept-Charset", charset);
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.connect();

                int code = conn.getResponseCode();
                Log.v("My Project conn.error",String.valueOf(code) );
                String paramsString = sbParams.toString();

                DataOutputStream wr;
                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();

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
                if(tweetArray.length() >0) {
                    for (int t = 0; t < tweetArray.length(); t++) {
                        /* 02 12 15*/
                        JSONObject tweetObject = tweetArray.getJSONObject(t);
            /*            ArrayList<String> us = new ArrayList<String>();
                        us.add(String.valueOf(tweetObject.get("id") + " - " + String.valueOf(tweetObject.get("email"))));
                        us.add(String.valueOf(tweetObject.get("foto")));
                        us.add(String.valueOf(tweetObject.get("foto")));
              */
                        UserListModel us = new UserListModel(String.valueOf(tweetObject.get("id"))+String.valueOf(tweetObject.get("email")),String.valueOf(tweetObject.get("foto")),String.valueOf(tweetObject.get("foto")));
                        UsersArrays.add(us);
                        Log.v("My Project UsersArrays.add","UsersArrays.add");
                    }

                }else{
                    context.usersEnded = true;
                }
               ListView UsersList1 = (ListView) context.findViewById(R.id.listView);
                //useLadapter = new UserListAdapter((MainActivity) context, emailsArr, passesArr,fotosArr);
               // useLadapter = new UserListAdapter((MainActivity) context, emails, passes,passes);
/*
            if(context.SkipUsers==0) {//если мы впервые загружаем список
                useLadapter = new UserListAdapter((MainActivity) context, R.layout.list_users, UsersArrays);
                //          UsersList1.setAdapter(useLadapter);
                UsersList1.setAdapter((UserListAdapter) ((BaseAdapter) useLadapter));
            }else{//если добавляем
                ((UserListAdapter)((BaseAdapter)UsersList1.getAdapter())).notifyDataSetChanged();

            }
*/
            //    ((UserListAdapter)((BaseAdapter)UsersList1.getAdapter())).notifyDataSetChanged();

                //useLadapter.notifyDataSetChanged();
                UsrListFragment myFragmentUsers = (UsrListFragment) context.supportFragmentManager.findFragmentByTag("main");
                ((UserListAdapter)((BaseAdapter)((UsrListFragment)myFragmentUsers).useLadapter)).notifyDataSetChanged();
              // myFragmentUsers.getListView().refreshDrawableState();
                //myFragmentUsers.getListView().setSelection(messages.size()-1);
                //myFragmentUsers.getListView().setNotifyDataChanged();
                      // .invalidateViews();

                context.supportFragmentManager.beginTransaction()
                        //fragmentTransaction
                        .show(myFragmentUsers).commit();

            } catch (Exception e) {
                // tweetDisplay.setText("Whoops - something went wrong!");
                e.printStackTrace();
            }

            context.pDialog.dismiss();
        }

    }

