package io.github.diegocdl.sesnor_temperatura.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.diegocdl.sesnor_temperatura.R;

/**
 * Created by diego on 6/6/2017.
 */

public class TemperatureAdapter extends RecyclerView.Adapter<TemperatureAdapter.ViewHolder>{
    protected Context context;
    protected ArrayList<TempRegister> data;

    public TemperatureAdapter(Context ctx) {
        this.context = ctx;
        data = new ArrayList<>();
    }

    public TemperatureAdapter(Context ctx, ArrayList<TempRegister> arrayList) {
        this(ctx);
        data = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.temperature_list_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TempRegister tempRegister = data.get(position);

        holder.tempDate.setText(tempRegister.getDate());
        holder.tempTime.setText(tempRegister.getTime());
        holder.tempValue.setText(tempRegister.getValue());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addElement(TempRegister e) {
        data.add(0, e);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        protected View view;

        @BindView(R.id.temp_date) TextView tempDate;
        @BindView(R.id.temp_time) TextView tempTime;
        @BindView(R.id.temp_value) TextView tempValue;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
