package pl.dawidgdanski.compass.compassapi;

import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import pl.dawidgdanski.compass.compassapi.geo.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.geo.AzimuthSupplierImpl;
import pl.dawidgdanski.compass.compassapi.location.LocationSupplier;
import pl.dawidgdanski.compass.compassapi.location.LocationSupplierImpl;
import pl.dawidgdanski.compass.compassapi.util.CompassPreconditions;

public class CompassImpl implements Compass {

    private final LocationSupplier locationSupplier;

    private final AzimuthSupplier azimuthSupplier;

    private View view;

    public CompassImpl(final SensorManager sensorManager, final LocationManager locationManager) {
        CompassPreconditions.checkNotNull(sensorManager, "SensorManager cannot be null");
        CompassPreconditions.checkNotNull(locationManager, "LocationManager cannot be null");

        this.azimuthSupplier = new AzimuthSupplierImpl(sensorManager);
        this.locationSupplier = new LocationSupplierImpl(locationManager);
    }

    public synchronized void start() {
        azimuthSupplier.start(this);
        locationSupplier.start(this);
    }

    public synchronized void stop() {
        azimuthSupplier.stop();
        locationSupplier.stop();
    }

    public synchronized void setView(final View view) {
        this.view = view;
    }

    public synchronized View getView() {
        return view;
    }

    @Override
    public void onAzimuthChanged(float previousAzimuth, float currentAzimuth) {

        final View view = this.view;

        if (view == null) {
            return;
        }

        Animation an = new RotateAnimation(-previousAzimuth, -currentAzimuth,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);

        an.setDuration(500);
        an.setRepeatCount(0);
        an.setFillAfter(true);

        view.startAnimation(an);
    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
