package io.github.diegocdl.sesnor_temperatura;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.diegocdl.sesnor_temperatura.data.DateListAdapter;

public class HistoryDateSelectActivity extends AppCompatActivity implements View.OnClickListener {
    public static String DATE = "date";
    @BindView(R.id.date_list)
        RecyclerView dateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_date_select);
        ButterKnife.bind(this);

        dateList.setLayoutManager(new LinearLayoutManager(this));
        dateList.setAdapter(new DateListAdapter(SensorMainActivity.db.getDates(), this));

    }

    @Override
    public void onClick(View view) {
        String date = ((TextView)view.findViewById(R.id.date)).getText().toString();
        Intent i = new Intent(this, TempHistoryActivity.class);
        i.putExtra(DATE, date);
        startActivity(i);
    }
}
