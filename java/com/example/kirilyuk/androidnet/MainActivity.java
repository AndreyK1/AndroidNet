package com.example.kirilyuk.androidnet;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/*
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
*/
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {

   ImageView profile_photo;
   TextView tV;
    ListView UsersList;
    PlaceholderFragment myFragment;
    Button BtnSendFrag;
    int cnt=0;
    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myFragment = new PlaceholderFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, myFragment)
                    .commit();
        }

        BtnSendFrag = (Button) findViewById(R.id.button);



        BtnSendFrag.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                //тестовая картинка

       //         profile_photo.setImageBitmap(getImageBitmap("http://192.168.123.168/img/no.jpg"));
               /* URL urlObjp = null;
                try {
                    urlObjp = new URL("http://192.168.123.168/img/no.jpg");
                   // URL urlObjp = new URL("http://192.168.123.168/img/no.jpg");
                    Bitmap mIcon_val = null;
                    try {
                        mIcon_val = BitmapFactory.decodeStream(urlObjp.openConnection().getInputStream());
                        profile_photo.setImageBitmap(mIcon_val);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }*/
              //  profile_photo.setImageDrawable();

                profile_photo = (ImageView) findViewById(R.id.imageTest);
                AsyncTask<URL, Void, Boolean> asyncTask1 = new AsyncTask<URL, Void, Boolean>() {
                    public Bitmap mIcon_val;
                    public IOException error;

                    @Override
                    protected Boolean doInBackground(URL... params) {
                        try {
                            mIcon_val = BitmapFactory.decodeStream(params[0].openConnection().getInputStream());
                        } catch (IOException e) {
                            this.error = e;
                            return false;
                        }
                        return true;
                    }

                    @Override
                    protected void onPostExecute(Boolean success) {
                        super.onPostExecute(success);
                        if (success) {
                            profile_photo.setImageBitmap(mIcon_val);
                        } else {
                            //profile_photo.setImageBitmap(defaultImage);
                        }
                    }
                };

                try {
                   // URL url = new URL("http://192.168.123.168/img/no.jpg");
                    URL url = new URL("http://192.168.123.168/img/no.jpg");
                    asyncTask1.execute(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }





                tV = (TextView) findViewById(R.id.tV);
                cnt++;
                tV.setText("new Text-"+String.valueOf(cnt));
                try {
                    tV.setText(tV.getText().toString()+ " Text ");
                  //   String searchURL = "http://search.twitter.com/search.json?q=cats";
                  //  String searchURL = "http://stackoverflow.com/questions/33059933/package-org-apache-http-client-does-not-exist";
                    //String searchURL ="http://192.168.123.168/#/PageUsers/1";
                    String searchURL ="http://192.168.123.168/AllUsers/5/15";
                   // String searchURL ="http://www.boogle1.ru/";

               /* LoginAsync la = new LoginAsync();/AllUsers/5/15
                la.execute(username, password);
    */
                     AsyncTask<String, Void, String> execute = new GetUsersTask().execute(searchURL);
                } catch (Exception e) {
                    tV.setText("Whoops - something went wrong!");
                    e.printStackTrace();
                }
            }
/*
            private Bitmap getImageBitmap(String url) {
                Log.v("Bitmap", "here1");
                Bitmap bm = null;
                try {
                    URL aURL = new URL(url);
                    URLConnection conn = aURL.openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);
                    bm = BitmapFactory.decodeStream(bis);
                    bis.close();
                    is.close();
                } catch (IOException e) {
                    Log.e("URLConnection", "Error getting bitmap", e);
                }
                return bm;
            }
            */
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {


        public PlaceholderFragment() {
        }

        TextView tV;
       // ListView UsersList;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            //items = getActivity().getResources().getStringArray(R.array.test);

            tV = (TextView) rootView.findViewById(R.id.tV);
/*
            UsersList = (ListView) rootView.findViewById(R.id.listView);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                    android.R.layout.simple_list_item_1, values);
            // UsersList.setAdapter(adapter);
            //; setListAdapter(adapter);
*/


            return rootView;
        }
    }


    private class GetUsersTask extends AsyncTask<String, Void, String> {

        // TextView tV;
        String charset = "UTF-8";
        Dialog loadingDialog;

        @Override
        protected String doInBackground(String... sURL) {
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
            Log.v("My Project conn.error","try" );

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

            return result.toString();


            //return null;
        }


        protected void onPostExecute(String result) {

            Log.v("My Project onPostExecute.result",String.valueOf(result) );
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
            ArrayList<String> emails = new ArrayList<String>();

            try {
                ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();


                JSONObject resultObject = new JSONObject(result);
                JSONArray tweetArray = resultObject.getJSONArray("data");
                for (int t = 0; t < tweetArray.length(); t++) {


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
                }


                ListAdapter adapter = new SimpleAdapter(getApplicationContext(), oslist,
                        R.layout.list_users,
                        new String[] { "email","pass" }, new int[] {
                        R.id.email,R.id.pass});

             //   list.setAdapter(adapter);

               // if(tweetArray.length()>0){

                ListView UsersList1 = (ListView) findViewById(R.id.listView);
                UsersList1.setAdapter(adapter);

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







}
