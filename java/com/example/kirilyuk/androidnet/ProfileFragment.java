package com.example.kirilyuk.androidnet;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Kirilyuk on 29.12.2015.
 */
public class ProfileFragment extends Fragment {

    public final int CAMERA_RESULT = 0;
    ImageView ImgProf;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }
/*
    public static final ProfileFragment newInstance(String name)
    {
        MyFragment f = new MyFragment();
        Bundle bdl = new Bundle(1);
        //bdl.putInt(EXTRA_TITLE, title);
        bdl.putString("name", name);
        f.setArguments(bdl);
        return f;
    }
    */
    //final String LOG_TAG = "myLogs";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        if (savedInstanceState == null) {

            Button BtnProfile = (Button) rootView.findViewById(R.id.butProfile);
            Button BtnMakeFot = (Button) rootView.findViewById(R.id.butMakeFoto);
            ImgProf = (ImageView) rootView.findViewById(R.id.imageProfile);

           final EditText EdEmail = (EditText) rootView.findViewById(R.id.edEmail);
            BtnProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Log.v("My project  Me", "Me");
                    EdEmail.setText("Получаем ми");
                    try {
                         String url = ((MainActivity) getActivity()).Server + "me";

                        AsyncTask<String, Void, Boolean> execute = new GetMeAsyncTask(((MainActivity) getActivity())).execute(url);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


            BtnMakeFot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Log.v("My project  MakeFot", "MakeFot");
                    EdEmail.setText("MakeFot ми");
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_RESULT);
                }
            });

        }

        return rootView;

    }

/*
    // Щелчок кнопки
    public void CameraTurnClick(View v) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_RESULT);
    }
*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_RESULT) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ImgProf.setImageBitmap(thumbnail);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putString("time_key", mTime);
        // outState.putAll(outState);
    }
}