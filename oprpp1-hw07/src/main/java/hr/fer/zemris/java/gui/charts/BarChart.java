package hr.fer.zemris.java.gui.charts;

import java.util.List;

public record BarChart(List<XYValue> values, String xDescription,
                       String yDescription, int yMin, int yMax, int yResolution) {

    public BarChart {
        if (yMin < 0) throw new IllegalArgumentException("yMin value can't be negative.");

        if (yMax <= yMin) throw new IllegalArgumentException("yMax should be larger than yMin");

        for (XYValue value : values) {
            if (value.y() < yMin) throw new IllegalArgumentException("XYValue y smaller than yMin");
        }
    }
}
