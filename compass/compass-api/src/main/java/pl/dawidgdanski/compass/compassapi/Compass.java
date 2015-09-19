package pl.dawidgdanski.compass.compassapi;

import android.hardware.SensorEventListener;

public interface Compass extends SensorEventListener {

    void start();

    void stop();

}
