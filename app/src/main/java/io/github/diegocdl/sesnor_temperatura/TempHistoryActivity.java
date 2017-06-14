
package io.github.diegocdl.sesnor_temperatura;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.diegocdl.sesnor_temperatura.data.TempRegister;
import io.github.diegocdl.sesnor_temperatura.data.TemperatureAdapter;

public class TempHistoryActivity extends AppCompatActivity {
    @BindView(R.id.temp_history)
        RecyclerView tempHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_history);
        ButterKnife.bind(this);

        Intent i = getIntent();
        String date = i.getStringExtra(HistoryDateSelectActivity.DATE);

        ArrayList<TempRegister> arrayList = SensorMainActivity.db.getLogs(date);
        tempHistory.setLayoutManager(new LinearLayoutManager(this));
        tempHistory.setAdapter(new TemperatureAdapter(this, arrayList));

    }
}
