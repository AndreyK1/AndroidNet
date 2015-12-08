package com.example.kirilyuk.androidnet;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
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
   // private class GetUsersTask extends AsyncTask<String, Void, String> {

        // TextView tV;
        String charset = "UTF-8";
        Dialog loadingDialog;
        String result1;
        public MainActivity context;
       UserListAdapter useLadapter;
         ArrayList<ArrayList<String>> UsersArrays = new ArrayList<ArrayList<String>>();

        public UsersListAsyncTask(MainActivity a,UserListAdapter useLadapter,ArrayList<ArrayList<String>> UsersArrays)
        {
            this.context = a;
            this.useLadapter = useLadapter;
            this.UsersArrays = UsersArrays;
        }

        @Override
        protected Boolean doInBackground(String... sURL) {
            // tV = (TextView) findViewById(R.id.tV);
           /*
            StringBuilder UsersFeedBuilder = new StringBuilder();
            for (String searchURL : sURL) {
                HttpClient tweetClient = new DefaultHttpClient();

                try {
                    HttpGet tweetGet = new HttpGet(searchURL);
                    HttpResponse tweetResponse = tweetClient.execute(tweetGet);
                    StatusLine searchStatus = tweetResponse.getStatusLine();
                    if (searchStatus.getStatusCode() == 200) {
                        HttpEntity tweetEntity = tweetResponse.getEntity();
                        InputStream tweetContent = tweetEntity.getContent();

                        InputStreamReader tweetInput = new InputStreamReader(
                                tweetContent);
                        BufferedReader tweetReader = new BufferedReader(
                                tweetInput);
                        String lineIn;
                        while ((lineIn = tweetReader.readLine()) != null) {
                            UsersFeedBuilder.append(lineIn);
                        }
                    } else {
                        tV.setText("Whoops - something went wrong!");
                    }
                } catch (Exception e) {
                   // tweetDisplay.setText("Whoops - something went wrong!");
                    e.printStackTrace();
                }
            }
            return UsersFeedBuilder.toString();
            */
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


            //return null;
        }


        protected void onPostExecute(Boolean success) {
            if (!success) {
                return;
            }
            Log.v("My Project onPostExecute.result",String.valueOf(result1) );
           /*
            StringBuilder tweetResultBuilder = new StringBuilder();

            try {
                JSONObject resultObject = new JSONObject(result);
                JSONArray tweetArray = resultObject.getJSONArray("results");

                for (int t = 0; t < tweetArray.length(); t++) {
                    JSONObject tweetObject = tweetArray.getJSONObject(t);
                    tweetResultBuilder.append(tweetObject
                            .getString("from_user") + ": ");
                    tweetResultBuilder.append(tweetObject.get("text") + "\n\n");
                }
            } catch (Exception e) {
               // tweetDisplay.setText("Whoops - something went wrong!");
                e.printStackTrace();
            }


            if (tweetResultBuilder.length() > 0)
                tV.setText(tweetResultBuilder.toString());
            else
                tV.setText("Sorry - no tweets found for your search!");

        }*/

            StringBuilder tweetResultBuilder = new StringBuilder();
/* 08 12 15
            ArrayList<String> emails = new ArrayList<String>();
            ArrayList<String> passes = new ArrayList<String>();
            ArrayList<String> fotos = new ArrayList<String>();
*/
            try {
              //  ArrayList<HashMap<String, String>> uslist = new ArrayList<HashMap<String, String>>();


                JSONObject resultObject = new JSONObject(result1);
                JSONArray tweetArray = resultObject.getJSONArray("data");
                for (int t = 0; t < tweetArray.length(); t++) {
/*02 12 15

                    JSONObject tweetObject = tweetArray.getJSONObject(t);
                    Log.v("My Project onPostExecute.result",String.valueOf(tweetObject.get("email")) );
                    tweetResultBuilder.append(tweetObject
                            .getString("password") + ": ");
                    tweetResultBuilder.append(tweetObject.get("email") + "\n\n");
             //       emails.add(String.valueOf(tweetObject.get("email")));

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("email", String.valueOf(tweetObject.get("email")));
                    map.put("pass", String.valueOf(tweetObject.get("password")));
                    oslist.add(map);
                    //values[]=
*/
                    /* 02 12 15*/
                 JSONObject tweetObject = tweetArray.getJSONObject(t);
  /* 08 12 15
                    emails.add(String.valueOf(tweetObject.get("id")+" - "+String.valueOf(tweetObject.get("email"))));
                    passes.add(String.valueOf(tweetObject.get("foto")));
                    fotos.add(String.valueOf(tweetObject.get("foto")));
 */
                    ArrayList<String> us = new ArrayList<String>();
                    us.add(String.valueOf(tweetObject.get("id")+" - "+String.valueOf(tweetObject.get("email"))));
                    us.add(String.valueOf(tweetObject.get("foto")));
                    us.add(String.valueOf(tweetObject.get("foto")));
                    UsersArrays.add(us);
                }

/* 02 12 15
                ListAdapter adapter = new SimpleAdapter(getApplicationContext(), oslist,
                        R.layout.list_users,
                        new String[] { "email","pass" }, new int[] {
                        R.id.email,R.id.pass});

               ListView UsersList1 = (ListView) findViewById(R.id.listView);
                UsersList1.setAdapter(adapter);
*/

                /* 02 12 15*/
  /* 08 12 15
                UsersArrays.add(emails);
                UsersArrays.add(passes);
                UsersArrays.add(passes);
*/

/*до 07 12 15
                String[] emailsArr = new String[emails.size()];
                emailsArr = emails.toArray(emailsArr);

                String[] passesArr = new String[passes.size()];
                passesArr = passes.toArray(passesArr);

                String[] fotosArr = new String[fotos.size()];
                fotosArr = passes.toArray(fotosArr);
*/


                ListView UsersList1 = (ListView) context.findViewById(R.id.listView);
                //useLadapter = new UserListAdapter((MainActivity) context, emailsArr, passesArr,fotosArr);
               // useLadapter = new UserListAdapter((MainActivity) context, emails, passes,passes);
                useLadapter = new UserListAdapter((MainActivity) context, R.layout.list_users, UsersArrays);
                UsersList1.setAdapter(useLadapter);



    /*
    //                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
    //                      android.R.layout.simple_list_item_1, emails);
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                      R.layout.list_users, R.id.email, emails);

                    UsersList1.setAdapter(adapter);*/

                //}
            } catch (Exception e) {
                // tweetDisplay.setText("Whoops - something went wrong!");
                e.printStackTrace();
            }
        }

    }

//};