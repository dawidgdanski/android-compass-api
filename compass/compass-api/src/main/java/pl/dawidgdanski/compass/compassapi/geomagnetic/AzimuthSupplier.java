package pl.dawidgdanski.compass.compassapi.geomagnetic;

import android.hardware.SensorEventListener;
import android.util.Log;

public interface AzimuthSupplier extends SensorEventListener {

    void start(OnAzimuthChangedListener onAzimuthChangedListener);

    void stop();

    void setBearing(float bearing);

    interface OnAzimuthChangedListener {

        OnAzimuthChangedListener NULL_LISTENER = new OnAzimuthChangedListener() {
            @Override
            public void onAzimuthChanged(float previousAzimuth, float currentAzimuth) {
                Log.d("NullOnAzimuthchanged", String.format("previous: %.2f, current: %.2f",
                        previousAzimuth,
                        currentAzimuth));
            }
        };

        void onAzimuthChanged(float previousAzimuth, float currentAzimuth);
    }
}
