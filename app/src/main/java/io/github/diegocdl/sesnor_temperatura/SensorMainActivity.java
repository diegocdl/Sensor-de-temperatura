package io.github.diegocdl.sesnor_temperatura;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.diegocdl.sesnor_temperatura.data.CommunicationInterface;
import io.github.diegocdl.sesnor_temperatura.data.TempRegister;
import io.github.diegocdl.sesnor_temperatura.data.TemperatureAdapter;

public class SensorMainActivity extends AppCompatActivity implements CommunicationInterface {
    TemperatureAdapter temperatureAdapter;
    LinearLayoutManager mLayoutManager;
    Handler mainUIHandler;
    @BindView(R.id.temperature_list)
        RecyclerView temperatureList;

    @BindView(R.id.current_temp)
        TextView currentTemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_main);
        ButterKnife.bind(this);
        BluetoothAdapter mBluetoothAdapter = null;
        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        } catch(Exception e) { }
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        BluetoothDevice device = null;

        for(BluetoothDevice d : pairedDevices){
            if(d.getName().equals("HC-06")) {
                device = d;
            }
            Log.e("Bluetooth", d.getName() + " " + d.getAddress());
        }

        mainUIHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                TempRegister tr = (TempRegister) msg.obj;
                temperatureAdapter.addElement(tr);
                currentTemp.setText(tr.getValue() + " °C");
            }
        };

        if(device != null) {

            ConnectThread connectThread = null;
            try {
                connectThread = new ConnectThread(device, this, mainUIHandler);
                connectThread.start();
            } catch (IOException e) {
                Snackbar.make(findViewById(R.id.constraintLayout), "Dispositivo Bluetooth no emparejado aun",Snackbar.LENGTH_INDEFINITE).show();
            }

        } else {
            Snackbar.make(findViewById(R.id.constraintLayout), "Dispositivo Bluetooth no emparejado aun",Snackbar.LENGTH_INDEFINITE).show();
        }
        temperatureAdapter = new TemperatureAdapter(this);
        mLayoutManager = new LinearLayoutManager(this);
        temperatureList.setLayoutManager(mLayoutManager);

        temperatureList.setAdapter(temperatureAdapter);

    }


    @Override
    public void reportConnectionError(String error) {

        //Snackbar.make(findViewById(R.id.constraintLayout), error, Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public void dataReceived(TempRegister tr) {
//        currentTemp.setText(tr.getValue() + " °C");
    }
}
