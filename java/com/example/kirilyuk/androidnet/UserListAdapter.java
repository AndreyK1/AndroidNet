package com.example.kirilyuk.androidnet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Kirilyuk on 02.12.2015.
 */
public class UserListAdapter extends BaseAdapter {
    String [] result;
    String [] result1;
    String [] fotos;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public UserListAdapter(MainActivity mainActivity, String[] prgmNameList, String[] prgmNameList1,String[] fotoList) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        result1=prgmNameList1;
        fotos=fotoList;
        context=mainActivity;
     //   imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
/*
    public class Holder
    {
        TextView tv;
        ImageView img;
    }*/
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
      //  Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_users, null);
       // holder.tv=(TextView) rowView.findViewById(R.id.email);
      //  holder.tv1=(TextView) rowView.findViewById(R.id.email);
     //   holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
     //   holder.tv.setText(result[position]);
      //  holder.img.setImageResource(imageId[position]);
        TextView tvEm=(TextView) rowView.findViewById(R.id.email);
        TextView tvPass= (TextView) rowView.findViewById(R.id.pass);
        final ImageView   img=(ImageView) rowView.findViewById(R.id.imageUser);
        tvEm.setText(result[position]);
        String str = "";
        if(result1[position].equals("null")){
            str = result1[position]+"+null+";
        }else{
            str = result1[position];
        }
        tvPass.setText(str);

        //fotos
       // if(str1.equals(str2))
        if(fotos[position].equals("null")){
            img.setImageResource(R.drawable.test);
        }else{
            String str1 = "http://192.168.123.168/foto/"+fotos[position];

                //получение картинок
                    //ImageView profile_photo = (ImageView) findViewById(R.id.imageTest);
                    AsyncTask<URL, Void, Boolean> asyncTask1 = new AsyncTask<URL, Void, Boolean>() {
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
                                img.setImageBitmap(mIcon_val);
                            } else {
                                //profile_photo.setImageBitmap(defaultImage);
                            }
                        }
                    };

                    try {
                        // URL url = new URL("http://192.168.123.168/img/no.jpg");
                        URL url = new URL(str1);
                        asyncTask1.execute(url);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
        }

      //  img.setImageResource(R.drawable.test);
        //img.setImageResource(imageId[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

}
