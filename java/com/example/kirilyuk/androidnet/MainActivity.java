package com.example.kirilyuk.androidnet;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    ProgressDialog pDialog;
    UserListAdapter useLadapter;
    ArrayList<ArrayList<String>> UsersArrays = new ArrayList<ArrayList<String>>();
    //String[] emailsArr;


    int cnt=0;
    Context context1;
    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2" };

    int pageSize = 10;//по сколько юзеров загружать
    int SkipUsers = 0;//с какого юзера
    boolean usersEnded = false; //долистались ли мы до конца юзеров

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


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait..");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);

        BtnSendFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //тестовая картинка
                profile_photo = (ImageView) findViewById(R.id.imageTest);

                AsyncTask<URL, Void, Boolean> asyncTask1 = new PutFototAsyncTask((MainActivity) context1);
                try {
                    // URL url = new URL("http://192.168.123.168/img/no.jpg");
                    URL url = new URL("http://192.168.123.168/img/no.jpg");
                    asyncTask1.execute(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }



                UsersList = (ListView) findViewById(R.id.listView);
                LayoutInflater layoutInflater = (LayoutInflater) context1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                // LayoutInflater layoutInflater = inflater.inflate(R.layout.list_users, parent, false);
                // inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
/*
                final LinearLayout mLoadingFooter;
                mLoadingFooter = (LinearLayout) layoutInflater.inflate(R.layout.loading_footer,null );
                mLoadingFooter.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.FILL_PARENT, ListView.LayoutParams.WRAP_CONTENT));
                UsersList.addFooterView(mLoadingFooter);
 */
              //  ((UserListAdapter)((BaseAdapter)((ListView)findViewById(R.id.listView)).getAdapter()))

                // mList.addFooterView(mLoadingFooter);
                //http://habrahabr.ru/post/130319/


                UsersList.setOnScrollListener(new AbsListView.OnScrollListener() {

                    @Override
                    public void onScrollStateChanged(AbsListView arg0, int arg1) {}

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
 //                       UsersList.removeFooterView((LinearLayout) mLoadingFooter);
                        if (visibleItemCount > 0 && firstVisibleItem + visibleItemCount == totalItemCount) {

                            loadNextPageUsers();
                        }
                    }
                });


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
               // loadNextPageUsers(mLoadingFooter);
               // UsersList.removeFooterView((LinearLayout) mLoadingFooter);
            }
          });

/*
        UsersList.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (visibleItemCount > 0 && firstVisibleItem + visibleItemCount == totalItemCount) {
                    loadNextPageUsers();
                }
            }
        });
*/

    }

    //подгрузка юзеров в листвью
    public void loadNextPageUsers(){
        if(usersEnded){
            return;
        }
       // UsersList.removeFooterView(mLoadingFooter);
     // ((UserListAdapter) ((ListView)findViewById(R.id.listView)).getAdapter()).notifyDataSetChanged();
//        ((UserListAdapter)((BaseAdapter)((ListView)findViewById(R.id.listView)).getAdapter())).notifyDataSetChanged();
 //       pDialog.dismiss();

        try {
            SkipUsers = SkipUsers+10;
            String searchURL = "http://192.168.123.168/AllUsers/" + SkipUsers + "/" + pageSize;

            //AsyncTask<String, Void, String> execute = new GetUsersTask().execute(searchURL);
            AsyncTask<String, Void, Boolean> execute = new UsersListAsyncTask((MainActivity) context1,useLadapter,UsersArrays).execute(searchURL);
        } catch (Exception e) {
            tV.setText("Whoops - something went wrong!");
            e.printStackTrace();
        }

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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            //items = getActivity().getResources().getStringArray(R.array.test);
            tV = (TextView) rootView.findViewById(R.id.tV);
            return rootView;
        }
    }


}
