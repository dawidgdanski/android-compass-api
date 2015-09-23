package pl.dawidgdanski.compass.util;


import com.google.common.collect.Range;

public final class Ranges {

    private Ranges() { }

    public static final Range<Double> LONGITUDE_RANGE = Range.closed(-180.0, 180.0);

    public static final Range<Double> LATITUDE_RANGE = Range.closed(-90.0, 90.0);
}
