package com.example.kirilyuk.androidnet;

import android.app.Dialog;
import android.content.Context;
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
    Button BtnAddUsers;

    UserListAdapter useLadapter;
    ArrayList<ArrayList<String>> UsersArrays = new ArrayList<ArrayList<String>>();
    //String[] emailsArr;


    int cnt=0;
    Context context1;
    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2" };
    int pageSize = 10;
    int SkipUsers = 0;

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
        context1 = this;
        BtnSendFrag = (Button) findViewById(R.id.button);
        BtnAddUsers = (Button)findViewById(R.id.butAddUser);


        BtnSendFrag.setOnClickListener(new View.OnClickListener() {
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

                AsyncTask<URL, Void, Boolean> asyncTask1 = new PutFototAsyncTask((MainActivity) context1);
                try {
                    // URL url = new URL("http://192.168.123.168/img/no.jpg");
                    URL url = new URL("http://192.168.123.168/img/no.jpg");
                    asyncTask1.execute(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }


                tV = (TextView) findViewById(R.id.tV);
                cnt++;
                tV.setText("new Text-" + String.valueOf(cnt));
                try {
                    tV.setText(tV.getText().toString() + " Text ");
                    //   String searchURL = "http://search.twitter.com/search.json?q=cats";
                    //  String searchURL = "http://stackoverflow.com/questions/33059933/package-org-apache-http-client-does-not-exist";
                    //String searchURL ="http://192.168.123.168/#/PageUsers/1";
                    String searchURL = "http://192.168.123.168/AllUsers/" + SkipUsers + "/" + pageSize;

                    //AsyncTask<String, Void, String> execute = new GetUsersTask().execute(searchURL);
                    AsyncTask<String, Void, Boolean> execute = new UsersListAsyncTask((MainActivity) context1,useLadapter,UsersArrays).execute(searchURL);
                } catch (Exception e) {
                    tV.setText("Whoops - something went wrong!");
                    e.printStackTrace();
                }
            }

            //BtnAddUsers

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


        BtnAddUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
              /* for ( ArrayList<String> ar:UsersArrays){

               }*/
/*

                UsersArrays.get(0).add("ytrtr");
                UsersArrays.get(1).add("gyiyuiy");
                UsersArrays.get(2).add("55.jpg");

                UsersArrays.get(0).add("ytrtr1");
                UsersArrays.get(1).add("null");
                UsersArrays.get(2).add("null");

                UsersArrays.get(0).add("null");
                UsersArrays.get(1).add("null");
                UsersArrays.get(2).add("48.jpg");*/
/*до 07 12 2015*/
                /*
                String[] emailsArr = new String[UsersArrays.get(0).size()];
                emailsArr =  UsersArrays.get(0).toArray(emailsArr);

                String[] passesArr = new String[UsersArrays.get(1).size()];
                passesArr =  UsersArrays.get(1).toArray(passesArr);

                String[] fotosArr = new String[UsersArrays.get(2).size()];
                fotosArr =  UsersArrays.get(2).toArray(fotosArr);
*/

                ArrayList<String> us = new ArrayList<String>();
                us.add("nnnnnnn");
                us.add("null");
                us.add("null");
                UsersArrays.add(us);

                ArrayList<String> us1 = new ArrayList<String>();
                us1.add("4888888");
                us1.add("not null");
                us1.add("48.jpg");
                UsersArrays.add(us1);
                ArrayList<String> us2 = new ArrayList<String>();
                us2.add("55555");
                us2.add("not null");
                us2.add("55.jpg");
                UsersArrays.add(us2);

                //ListView UsersList1 = (ListView) context.findViewById(R.id.listView);
               // ((ListView)findViewById(R.id.conv_list)).getAdapter().notifyDataSetChanged();
                ((UserListAdapter) ((ListView)findViewById(R.id.listView)).getAdapter()).notifyDataSetChanged();

               // useLadapter.notifyDataSetChanged();
/**/
            //    ListView UsersList1 = (ListView) findViewById(R.id.listView);
              //  useLadapter = new UserListAdapter((MainActivity) context1, UsersArrays.get(0), UsersArrays.get(1),UsersArrays.get(2));
            //    UsersList1.setAdapter(useLadapter);



                /*for (String a[]:UsersArrays){
                    /*for (String b:a){
                       System.out.println(b);

                    }*/
                 //   a.add("foto");
             //   }
            }
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


}
