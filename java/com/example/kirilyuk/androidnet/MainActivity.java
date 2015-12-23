package com.example.kirilyuk.androidnet;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
/*
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
*/

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;


import android.support.v7.app.ActionBarActivity;
import android.text.Layout;
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
import android.widget.Toast;

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
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//public class MainActivity extends Activity {
public class MainActivity extends ActionBarActivity {

   ImageView profile_photo;
   TextView tV;
    TextView loginTxt;
    ListView UsersList;

    //FragmentManager fragmentManager;
   // PlaceholderFragment myFragmentUsers;
   // PlaceholderFragment myFragmentLogin;
    MyFragment myFragmentUsers;
    MyFragment myFragmentLogin;
    SharedPreferences sPref;

    Button BtnSendFrag;
    Button BtnAddUsers;
    Button BtnLogin;
  FragmentManager supportFragmentManager= getFragmentManager();
   //FragmentManager supportFragmentManager;

    FragmentTransaction fragmentTransaction;

    String Server = "http://192.168.123.168/";

    ProgressDialog pDialog;
    UserListAdapter useLadapter;
    ArrayList<ArrayList<String>> UsersArrays = new ArrayList<ArrayList<String>>();
    ArrayList<String> user=new ArrayList<String>(); //авторизованый юзер
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
 //       savedInstanceState = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // supportFragmentManager= getSupportFragmentManager();

       // supportFragmentManager  = getFragmentManager();
        //savedInstanceState = null;


       // if (myFragmentUsers == null) {
        if (savedInstanceState == null) {
/*
            myFragmentUsers = new PlaceholderFragment();
            myFragmentUsers.FragName = "main";
            myFragmentLogin = new PlaceholderFragment();
            myFragmentLogin.FragName = "login";
            Toast.makeText(this, "onCreate - savedInstanceState", Toast.LENGTH_SHORT).show();
      //      supportFragmentManager = getSupportFragmentManager();
           // getSupportFragmentManager().beginTransaction()



            fragmentTransaction = supportFragmentManager.beginTransaction();
            fragmentTransaction
                    .add(R.id.container, myFragmentUsers,"main")
                    .add(R.id.container, myFragmentLogin,"login")
                    .hide(myFragmentLogin)
                    .commit();
*/

          /*  MyFragment myFragmentUsers = new MyFragment("main");
            MyFragment myFragmentLogin = new MyFragment("login");*/
            MyFragment myFragmentUsers = MyFragment.newInstance("main");
            MyFragment myFragmentLogin = MyFragment.newInstance("login");
            FragmentTransaction ft = supportFragmentManager.beginTransaction();
            //ft.add(R.id.fragment2, frag2);
            ft.add(R.id.container, myFragmentUsers,"main");
            ft.add(R.id.container, myFragmentLogin,"login");
            ft.hide(myFragmentLogin);
            ft.commit();

        SkipUsers = 0;
/*
           getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, PlaceholderFragment.newInstance("main").commit());*/
       }

      //  else{
            if(supportFragmentManager.findFragmentByTag("main") != null) {
                Toast.makeText(this, "onCreate - main not null", Toast.LENGTH_SHORT).show();
                myFragmentUsers =(MyFragment) supportFragmentManager.findFragmentByTag("main");

            }else{
                Toast.makeText(this, "onCreate - main null", Toast.LENGTH_SHORT).show();
            }
            if(supportFragmentManager.findFragmentByTag("login") != null) {
                Toast.makeText(this, "onCreate - login not null", Toast.LENGTH_SHORT).show();
                myFragmentLogin = (MyFragment) supportFragmentManager.findFragmentByTag("login");
            }else{
                Toast.makeText(this, "onCreate - login null", Toast.LENGTH_SHORT).show();
            }
     //   }

        context1 = this;
        BtnSendFrag = (Button) findViewById(R.id.button);
        BtnAddUsers = (Button)findViewById(R.id.butAddUser);
       // BtnLogin = (Button)findViewById(R.id.btLogin);



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


                loadUserList();

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


        this.loadText("user");
        this.ShowAutorizedUser();

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

//сохранение перед поворотом
    /*public Object onRetainNonConfigurationInstance() {
        return supportFragmentManager;
    }*/
 /*
    @Override
   public Object onRetainNonConfigurationInstance() {
        return this;
    }
*/

    //сохранение
    void saveText(String what) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        if(what =="user"){
            if(user.size()>0){
                ed.putString("user_id", String.valueOf(user.get(0)));
                ed.putString("user_email", String.valueOf(user.get(1)));
                ed.putString("user_foto", String.valueOf(user.get(2)));
                ed.putString("user_token", String.valueOf(user.get(3)));

                ed.commit();
                Toast.makeText(this, "Text saved-"+"user_email "+sPref.getString("user_email", ""), Toast.LENGTH_SHORT).show();
            }
        }


    }

    void loadText(String what) {
        sPref = getPreferences(MODE_PRIVATE);
        if(what =="user") {
            if (user.size() == 0) {
                //String savedText = sPref.getString(SAVED_TEXT, "");
                //etText.setText(savedText);
                user.add(String.valueOf(sPref.getString("user_id", "")));
                user.add(String.valueOf(sPref.getString("user_email", "")));
                user.add(String.valueOf(sPref.getString("user_foto", "")));
                user.add(String.valueOf(sPref.getString("user_token", "")));
                Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "hhh", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void ShowAutorizedUser() {
        if(user.size()>0){
            Toast.makeText(this, "ShowAutorizedUser : " + String.valueOf(user.get(1)), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "ShowAutorizedUser : " + "none", Toast.LENGTH_LONG).show();
        }
    }


    public void loadUserList() {

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

       // loadUserList();
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
            AsyncTask<String, Void, Boolean> execute = new UsersListAsyncTask((MainActivity) context1, useLadapter, UsersArrays).execute(searchURL);
        } catch (Exception e) {
            tV.setText("Whoops - something went wrong!");
            e.printStackTrace();
        }

        BtnAddUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // loadNextPageUsers(mLoadingFooter);
                // UsersList.removeFooterView((LinearLayout) mLoadingFooter);
            }
        });
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
        myFragmentUsers =(MyFragment) supportFragmentManager.findFragmentByTag("main");
        myFragmentLogin =(MyFragment) supportFragmentManager.findFragmentByTag("login");

    /*    List<Fragment> lfrg= getSupportFragmentManager().getFragments();
        Fragment fr1 =  lfrg.get(0);
        Fragment fr2 =  lfrg.get(1);*/
       // Fragment fr3 =  lfrg.get(2);
      /* Toast.makeText(this, "getSupportFragmentManager: fr1 "+((PlaceholderFragment) fr1).FragName+
                " getSupportFragmentManager: fr2 "+((PlaceholderFragment) fr2).FragName
              // + "getSupportFragmentManager: fr3 "+((PlaceholderFragment) fr3).FragName
                , Toast.LENGTH_LONG).show();*/

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
           // List<Fragment> lfrg= getSupportFragmentManager().getFragments();
            return true;
        }
        if (id == R.id.action_usersList){
            /*myFragment = new PlaceholderFragment();
            myFragment.FragName = "main";
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, myFragment)
                    .commit();
*/
         //   Toast.makeText(this, "menu - action_usersList: "+myFragmentLogin.FragName , Toast.LENGTH_LONG).show();


           // getSupportFragmentManager().beginTransaction()
                   supportFragmentManager.beginTransaction()
            //fragmentTransaction
                    .hide(myFragmentLogin)
                   .show(myFragmentUsers)
                   // .hide(fr2)
                  //  .show(fr1)
                    .commit();

            loadUserList();
            return true;
        }

        if (id == R.id.action_login){
       //    Toast.makeText(this, "menu - action_login: "+myFragmentLogin.FragName , Toast.LENGTH_LONG).show();



            //getSupportFragmentManager().beginTransaction()
            supportFragmentManager.beginTransaction()
            //fragmentTransaction
                    .hide(myFragmentUsers)
                    .show(myFragmentLogin)
                   // .hide(fr1)
                   // .show(fr2)
                    .commit();
            /*
            myFragment = new PlaceholderFragment();
            myFragment.FragName = "login";
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, myFragment)
                        .commit();
                        */
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
/*
    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        String FragName;
        View rootView;
        ArrayList<PlaceholderFragment> arr;

        public PlaceholderFragment() {
            //FragName =FragName1;
        }


        TextView tV;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            if(FragName == "main") {
               rootView = inflater.inflate(R.layout.fragment_main, container, false);

           }
            if(FragName == "profile") {
                // rootView = inflater.inflate(R.layout.fragment_main, container, false);

            }
            if(FragName == "login") {
                rootView = inflater.inflate(R.layout.fragment_login, container, false);

                Button BtnLoginIn = (Button) rootView.findViewById(R.id.btLogin);
                Button BtnMe = (Button) rootView.findViewById(R.id.btMe);
                final TextView loginTxt = (TextView) rootView.findViewById(R.id.login);
                BtnLoginIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Log.v("My project login", "login");
                        loginTxt.setText("Логинимся");
                        try {
                            // tV.setText("Whoops - Whoops Whoops Whoops Whoops!");
                            ((MainActivity) getActivity()).BtnSendFrag.setText("opaaaa");
                            // button
                            // (Activity) activity.tV.
                            //String searchURL = getActivity().Server; //  "http://192.168.123.168/AllUsers/" + SkipUsers + "/" + pageSize;
                            String url = ((MainActivity) getActivity()).Server+"login";

                            AsyncTask<String, Void, Boolean> execute = new LoginAsyncTask(((MainActivity) getActivity())).execute(url);
                            // AsyncTask<String, Void, Boolean> execute = new UsersListAsyncTask((MainActivity) activity, useLadapter, UsersArrays).execute(searchURL);
                        } catch (Exception e) {
                            tV.setText("Whoops - something went wrong!");
                            e.printStackTrace();
                        }
                       // ((MainActivity) getActivity()).ShowAutorizedUser();
                    }
                });

                BtnMe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Log.v("My project  Me", "Me");
                        loginTxt.setText("Получаем ми");
                        try {
                            // tV.setText("Whoops - Whoops Whoops Whoops Whoops!");
                            ((MainActivity) getActivity()).BtnSendFrag.setText("meeee");
                            // button
                            // (Activity) activity.tV.
                            //String searchURL = getActivity().Server; //  "http://192.168.123.168/AllUsers/" + SkipUsers + "/" + pageSize;
                            String url = ((MainActivity) getActivity()).Server+"me";

                            AsyncTask<String, Void, Boolean> execute = new GetMeAsyncTask(((MainActivity) getActivity())).execute(url);
                            // AsyncTask<String, Void, Boolean> execute = new UsersListAsyncTask((MainActivity) activity, useLadapter, UsersArrays).execute(searchURL);
                        } catch (Exception e) {
                            tV.setText("Whoops - something went wrong!");
                            e.printStackTrace();
                        }
                       // ((MainActivity) getActivity()).ShowAutorizedUser();

                    }
                });
               // getActivity()
            }

            //items = getActivity().getResources().getStringArray(R.array.test);
            tV = (TextView) rootView.findViewById(R.id.tV);
            return rootView;

        }

    }
*/

}
