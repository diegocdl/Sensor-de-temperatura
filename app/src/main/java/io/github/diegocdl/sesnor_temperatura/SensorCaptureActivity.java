package io.github.diegocdl.sesnor_temperatura;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.diegocdl.sesnor_temperatura.data.CommunicationInterface;
import io.github.diegocdl.sesnor_temperatura.data.DbHelper;
import io.github.diegocdl.sesnor_temperatura.data.TempRegister;
import io.github.diegocdl.sesnor_temperatura.data.TemperatureAdapter;

public class SensorCaptureActivity extends AppCompatActivity implements CommunicationInterface, View.OnClickListener {
    private static final int CONNECT_DEVICE = 1;
    TemperatureAdapter temperatureAdapter;
    LinearLayoutManager mLayoutManager;
    Handler mainUIHandler;
    DbHelper db;

    @BindView(R.id.temperature_list)
        RecyclerView temperatureList;

    @BindView(R.id.current_temp)
        TextView currentTemp;
    @BindView(R.id.temp_container)
        RelativeLayout tempContainer;
    @BindView(R.id.floatingActionButton3)
        FloatingActionButton connectBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_sensor);
        ButterKnife.bind(this);
        db = new DbHelper(this);

        connectBtn.setOnClickListener(this);

        // communicate the UI thread with the connection thread
        mainUIHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                TempRegister tr = (TempRegister) msg.obj;
                temperatureAdapter.addElement(tr);
                currentTemp.setText(tr.getValue() + " °C");
                db.insert(tr);
//                float hue = 30 + 240 * (30 - Float.parseFloat(tr.getValue())) / 60;
//                tempContainer.setBackgroundColor(Color.HSVToColor(new float[]{hue, 100, 100}));
            }
        };
        // initializes the list of temperatures
        temperatureAdapter = new TemperatureAdapter(this);
        mLayoutManager = new LinearLayoutManager(this);
        temperatureList.setLayoutManager(mLayoutManager);
        temperatureList.setAdapter(temperatureAdapter);

        Intent intent = new Intent(this, SelectDeviceActivity.class);
        startActivityForResult(intent, CONNECT_DEVICE);
        Log.e("DB log", db.getDates().toString());
        Log.e("DB log", db.getLogs("2017/06/14").toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CONNECT_DEVICE) {
            if(resultCode == Activity.RESULT_OK) {

                String dName = data.getStringExtra(SelectDeviceActivity.SELECTED_DEVICE);
                Log.e("fdgnfd", "fsdjfgndkg " + dName);
                connect(dName);
            }
        }
    }

    public void connect(String dName) {
        BluetoothAdapter mBluetoothAdapter = null;
        BluetoothDevice device = null;
        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            for(BluetoothDevice d : pairedDevices)
                if(d.getName().equals(dName)) device = d;

            ConnectThread connectThread = null;
            try {
                connectThread = new ConnectThread(device, this, mainUIHandler);
                connectThread.start();
            } catch (IOException e) {
                Snackbar.make(findViewById(R.id.constraintLayout), "Dispositivo Bluetooth no emparejado aun",Snackbar.LENGTH_INDEFINITE).show();
            }

        } catch(Exception e) { }
    }

    @Override
    public void reportConnectionError(String error) {

        //Snackbar.make(findViewById(R.id.constraintLayout), error, Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public void dataReceived(TempRegister tr) {
//        currentTemp.setText(tr.getValue() + " °C");
    }

    @Override
    public void onClick(View view) {
        if(view == connectBtn){
            Intent intent = new Intent(this, SelectDeviceActivity.class);
            startActivityForResult(intent, CONNECT_DEVICE);
        }
    }
}
