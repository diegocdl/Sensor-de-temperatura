package io.github.diegocdl.sesnor_temperatura.data;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.diegocdl.sesnor_temperatura.R;

/**
 * Created by diego on 6/13/2017.
 */

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder>{
    ArrayList<BluetoothDevice> arrayList;
    View.OnClickListener onClickListener;

    public DeviceListAdapter(ArrayList<BluetoothDevice> data, View.OnClickListener listener) {
        super();
        arrayList = data;
        onClickListener = listener;
    }

    @Override
    public DeviceListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_list_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BluetoothDevice device = arrayList.get(position);
        holder.deviceName.setText(device.getName());
        holder.deviceAddress.setText(device.getAddress());
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        protected View view;

        @BindView(R.id.device_name)
            TextView deviceName;

        @BindView(R.id.device_address)
            TextView deviceAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
            view.setOnClickListener(onClickListener);
        }
    }
}
