package com.example.android.allocate.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.android.allocate.PieChartHandler;
import com.example.android.allocate.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

public class ViewStatsActivity extends AppCompatActivity {

    private PieChart mPieChart;
    private PieChartHandler mPieChartHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stats);

        Toolbar toolbar = (Toolbar) findViewById(R.id.stats_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Stats");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPieChart = (PieChart) findViewById(R.id.pie_chart);

        mPieChartHandler = new PieChartHandler(this);

        mPieChartHandler.addData(mPieChart);
    }
}
