package com.gmail.plai2.ying.fitjournal.ui.stats;

import com.github.mikephil.charting.formatter.ValueFormatter;

public class FloatToIntegerFormatter extends ValueFormatter {

    // Constructor
    public FloatToIntegerFormatter() {
    }

    @Override
    public String getFormattedValue(float value) {
        return "" + ((int) value);
    }
}
