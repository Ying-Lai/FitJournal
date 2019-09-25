package com.gmail.plai2.ying.fitjournal.ui.stats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.gmail.plai2.ying.fitjournal.MainActivity;
import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.Stat;
import com.gmail.plai2.ying.fitjournal.room.StatType;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StatsFragment extends Fragment implements AddStatDialogFragment.StatListener {

    // Input fields
    private String mWeightInput;
    private String mFatInput;
    private String mStatType;
    private boolean mShouldUpdate = false;

    // UI fields
    private MaterialToolbar mToolbar;
    private MaterialTextView mWeightInputTV;
    private MaterialTextView mFatInputTV;
    private MaterialButton mUpdateWeightButton;
    private MaterialButton mUpdateFatButton;
    private LineChart mWeightChart;
    private LineChart mFatChart;

    // View model fields
    private StatsViewModel mViewModel;

    public StatsFragment() {
        // To enable menu for this fragment
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // To enable menu for this fragment
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Initialize fields and variables
        View root = inflater.inflate(R.layout.fragment_stats, container, false);
        mToolbar = root.findViewById(R.id.stats_tb);
        mWeightInputTV = root.findViewById(R.id.stats_weight_input_tv);
        mFatInputTV = root.findViewById(R.id.stats_body_fat_input_tv);
        mUpdateWeightButton = root.findViewById(R.id.stats_update_weight_btn);
        mUpdateFatButton = root.findViewById(R.id.stats_update_fat_btn);
        mWeightChart = root.findViewById(R.id.weight_chart);
        mFatChart = root.findViewById(R.id.fat_chart);

        // Setup app tool bar
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // On click listeners
        mUpdateWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDialogFragment(StatType.WEIGHT, "update_weight");
            }
        });
        mUpdateFatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDialogFragment(StatType.BODYFAT, "update_fat");
            }
        });

        // Observe live data
        mViewModel = ViewModelProviders.of(getActivity()).get(StatsViewModel.class);
        mViewModel.getAllStats().observe(getViewLifecycleOwner(), new Observer<List<Stat>>() {
            @Override
            public void onChanged(List<Stat> stats) {

                // If there is no data, show no data text
                if (stats.size() == 0) {

                    mWeightChart.setNoDataTextColor(getResources().getColor(R.color.evening_blue, null));
                    mWeightChart.setNoDataTextTypeface(getResources().getFont(R.font.open_sans));
                    mWeightChart.setNoDataText(getResources().getString(R.string.weight_no_data));

                    mFatChart.setNoDataTextColor(getResources().getColor(R.color.evening_blue, null));
                    mFatChart.setNoDataTextTypeface(getResources().getFont(R.font.open_sans));
                    mFatChart.setNoDataText(getResources().getString(R.string.fat_no_data));

                    mWeightChart.invalidate();
                    mFatChart.invalidate();
                    return;
                }

                // Sort stat list
                Collections.sort(stats, new Comparator<Stat>() {
                    public int compare(Stat o1, Stat o2) {
                        return o1.getMDate().compareTo(o2.getMDate());
                    }
                });

                // Check and set data for today
                Stat lastEntry = stats.get(stats.size()-1);
                if (lastEntry.getMDate().isEqual(LocalDate.now())) {
                    mShouldUpdate = true;
                    mWeightInput = lastEntry.getMWeight()+"";
                    if (lastEntry.getMWeight() != MainActivity.EMPTY) {
                        String displayWeight = mWeightInput + " lbs";
                        mWeightInputTV.setText(displayWeight);
                        mUpdateWeightButton.setText(getResources().getString(R.string.update));
                    } else {
                        mWeightInputTV.setText(getResources().getString(R.string.none));
                        mUpdateWeightButton.setText(getResources().getString(R.string.add));
                    }
                    mFatInput = lastEntry.getMBodyFat()+"";
                    if (lastEntry.getMBodyFat() != MainActivity.EMPTY) {
                        String displayFat = mFatInput + "%";
                        mFatInputTV.setText(displayFat);
                        mUpdateFatButton.setText(getResources().getString(R.string.update));
                    } else {
                        mFatInputTV.setText(getResources().getString(R.string.none));
                        mUpdateFatButton.setText(getResources().getString(R.string.add));
                    }
                } else {
                    mWeightInput = MainActivity.EMPTY + "";
                    mFatInput = MainActivity.EMPTY + "";
                    mWeightInputTV.setText(getResources().getString(R.string.none));
                    mFatInputTV.setText(getResources().getString(R.string.none));
                }

                // Y bounds for charts
                int yWeightMax = 0;
                int yWeightMin = Integer.MAX_VALUE;
                int yBodyFatMax = 0;
                int yBodyFatMin = 100;

                // Create entries
                List<Entry> weightEntries = new ArrayList<Entry>();
                List<Entry> fatEntries = new ArrayList<Entry>();
                List<Stat> weightOnlyList = new ArrayList<Stat>();
                List<Stat> fatOnlyList = new ArrayList<Stat>();

                for (Stat stat : stats) {
                    if (stat.getMWeight() != MainActivity.EMPTY) {
                        weightOnlyList.add(stat);
                        weightEntries.add(new Entry(weightOnlyList.size(), stat.getMWeight()));
                        if (stat.getMWeight() > yWeightMax) yWeightMax = stat.getMWeight();
                        if (stat.getMWeight() < yWeightMin) yWeightMin = stat.getMWeight();
                     }
                    if (stat.getMBodyFat() != MainActivity.EMPTY) {
                        fatOnlyList.add(stat);
                        fatEntries.add(new Entry(fatOnlyList.size(), stat.getMBodyFat()));
                        if (stat.getMBodyFat() > yBodyFatMax) yBodyFatMax = stat.getMBodyFat();
                        if (stat.getMBodyFat() < yBodyFatMin) yBodyFatMin = stat.getMBodyFat();
                    }
                }

                // X bounds for charts
                float xWeightMin = weightEntries.get(0).getX() - 1;
                float xWeightMax = weightEntries.get(weightEntries.size()-1).getX() + 1;
                float xBodyFatMin = fatEntries.get(0).getX() - 1;
                float xBodyFatMax = fatEntries.get(fatEntries.size()-1).getX() + 1;

                // Create line data sets
                LineDataSet weightLineDataSet = new LineDataSet(weightEntries, "Weight (lb)");
                LineDataSet bodyFatLineDataSet = new LineDataSet(fatEntries, "Body Fat (%)");

                FloatToIntegerFormatter integerFormatter = new FloatToIntegerFormatter();

                // Format body weight data set
                weightLineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                weightLineDataSet.setValueFormatter(integerFormatter);
                weightLineDataSet.setLineWidth(3f);
                weightLineDataSet.setValueTextSize(10f);
                weightLineDataSet.setColor(getResources().getColor(R.color.reply_orange, null));
                weightLineDataSet.setCircleColor(getResources().getColor(R.color.reply_orange, null));
                weightLineDataSet.setCircleHoleColor(getResources().getColor(R.color.reply_orange, null));
                weightLineDataSet.setCircleRadius(4f);

                // Format body fat data set
                bodyFatLineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                bodyFatLineDataSet.setValueFormatter(integerFormatter);
                bodyFatLineDataSet.setLineWidth(3f);
                bodyFatLineDataSet.setValueTextSize(10f);
                bodyFatLineDataSet.setColor(getResources().getColor(R.color.sailor_blue, null));
                bodyFatLineDataSet.setCircleColor(getResources().getColor(R.color.sailor_blue, null));
                bodyFatLineDataSet.setCircleHoleColor(getResources().getColor(R.color.sailor_blue, null));
                bodyFatLineDataSet.setCircleRadius(4f);

                // Create list of data with line data sets
                List<ILineDataSet> weightListOfData= new ArrayList<ILineDataSet>();
                List<ILineDataSet> bodyFatListOfData = new ArrayList<ILineDataSet>();
                weightListOfData.add(weightLineDataSet);
                bodyFatListOfData.add(bodyFatLineDataSet);

                // Create line data
                LineData weightLineData = new LineData(weightListOfData);
                LineData bodyFatLineData = new LineData(bodyFatListOfData);

                // Format typeface for line data
                weightLineData.setValueTypeface(getResources().getFont(R.font.open_sans));
                bodyFatLineData.setValueTypeface(getResources().getFont(R.font.open_sans));

                // Apply axis label formatter
                CustomDateFormatter weightDateFormatter = new CustomDateFormatter(weightOnlyList);
                CustomDateFormatter fatDateFormatter = new CustomDateFormatter(fatOnlyList);
                mWeightChart.getXAxis().setValueFormatter(weightDateFormatter);
                mFatChart.getXAxis().setValueFormatter(fatDateFormatter);
                mWeightChart.getXAxis().setTypeface(getResources().getFont(R.font.open_sans));
                mFatChart.getXAxis().setTypeface(getResources().getFont(R.font.open_sans));
                mWeightChart.getXAxis().setGranularity(1f);
                mFatChart.getXAxis().setGranularity(1f);

                // Set X, Y bounds
                mWeightChart.getXAxis().setAxisMinimum(xWeightMin);
                mWeightChart.getXAxis().setAxisMaximum(xWeightMax);
                mWeightChart.getAxisLeft().setAxisMinimum(yWeightMin-25>0?yWeightMin-25:0);
                mWeightChart.getAxisLeft().setAxisMaximum(yWeightMax+25);

                mFatChart.getXAxis().setAxisMinimum(xBodyFatMin);
                mFatChart.getXAxis().setAxisMaximum(xBodyFatMax);
                mFatChart.getAxisLeft().setAxisMinimum(yBodyFatMin-10>0?yBodyFatMin-10:0);
                mFatChart.getAxisLeft().setAxisMaximum(yBodyFatMax+10);

                // Set max visible range and initial view
                mWeightChart.setVisibleXRangeMaximum(8);
                mWeightChart.setVisibleXRangeMinimum(2);
                mWeightChart.moveViewToX(weightEntries.size() + 3);
                mFatChart.setVisibleXRangeMaximum(8);
                mFatChart.setVisibleXRangeMinimum(2);
                mFatChart.moveViewToX(fatEntries.size() + 3);

                // Other styling
                // No grid lines
                mWeightChart.getAxisRight().setDrawGridLines(false);
                mWeightChart.getXAxis().setDrawGridLines(false);
                mWeightChart.getAxisLeft().setDrawGridLines(false);
                mFatChart.getAxisRight().setDrawGridLines(false);
                mFatChart.getXAxis().setDrawGridLines(false);
                mFatChart.getAxisLeft().setDrawGridLines(false);

                // No left and right axis lines
                mWeightChart.getAxisLeft().setDrawLabels(false);
                mWeightChart.getAxisLeft().setDrawAxisLine(false);
                mWeightChart.getAxisRight().setDrawLabels(false);
                mWeightChart.getAxisRight().setDrawAxisLine(false);
                mFatChart.getAxisLeft().setDrawAxisLine(false);
                mFatChart.getAxisLeft().setDrawLabels(false);
                mFatChart.getAxisRight().setDrawAxisLine(false);
                mFatChart.getAxisRight().setDrawLabels(false);

                // No description label
                mWeightChart.getDescription().setEnabled(false);
                mFatChart.getDescription().setEnabled(false);

                // Legend formatting
                mWeightChart.getLegend().setTextSize(10f);
                mWeightChart.getLegend().setTypeface(getResources().getFont(R.font.open_sans));
                mFatChart.getLegend().setTextSize(10f);
                mFatChart.getLegend().setTypeface(getResources().getFont(R.font.open_sans));

                // Disable double tap to zoom
                mWeightChart.setDoubleTapToZoomEnabled(false);
                mFatChart.setDoubleTapToZoomEnabled(false);

                // Set the data and refresh the chart
                mWeightChart.setData(weightLineData);
                mFatChart.setData(bodyFatLineData);

                // Refresh
                mWeightChart.animateX(500, Easing.EaseInCubic);
                mFatChart.animateX(500, Easing.EaseInCubic);
            }
        });
    }

    // Setup menu options
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.workout_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void sendStat(StatType statType, int data) {
        if (statType == StatType.WEIGHT) {
            if (mShouldUpdate) {
                mViewModel.update(new Stat(LocalDate.now(), data, Integer.parseInt(mFatInput)));
            } else {
                mViewModel.insert(new Stat(LocalDate.now(), data, Integer.parseInt(mFatInput)));
            }
        } else if (statType == StatType.BODYFAT){
            if (mShouldUpdate) {
                mViewModel.update(new Stat(LocalDate.now(), Integer.parseInt(mWeightInput), data));
            } else {
                mViewModel.insert(new Stat(LocalDate.now(), Integer.parseInt(mWeightInput), data));
            }
        }
    }

    // Other methods
    public void startDialogFragment(StatType statType, String tag) {
        AddStatDialogFragment addStatDialogFragment = AddStatDialogFragment.newInstance(Integer.parseInt(mWeightInput), Integer.parseInt(mFatInput), statType);
        addStatDialogFragment.setTargetFragment(this, 1);
        addStatDialogFragment.show(getFragmentManager(), tag);
    }
}