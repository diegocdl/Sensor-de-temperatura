package io.github.diegocdl.sesnor_temperatura.data;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
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

public class DateListAdapter extends RecyclerView.Adapter<DateListAdapter.ViewHolder>{
    ArrayList<String> arrayList;
    View.OnClickListener onClickListener;

    public DateListAdapter(ArrayList<String> data, View.OnClickListener listener) {
        super();
        arrayList = data;
        onClickListener = listener;
    }

    @Override
    public DateListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.date_list_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String date = arrayList.get(position);
        holder.date.setText(date);
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        protected View view;

        @BindView(R.id.date)
            TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
            view.setOnClickListener(onClickListener);
        }
    }
}
