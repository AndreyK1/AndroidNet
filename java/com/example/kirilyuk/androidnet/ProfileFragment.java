package com.example.kirilyuk.androidnet;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Kirilyuk on 29.12.2015.
 */
public class ProfileFragment extends Fragment {

    public final int CAMERA_RESULT = 0;
    ImageView ImgProf;
    private Uri outputFileUri;
    //File file;
    File file= new File(Environment.getExternalStorageDirectory(),
    "test.jpg");

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
            Button BtnSaveFoto = (Button) rootView.findViewById(R.id.butSaveFoto);
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
                    //getThumbailPicture();
                    saveFullImage();

                }
            });

            BtnSaveFoto.setOnClickListener(new View.OnClickListener() {//
                @Override
                public void onClick(View arg0) {
                    Log.v("My project SaveFoto", "SaveFoto");
                    EdEmail.setText("SaveFoto ми");
                    //getThumbailPicture();

                    String requestURL = ((MainActivity)getActivity()).Server+"SaveFile";

                    try {
                       // String url = ((MainActivity) getActivity()).Server + "me";

                        AsyncTask<String, Void, Boolean> execute = new HttpFileUploader(((MainActivity) getActivity()),file.getAbsolutePath()).execute(requestURL);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }




 /*
                    HttpURLConnection httpUrlConnection = null;
                    String boundary =  "*****";
                    String crlf = "\r\n";
                    String twoHyphens = "--";
                    String attachmentName = "bitmap";
                    String attachmentFileName = "bitmap.bmp";

                      Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    //Bitmap bitmap = ImgProf.getBitmap();
                  try {
                        //Setup the request:
                        URL url = new URL(requestURL);
                        Log.v("My project requestURL", requestURL);

                        httpUrlConnection = (HttpURLConnection) url.openConnection();
                   //     httpUrlConnection.setUseCaches(false);
                         httpUrlConnection.setDoInput(true);
                        httpUrlConnection.setDoOutput(true);


                        httpUrlConnection.setRequestMethod("POST");
                        httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
                        httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
                       httpUrlConnection.setRequestProperty(
                                "Content-Type", "multipart/form-data;boundary=" + boundary);

                      httpUrlConnection.setReadTimeout(15000);
                      httpUrlConnection.setConnectTimeout(15000);
                      httpUrlConnection.connect();
                      Log.v("My project response String",  "jjj");
                        //Start content wrapper:

                        DataOutputStream request = new DataOutputStream(
                                httpUrlConnection.getOutputStream());

                        request.writeBytes(twoHyphens + boundary + crlf);
                        request.writeBytes("Content-Disposition: form-data; name=\"" +
                                attachmentName + "\";filename=\"" +
                                attachmentFileName + "\"" + crlf);
                        request.writeBytes(crlf);


                       // Convert Bitmap to ByteBuffer:

                        //I want to send only 8 bit black & white bitmaps

                        byte[] pixels = new byte[bitmap.getWidth() * bitmap.getHeight()];
                        for (int i = 0; i < bitmap.getWidth(); ++i) {
                            for (int j = 0; j < bitmap.getHeight(); ++j) {
                                //we're interested only in the MSB of the first byte,
                                //since the other 3 bytes are identical for B&W images
                                pixels[i + j] = (byte) ((bitmap.getPixel(i, j) & 0x80) >> 7);
                            }
                        }

                        request.write(pixels);
                        Log.v("My project pixels",  String.valueOf(pixels));

                       /// End content wrapper:

                        request.writeBytes(crlf);
                        request.writeBytes(twoHyphens + boundary +
                                twoHyphens + crlf);
                        //Flush output buffer:

                        request.flush();
                        request.close();

                        //Get response:

                        InputStream responseStream = new
                                BufferedInputStream(httpUrlConnection.getInputStream());

                        BufferedReader responseStreamReader =
                                new BufferedReader(new InputStreamReader(responseStream));

                        String line = "";
                        StringBuilder stringBuilder = new StringBuilder();

                        while ((line = responseStreamReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        responseStreamReader.close();

                        String response = stringBuilder.toString();

                        Log.v("My project response String",  response);

                        ///Close response stream:



                        responseStream.close();
                        //Close the connection:

                        httpUrlConnection.disconnect();


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
*/


                }
            });


        }

          //  Log.v("My project  MakeFot", "savedInstanceState != null");
            if(file.exists()) {
                Log.v("My project  MakeFot", "file.exists");
                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                ImgProf.setImageBitmap(myBitmap);
            }

        return rootView;

    }

    //просто поучить миниатюру
    private void getThumbailPicture() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_RESULT);
    }

    private void saveFullImage() {//сохранение фото
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(Environment.getExternalStorageDirectory(),
                "test.jpg");
        outputFileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, CAMERA_RESULT);
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
            Log.v("My project  ", "CAMERA_RESULT");
            Uri imageUri = null;
            // Check if the result includes a thumbnail Bitmap
            if (data != null) {
                Log.v("My project  ", "data != null");
              //  Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                Bitmap thumbnail = data.getParcelableExtra("data");
                ImgProf.setImageBitmap(thumbnail);
            }//else{
                file = new File(Environment.getExternalStorageDirectory(),
                        "test.jpg");
                if(file.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    ImgProf.setImageBitmap(myBitmap);
                }
           // }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putString("time_key", mTime);
        // outState.putAll(outState);
    }
}