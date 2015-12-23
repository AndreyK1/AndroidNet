package com.example.kirilyuk.androidnet;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Kirilyuk on 23.12.2015.
 */
public class MyFragment  extends Fragment {

   // public String FragName;
/*
    public MyFragment(){
    }
*/
/*
    public void MyFragment(String FragName){
        this.FragName = FragName;
    }
*/


    public static final MyFragment newInstance(String name)
    {
        MyFragment f = new MyFragment();
        Bundle bdl = new Bundle(1);
        //bdl.putInt(EXTRA_TITLE, title);
        bdl.putString("name", name);
        f.setArguments(bdl);
        return f;
    }
    //final String LOG_TAG = "myLogs";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*View v = inflater.inflate(R.layout.fragment2, null);

        Button button = (Button) v.findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Log.d(LOG_TAG, "Button click in Fragment2");
            }
        });

        return v;*/

        String FragName  = getArguments().getString("name");

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
                      //  tV.setText("Whoops - something went wrong!");
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
                       // tV.setText("Whoops - something went wrong!");
                        e.printStackTrace();
                    }
                    // ((MainActivity) getActivity()).ShowAutorizedUser();

                }
            });
            // getActivity()
        }

        //items = getActivity().getResources().getStringArray(R.array.test);
       // tV = (TextView) rootView.findViewById(R.id.tV);
        return rootView;
    }
}