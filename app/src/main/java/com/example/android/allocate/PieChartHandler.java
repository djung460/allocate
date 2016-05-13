package com.example.android.allocate;

import android.content.Context;
import android.util.Log;

import com.example.android.allocate.db.TaskDatabaseHelper;
import com.example.android.allocate.task.Task;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by David Jung on 13/05/16.
 */
public class PieChartHandler {
    private PieChart mPieChart;
    private ArrayList<String> mLabels;
    private ArrayList<Entry> mEntries;
    private PieDataSet mDataset;

    private TaskDatabaseHelper mTaskDatabaseHelper;
    private Context mContext;

    public PieChartHandler(Context context) {
        mTaskDatabaseHelper = new TaskDatabaseHelper(context);
        mContext = context;
    }

    public void addData(PieChart pieChart) {
        mPieChart = pieChart;

        float fracTimeUsed = getFractionTimeUsed();

        mEntries = new ArrayList<>();
        mEntries.add(new Entry(fracTimeUsed, 0));
        mEntries.add(new Entry(1f-fracTimeUsed, 1));

        mDataset = new PieDataSet(mEntries, "");

        mLabels = new ArrayList<>();
        mLabels.add("Time Used");
        mLabels.add("Time Left");

        customizeChart();

        PieData data = new PieData(mLabels, mDataset);
        mPieChart.setData(data);
    }

    private void customizeChart() {
        mPieChart.setDescription("");
        mPieChart.setUsePercentValues(true);
        mDataset.setValueTextSize(16);
        mDataset.setColors(ColorTemplate.JOYFUL_COLORS); // set the color
        mPieChart.setHoleRadius(30);
        mPieChart.getLegend().setTextSize(16);
    }


    private float getFractionTimeUsed() {
        ArrayList<Task> allTasks = mTaskDatabaseHelper.getAllTasks();

        long totalTime = 0;
        long timeUsed = 0;
        for(Task t : allTasks) {
            totalTime += t.getInitialTime();
            timeUsed += t.getInitialTime() - t.getTimeLeft();
        }

        Log.i("PieChartHandler",Float.toString((float) timeUsed / (float) totalTime));

        if(totalTime == 0 || timeUsed == 0)
            return 0f;

        return (float) timeUsed / (float) totalTime;
    }
}
