package com.gmail.plai2.ying.fitjournal.ui.stats;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.gmail.plai2.ying.fitjournal.room.Stat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;

public class CustomDateFormatter extends IndexAxisValueFormatter {

    // Fields
    private List<Stat> mStatList;

    // Constructor
    public CustomDateFormatter(List<Stat> statList) {
        mStatList = statList;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {

        int position = (int) value;

        // Format date based on string from position in the list
        return getDateStringFormat(position);
    }

    private String getDateStringFormat(int position) {
        if (position == 0 || position > mStatList.size()) {
            return "";
        } else {
            return mStatList.get(position-1).getMDate().format(DateTimeFormatter.ofPattern("M/d"));
        }
    }
}
