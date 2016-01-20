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
//import android.app.FragmentActivity;
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
import android.widget.EditText;
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
    UsrListFragment myFragmentUsers;
    MyFragment myFragmentLogin;
    ProfileFragment myFragmentProfile;
    BlueTootshFragment myFragmentBlueToth;

    SharedPreferences sPref;
   boolean usersLoaded;

    Button BtnSendFrag;
    Button BtnAddUsers;
    Button BtnLogin;
  FragmentManager supportFragmentManager= getFragmentManager();
   //FragmentManager supportFragmentManager;

    FragmentTransaction fragmentTransaction;

    String Server = "http://192.168.123.168/";

    ProgressDialog pDialog;
    UserListAdapter useLadapter;
//    ArrayList<ArrayList<String>> UsersArrays = new ArrayList<ArrayList<String>>();
  //  ArrayList<UserListModel> UsersArrays = new ArrayList<UserListModel>();
 //   List<UserListModel> UsersArrays = new ArrayList<UserListModel>();
    List<UserListModel> UsersArrays;
    ArrayList<String> user=new ArrayList<String>(); //авторизованый юзер
    //String[] emailsArr;


    int cnt=0;
    Context context1;
    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2" };

    int pageSize = 10;//по сколько юзеров загружать
    int SkipUsers;//с какого юзера
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

            UsersArrays = new ArrayList<UserListModel>();
            SkipUsers = 0;
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
          //  MyFragment myFragmentUsers = MyFragment.newInstance("main");
            UsrListFragment myFragmentUsers = new UsrListFragment();
            ProfileFragment myFragmentProfile = new ProfileFragment();
            BlueTootshFragment myFragmentBlueToth = new BlueTootshFragment();

            MyFragment myFragmentLogin = MyFragment.newInstance("login");
            FragmentTransaction ft = supportFragmentManager.beginTransaction();
            //ft.add(R.id.fragment2, frag2);
            ft.add(R.id.container, myFragmentUsers,"main");
            ft.add(R.id.container, myFragmentLogin,"login");
            ft.add(R.id.container, myFragmentProfile,"profile");
            ft.add(R.id.container, myFragmentBlueToth,"bluetoth");
            ft.hide(myFragmentLogin);
            ft.hide(myFragmentProfile);
            ft.commit();

            usersLoaded=false;
            this.saveText("usersLoaded");


       // SkipUsers = 0;
/*
           getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, PlaceholderFragment.newInstance("main").commit());*/
       }else {



            //  else{
            if (supportFragmentManager.findFragmentByTag("main") != null) {
                Toast.makeText(this, "onCreate - main not null", Toast.LENGTH_SHORT).show();
                myFragmentUsers = (UsrListFragment) supportFragmentManager.findFragmentByTag("main");
                UsersArrays = myFragmentUsers.UsersArrays;
                SkipUsers  = myFragmentUsers.SkipUsers;
                if (UsersArrays == null) {

                    UsersArrays = new ArrayList<UserListModel>();
                    SkipUsers = 0;

                    myFragmentUsers.UsersArrays = UsersArrays;
                    myFragmentUsers.SkipUsers = SkipUsers;
                }

            } else {
                Toast.makeText(this, "onCreate - main null", Toast.LENGTH_SHORT).show();
            }
            if (supportFragmentManager.findFragmentByTag("login") != null) {
                Toast.makeText(this, "onCreate - login not null", Toast.LENGTH_SHORT).show();
                myFragmentLogin = (MyFragment) supportFragmentManager.findFragmentByTag("login");
            } else {
                Toast.makeText(this, "onCreate - login null", Toast.LENGTH_SHORT).show();
            }
            if (supportFragmentManager.findFragmentByTag("profile") != null) {
                Toast.makeText(this, "onCreate - profile not null", Toast.LENGTH_SHORT).show();
                myFragmentProfile = (ProfileFragment) supportFragmentManager.findFragmentByTag("profile");
            }
            if (supportFragmentManager.findFragmentByTag("bluetoth") != null) {
                Toast.makeText(this, "onCreate - bluetoth not null", Toast.LENGTH_SHORT).show();
                myFragmentBlueToth = (BlueTootshFragment) supportFragmentManager.findFragmentByTag("bluetoth");
            }
        }

        this.loadText("usersLoaded");

        context1 = this;
        BtnSendFrag = (Button) findViewById(R.id.button);
        BtnAddUsers = (Button)findViewById(R.id.butAddUser);
       // BtnLogin = (Button)findViewById(R.id.btLogin);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait..");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);

       /*
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

        });
*/

        this.loadText("user");
  //      this.ShowAutorizedUser();

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



    @Override
    protected void onSaveInstanceState(Bundle outState) {

        // Android automatically saves visible fragments here. (?)

        super.onSaveInstanceState(outState);
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
        if(what =="usersLoaded"){
                ed.putBoolean("usersLoaded", usersLoaded);
                ed.commit();
                Toast.makeText(this, "sPref save usersLoaded- "+String.valueOf(usersLoaded), Toast.LENGTH_SHORT).show();
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
        if(what =="usersLoaded") {
            usersLoaded = sPref.getBoolean("usersLoaded", false);
            Toast.makeText(this, "sPref load usersLoaded- "+String.valueOf(usersLoaded), Toast.LENGTH_SHORT).show();
        }
    }

    public void ShowAutorizedUser() {
        if(user.size()>0){
            ((EditText) findViewById(R.id.edEmail)).setText(user.get(1));
            ((EditText) findViewById(R.id.edFot)).setText(user.get(2));

                String str1 = Server+"/foto/"+user.get(2);
            //получение картинки
            ImageView ImgProf = (ImageView) findViewById(R.id.imageProfile);


            if(user.get(2).equals("null")){

                ImgProf.setImageResource(R.drawable.test);
                //holder.imgIcon.setImageResource(R.drawable.test);
                //  img.setImageResource(R.drawable.test);
            }else {
                //ImageView profile_photo = (ImageView) findViewById(R.id.imageTest);
                try {
                    // URL url = new URL("http://192.168.123.168/img/no.jpg");
                    URL url = new URL(str1);
                    AsyncTask<URL, Void, Boolean> asyncTask2 = new GetPictureAsyncTaskN(ImgProf);
                    // asyncTask1.execute(url);
                    asyncTask2.execute(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }


            Toast.makeText(this, "ShowAutorizedUser : " + String.valueOf(user.get(1)), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "ShowAutorizedUser : " + "none", Toast.LENGTH_LONG).show();
        }
    }


    public void loadUserList() {
        try {
            String searchURL = "http://192.168.123.168/AllUsers/" + SkipUsers + "/" + pageSize;
            //AsyncTask<String, Void, String> execute = new GetUsersTask().execute(searchURL);
            AsyncTask<String, Void, Boolean> execute = new UsersListAsyncTask((MainActivity) context1, useLadapter, UsersArrays).execute(searchURL);
        } catch (Exception e) {
       //     tV.setText("Whoops - something went wrong!");
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
            myFragmentUsers.SkipUsers = SkipUsers;

            String searchURL = "http://192.168.123.168/AllUsers/" + SkipUsers + "/" + pageSize;

            //AsyncTask<String, Void, String> execute = new GetUsersTask().execute(searchURL);
            AsyncTask<String, Void, Boolean> execute = new UsersListAsyncTask((MainActivity) context1,useLadapter,UsersArrays).execute(searchURL);
        } catch (Exception e) {
         //   tV.setText("Whoops - something went wrong!");
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
        myFragmentUsers =(UsrListFragment) supportFragmentManager.findFragmentByTag("main");
        myFragmentLogin =(MyFragment) supportFragmentManager.findFragmentByTag("login");
        myFragmentProfile = (ProfileFragment) supportFragmentManager.findFragmentByTag("profile");
        myFragmentBlueToth = (BlueTootshFragment)  supportFragmentManager.findFragmentByTag("bluetoth");

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
           // List<Fragment> lfrg= getSupportFragmentManager().getFragments();
            return true;
        }
        if (id == R.id.action_usersList){
           // getSupportFragmentManager().beginTransaction()
                   supportFragmentManager.beginTransaction()
                    .hide(myFragmentLogin)
                    .hide(myFragmentProfile)
                    .hide(myFragmentBlueToth)
                   .show(myFragmentUsers)
                    .commit();
            Toast.makeText(this, "usersLoaded "+String.valueOf(usersLoaded) , Toast.LENGTH_LONG).show();
            if(!usersLoaded) {

                usersLoaded = true;
                loadText("usersLoaded");
                loadUserList();
            }
            return true;
        }

        if (id == R.id.action_login){
            //getSupportFragmentManager().beginTransaction()
            supportFragmentManager.beginTransaction()
            //fragmentTransaction
                    .hide(myFragmentUsers)
                    .hide(myFragmentProfile)
                    .hide(myFragmentBlueToth)
                    .show(myFragmentLogin)
                    .commit();
            return true;

        }
        if (id == R.id.action_profile){
            supportFragmentManager.beginTransaction()
                    .hide(myFragmentUsers)
                    .hide(myFragmentLogin)
                    .hide(myFragmentBlueToth)
                    .show(myFragmentProfile)
                    .commit();
            return true;
        }
        if (id == R.id.action_bluetooth){
            supportFragmentManager.beginTransaction()
                    .hide(myFragmentUsers)
                    .hide(myFragmentLogin)
                    .hide(myFragmentProfile)
                    .show(myFragmentBlueToth)
                    .commit();
            return true;
        }
       // myFragmentProfile

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
