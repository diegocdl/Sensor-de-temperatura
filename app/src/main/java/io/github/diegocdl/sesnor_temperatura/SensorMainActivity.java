package io.github.diegocdl.sesnor_temperatura;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.diegocdl.sesnor_temperatura.data.CommunicationInterface;
import io.github.diegocdl.sesnor_temperatura.data.DbHelper;
import io.github.diegocdl.sesnor_temperatura.data.TempRegister;
import io.github.diegocdl.sesnor_temperatura.data.TemperatureAdapter;

public class SensorMainActivity extends AppCompatActivity implements View.OnClickListener {
    public static DbHelper db;

    @BindView(R.id.history)
        Button historyBtn;

    @BindView(R.id.connect)
        Button connectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_main);
        ButterKnife.bind(this);
        db = new DbHelper(this);
        connectBtn.setOnClickListener(this);
        historyBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == connectBtn) {
            Intent intent = new Intent(this, SensorCaptureActivity.class);
            startActivity(intent);
        } else if(view == historyBtn) {
            Intent intent = new Intent(this, HistoryDateSelectActivity.class);
            startActivity(intent);
        }
    }
}
