package pl.dawidgdanski.compass.compassapi.location;

import android.location.Location;
import android.location.LocationListener;
import android.util.Log;

import pl.dawidgdanski.compass.compassapi.exception.LocationAbsentException;

public interface LocationSupplier {

    boolean hasLastKnownLocation();

    Location getLastKnownLocation() throws LocationAbsentException;

    void start(OnLocationChangedListener locationUpdateListener);

    void stop();

    interface OnLocationChangedListener {

        OnLocationChangedListener NULL_LISTENER = new OnLocationChangedListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("NullLocationListener", location.toString());
            }
        };

        void onLocationChanged(Location location);
    }
}
