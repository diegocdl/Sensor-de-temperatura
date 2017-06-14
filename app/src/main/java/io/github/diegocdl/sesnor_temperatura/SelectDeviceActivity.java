package io.github.diegocdl.sesnor_temperatura;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.diegocdl.sesnor_temperatura.data.DeviceListAdapter;

public class SelectDeviceActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String SELECTED_DEVICE = "selected_device";
    @BindView(R.id.deviceList)
        RecyclerView deviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_device);
        ButterKnife.bind(this);

        deviceList.setLayoutManager(new LinearLayoutManager(this));
        deviceList.setAdapter(new DeviceListAdapter(getBluetoothDevices(),this));

    }

    public ArrayList<BluetoothDevice> getBluetoothDevices() {
        BluetoothAdapter mBluetoothAdapter = null;
        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        } catch(Exception e) { }
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        return new ArrayList<BluetoothDevice>(pairedDevices);
    }

    @Override
    public void onClick(View view) {
        String dName = ((TextView)view.findViewById(R.id.device_name)).getText().toString();
        Intent i = new Intent();
        i.putExtra(SELECTED_DEVICE, dName);
        this.setResult(Activity.RESULT_OK, i);
        finish();
    }
}
