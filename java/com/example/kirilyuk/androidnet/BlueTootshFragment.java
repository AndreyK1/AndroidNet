package com.example.kirilyuk.androidnet;

import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaRecorder;
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
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by Kirilyuk on 12.01.2016.
 */
public class BlueTootshFragment extends Fragment {
/*
    public final int CAMERA_RESULT = 0;
    ImageView ImgProf;
    private Uri outputFileUri;*/
    //File file;
   // File file= new File(Environment.getExternalStorageDirectory(),        "test.jpg");
    TextView labelStateBluetooth;
    BluetoothAdapter bluetoothAdapter;
    Button buttonBluetoothState;
    Button buttonSearchOther;

    Button buttonMedRec;
    Button buttonMedRecS;
    TextView labelMedRec;
    public MediaRecorder mrec = null;
    File audiofile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);

    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_blooetoth, container, false);
        if (savedInstanceState == null) {

            labelStateBluetooth = (TextView) rootView.findViewById(R.id.texBluetoothstate);
            buttonBluetoothState = (Button) rootView.findViewById(R.id.butBluetoth);
            buttonSearchOther = (Button) rootView.findViewById(R.id.butSearchNeibor);


            labelMedRec = (TextView) rootView.findViewById(R.id.texMediaRec);
            buttonMedRec = (Button) rootView.findViewById(R.id.butMediaRec);
            buttonMedRecS = (Button) rootView.findViewById(R.id.butMediaRecS);
            mrec = new MediaRecorder();

            // получаем адаптер по умолчанию
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            final EditText EdEmail = (EditText) rootView.findViewById(R.id.edEmail);

           //включение nBluetooth
            buttonBluetoothState.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (bluetoothAdapter == null) {
                        labelStateBluetooth
                                .setText("Bluetooth на вашем устройстве не поддерживается");
                    } else {
                        if (bluetoothAdapter.isEnabled()) {
                            if (bluetoothAdapter.isDiscovering()) {
                                labelStateBluetooth
                                        .setText("Bluetooth в процессе включения.");
                            } else {

                                String address = bluetoothAdapter.getAddress();
                                String name = bluetoothAdapter.getName();
                                labelStateBluetooth.setText("Bluetooth доступен. address - "+address+" name - "+name);

                                //отключаем Bluetooth
                                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                                startActivity(enableBtIntent);

                            }
                        } else {
                            labelStateBluetooth.setText("Bluetooth не доступен!");
                            //пробуем включить
                            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivity(enableBtIntent);

                           //меняем Обнаруживаемость Bluetooth-адаптера
                            int DISCOVERY_REQUEST = 1;
                            String aDiscoverable = BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE;
                            Intent MDisc = new Intent(aDiscoverable);
                            MDisc.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,0);
                            startActivityForResult(MDisc,
                                    DISCOVERY_REQUEST);

                            buttonBluetoothState.setText("отключить Bluetooth");

                        }
                    }
                }
            });



            //поиск соседей nBluetooth
            buttonSearchOther.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (bluetoothAdapter == null) {
                        labelStateBluetooth
                                .setText("Bluetooth на вашем устройстве не поддерживается");
                    } else {
                        if (bluetoothAdapter.isEnabled()) {
                           /* if (bluetoothAdapter.isDiscovering()) {
                                labelStateBluetooth
                                        .setText("Bluetooth в процессе включения.");
                            } else {*/

                                String address = bluetoothAdapter.getAddress();
                                String name = bluetoothAdapter.getName();
                                labelStateBluetooth.setText("Bluetooth доступен. address - "+address+" name - "+name);

                                //поиск
                                if(bluetoothAdapter.isDiscovering()){
                                    bluetoothAdapter.cancelDiscovery();

                                }else{
                                    bluetoothAdapter.startDiscovery();

                                }



                           // }
                        } else {
                            labelStateBluetooth.setText("Bluetooth не доступен!");
                        }
                    }
                }
            });


            //пробуем записать наушники
            buttonMedRec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    labelMedRec.setText("пишем");
                    startRecording();
                   // testCreateAudioRecord();
                   // AudioManager.
                    AudioManager audioManager = (AudioManager) ((MainActivity) getActivity()).getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setMode(AudioManager.MODE_IN_CALL);

                    if (audioManager.isBluetoothA2dpOn()) {
                        // Adjust output for Bluetooth.
                    } else if (audioManager.isSpeakerphoneOn()) {
                        // Adjust output for Speakerphone.
                        Toast.makeText((MainActivity) getActivity(),
                                "Speakerphone", Toast.LENGTH_SHORT).show();
                    } else if (audioManager.isWiredHeadsetOn()) {
                        // Adjust output for headsets
                        Toast.makeText((MainActivity) getActivity(),
                                "sWiredHeadsetOn", Toast.LENGTH_SHORT).show();
                    } else {
                        // If audio plays and noone can hear it, is it still playing?
                    }
                }
            });
            //пробуем остан наушники
            buttonMedRecS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    labelMedRec.setText( labelMedRec.getText()+ "стоп");
                    stopRecording();
                }
            });



            final String  dStarted = BluetoothAdapter.ACTION_DISCOVERY_STARTED;
            final String dFinished = BluetoothAdapter.ACTION_DISCOVERY_FINISHED;

            //отображение изменния режима поиска
            BroadcastReceiver discoveryMonitor = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (dStarted.equals(intent.getAction())) {
                        // Процесс обнаружения начался.
                        Toast.makeText((MainActivity) getActivity(),
                                "Discovery Started...", Toast.LENGTH_SHORT).show();
                        labelStateBluetooth
                                .setText("Discovery Started.");
                    }
                    else if (dFinished.equals(intent.getAction())) {
                        // Процесс обнаружения завершился.
                        Toast.makeText((MainActivity) getActivity(),
                                "Discovery Completed...", Toast.LENGTH_SHORT).show();
                        labelStateBluetooth
                                .setText("Discovery Completed.");

                        //запускаем заново
                        bluetoothAdapter.startDiscovery();
                    }
                }
            };

            ((MainActivity) getActivity()).registerReceiver(discoveryMonitor, new IntentFilter(dStarted));
            ((MainActivity) getActivity()).registerReceiver(discoveryMonitor, new IntentFilter(dFinished));


            //Обнаруженные Bluetooth-устройства возвращаются через Широковещательные намерения с действием ACTION_FOUND.
            //ПРИЕМНИК отображения найденных устройств
            BroadcastReceiver discoveryResult = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String remoteDeviceName =
                            intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
                //    String remoteDeviceUUID =  intent.getStringExtra(BluetoothDevice.EXTRA_UUID);
                    BluetoothDevice remoteDevice;
                    remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    String remoteDeviceAddress = remoteDevice.getAddress();

                            Toast.makeText((MainActivity) getActivity(),
                            "Discovered: " + remoteDeviceName+" - "+remoteDeviceAddress,
                            Toast.LENGTH_SHORT).show();
                    labelStateBluetooth
                            .setText( "Discovered: " + remoteDeviceName+" - "+remoteDeviceAddress);
                    // TODO Сделать что-нибудь с объектом BluetoothDevice.
                }
            };

            ((MainActivity) getActivity()).registerReceiver(discoveryResult, new IntentFilter(BluetoothDevice.ACTION_FOUND));

        }

        return rootView;

    }




    protected void startRecording(){
        mrec.setAudioSource(MediaRecorder.AudioSource.MIC);
       // mrec.setAudioSource(MediaRecorder.AudioSource.REMOTE_SUBMIX);
        mrec.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mrec.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        //REMOTE_SUBMIX
        //http://stackoverflow.com/questions/26363669/how-to-record-submix-on-android-device-in-realtime-if-its-possible
        //http://stackoverflow.com/questions/25884182/how-to-record-android-audio-playing-in-headset


        if (audiofile == null) {
            File sampleDir = Environment.getExternalStorageDirectory();

            try {
                audiofile = File.createTempFile("meow", ".3gp", sampleDir);
                labelMedRec.setText(audiofile.getPath());
            } catch (IOException e) {
                Log.e("retret", "sdcard access error");
                // return '';
               // audiofile.getPath();
                labelMedRec.setText(audiofile.getAbsolutePath());
                return;
            }
        }

        mrec.setOutputFile(audiofile.getAbsolutePath());

        try {
            mrec.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mrec.start();
    }

    protected void stopRecording() {
        mrec.stop();
        mrec.release();
    }





/*
    public void testCreateAudioRecord() {
        final int bufferSize = AudioRecord.getMinBufferSize(44100,
                AudioFormat.CHANNEL_IN_STEREO, AudioFormat.ENCODING_PCM_16BIT);
        // The attempt to create the AudioRecord object succeeds even if the
        // app does not have permission, but the object is not usable.
        // The API should probably throw SecurityException but it was not originally
        // designed to do that and it's not clear we can change it now.
        AudioRecord record = new AudioRecord(MediaRecorder.AudioSource.REMOTE_SUBMIX, 44100,
                AudioFormat.CHANNEL_IN_STEREO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);
        try {

            if(record.getState() !=  AudioRecord.STATE_INITIALIZED){
                labelMedRec.setText("AudioRecord state should not be INITIALIZED");
            }


        } finally {
            record.release();
        }
    }
*/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putString("time_key", mTime);
        // outState.putAll(outState);
    }
}