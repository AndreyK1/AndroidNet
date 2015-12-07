package com.example.kirilyuk.androidnet;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.*;
import java.io.*;

import java.io.IOException;

/**
 * Created by Kirilyuk on 05.12.2015.
 */
public class PutFototAsyncTask extends AsyncTask<URL, Void, Boolean> {
    //}
//AsyncTask<URL, Void, Boolean>() {
    public Bitmap mIcon_val;
    public IOException error;
    public MainActivity context;
    ImageView profile_photo;

    public PutFototAsyncTask(MainActivity a)
    {
        this.context = a;
    }



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
            profile_photo = (ImageView)  context.findViewById(R.id.imageTest);
            profile_photo.setImageBitmap(mIcon_val);
        } else {
            //profile_photo.setImageBitmap(defaultImage);
        }
    }




};
