package com.example.kirilyuk.androidnet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kirilyuk on 02.12.2015.
 */
public class UserListAdapter extends BaseAdapter{// BaseAdapter  ArrayAdapter<ArrayList<ArrayList<String>>> {
   /* String [] result;
    String [] result1;
    String [] fotos;*/
/*

    ArrayList {
    public UserListAdapter(Context context, int resource) {
        super(context, resource);
    }
}<String> result;*/

//   ArrayList<String> result;
    ArrayList<String> result1;
    ArrayList<String> fotos;

 //   ArrayList<ArrayList<String>> users;
 //   ArrayList<UserListModel> users;
    List<UserListModel> users;

    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
   // public UserListAdapter(MainActivity mainActivity, int resource, ArrayList<ArrayList<String>> AllList) {
        public UserListAdapter(MainActivity mainActivity, int resource,List<UserListModel> AllList) {
      //  super(mainActivity, resource);
        // TODO Auto-generated constructor stub
      /*
        result=prgmNameList;
        result1=prgmNameList1;
        fotos=fotoList;
        */
/*
        result=AllList.get(0);
        result1=AllList.get(1);
        fotos=AllList.get(2);
*/
        users=AllList;

        context=mainActivity;
     //   imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
      //  return result.length;
        //return result.size();
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return users.get(position);
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
        //View rowView = convertView;
        View rowView;


        LocationHolder holder;
      //  rowView = inflater.inflate(R.layout.list_users, null);
       // holder.tv=(TextView) rowView.findViewById(R.id.email);
      //  holder.tv1=(TextView) rowView.findViewById(R.id.email);
     //   holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
     //   holder.tv.setText(result[position]);
      //  holder.img.setImageResource(imageId[position]);

/*
        TextView tvEm=(TextView) rowView.findViewById(R.id.email);
        TextView tvPass= (TextView) rowView.findViewById(R.id.pass);
        final ImageView   img=(ImageView) rowView.findViewById(R.id.imageUser);
 */
        if(convertView == null)
        {
            rowView = inflater.inflate(R.layout.list_users, parent, false);





            //tvEm.setText(result[position]);
        }
        else
        {
            rowView = convertView;
           // holder = (LocationHolder)rowView.getTag();
        }

        holder = new LocationHolder();
        holder.imgIcon = (ImageView)rowView.findViewById(R.id.imageUser);
        holder.txtTitle = (TextView)rowView.findViewById(R.id.email);
        holder.details = (TextView)rowView.findViewById(R.id.pass);
        rowView.setTag(holder);

    //    holder.txtTitle.setText(users.get(position).get(0));
        holder.txtTitle.setText(users.get(position).email);
        //holder.imgIcon.setImageResource(location.icon);
   //     if(users.get(position).get(1).equals("null")){
        if(users.get(position).pass.equals("null")){
  //          holder.details.setText(users.get(position).get(1)+"+null2+");
            holder.details.setText(users.get(position).pass+"+null2+");
        }else{
//            holder.details.setText(users.get(position).get(1));
            holder.details.setText(users.get(position).pass);
        }
        //holder.imgIcon.setImageResource(R.drawable.test);

      //  return rowView;
/*
        tvEm.setText(result.get(position));
        String str = "";
        if(result1.get(position).equals("null")){
            str = result1.get(position)+"+null+";
        }else{
            str = result1.get(position);
        }
        tvPass.setText(str);
*/
        //fotos
       // if(str1.equals(str2))

 //       if(users.get(position).get(2).equals("null")){
        if(users.get(position).fot.equals("null")){
            holder.imgIcon.setImageResource(R.drawable.test);
          //  img.setImageResource(R.drawable.test);
        }else{
           // String str1 = "http://192.168.123.168/foto/"+fotos.get(position);
         //   String str1 = "http://192.168.123.168/foto/"+users.get(position).get(2);
            String str1 = "http://192.168.123.168/foto/"+users.get(position).fot;

                //получение картинок
                    //ImageView profile_photo = (ImageView) findViewById(R.id.imageTest);
                    try {
                        // URL url = new URL("http://192.168.123.168/img/no.jpg");
                        URL url = new URL(str1);
                        AsyncTask<URL, Void, Boolean> asyncTask2 = new GetPictureAsyncTaskN(holder);
                       // asyncTask1.execute(url);
                        asyncTask2.execute(url);
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
               // Toast.makeText(context, "You Clicked "+users.get(position).get(0), Toast.LENGTH_LONG).show();
                Toast.makeText(context, "You Clicked "+users.get(position).email, Toast.LENGTH_LONG).show();
            }
        });

        return rowView;
    }

    class LocationHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        TextView details;
    }




}



class GetPictureAsyncTaskN  extends AsyncTask<URL, Void, Boolean> {
    public Bitmap mIcon_val;
    public IOException error;
    UserListAdapter.LocationHolder holder;
    ImageView ImgView;

    public GetPictureAsyncTaskN(UserListAdapter.LocationHolder holder)//если мы заполняем лист вью
    {
        this.holder = holder;
       // this.mIcon_val = mIcon_val;
    }

    public GetPictureAsyncTaskN(ImageView ImgView)//если мы заполняем профайл
    {
        this.ImgView = ImgView;
        // this.mIcon_val = mIcon_val;
    }

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
            if(holder !=null) {
                holder.imgIcon.setImageBitmap(mIcon_val);
            }
            if(ImgView !=null) {
                ImgView.setImageBitmap(mIcon_val);
            }
            //img.setImageBitmap(mIcon_val);
        } else {
            //profile_photo.setImageBitmap(defaultImage);
        }
    }
}
