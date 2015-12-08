package com.example.kirilyuk.androidnet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Kirilyuk on 07.12.2015.
 */
public class GetPictureAsyncTask  extends AsyncTask<URL, Void, Boolean> {
    public Bitmap mIcon_val;
    public IOException error;

    @Override
    protected Boolean doInBackground(URL... params) {
        try {
                                /* до 03.12.2015
                                mIcon_val = BitmapFactory.decodeStream(params[0].openConnection().getInputStream());
                           */
                                /* 03.12.2015*/
            int requiredWidth = 150;
            int requiredHeight = 150;
            // URL url = new URL(imageUrl);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(params[0].openConnection().getInputStream(),null, options);
            options.inSampleSize = calculateInSampleSize(options, requiredWidth, requiredHeight);
            options.inJustDecodeBounds = false;
            //don't use same inputstream object as in decodestream above. It will not work because
            //decode stream edit input stream. So if you create
            //InputStream is =url.openConnection().getInputStream(); and you use this in  decodeStream
            //above and bellow it will not work!
            mIcon_val = BitmapFactory.decodeStream(params[0].openConnection().getInputStream(),null, options);
            //return bm;


        } catch (IOException e) {
            this.error = e;
            return false;
        }
        return true;
    }

    //изменение размера картинки /* 03.12.2015*/
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }


    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        if (success) {
            //holder.imgIcon.setImageResource(mIcon_val);
            //img.setImageBitmap(mIcon_val);
        } else {
            //profile_photo.setImageBitmap(defaultImage);
        }
    }
}
