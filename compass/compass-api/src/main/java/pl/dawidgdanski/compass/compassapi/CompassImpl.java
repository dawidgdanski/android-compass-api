package pl.dawidgdanski.compass.compassapi;

import android.location.Location;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import pl.dawidgdanski.compass.compassapi.exception.LocationAbsentException;
import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.LocationSupplier;
import pl.dawidgdanski.compass.compassapi.util.CompassPreconditions;

public class CompassImpl implements Compass {

    private final LocationSupplier locationSupplier;

    private final AzimuthSupplier azimuthSupplier;

    private View view;

    private Location destination;

    private Location myLocation;

    public CompassImpl(AzimuthSupplier azimuthSupplier, LocationSupplier locationSupplier) {
        CompassPreconditions.checkNotNull(azimuthSupplier, "Azimuth Supplier cannot be null");
        CompassPreconditions.checkNotNull(locationSupplier, "Location supplier cannot be null");

        this.azimuthSupplier = azimuthSupplier;
        this.locationSupplier = locationSupplier;

        try {
            this.myLocation = locationSupplier.getLastKnownLocation();
        } catch (LocationAbsentException ignored) {
        }
    }

    @Override
    public synchronized void start() {
        azimuthSupplier.start(this);
        locationSupplier.start(this);
    }

    @Override
    public synchronized void stop() {
        azimuthSupplier.stop();
        locationSupplier.stop();

        final View view = this.view;

        if (view != null) {
            view.clearAnimation();
        }
    }

    @Override
    public synchronized void destroy() {
        stop();
        view = null;
    }

    public synchronized void setView(final View view) {
        this.view = view;
    }

    public synchronized View getView() {
        return view;
    }

    public synchronized void setDestination(final Location destination) {
        CompassPreconditions.checkNotNull(destination, "Location cannot be null");
        this.destination = new Location(destination);
    }

    public synchronized Location getDestination() {
        return destination != null ? new Location(destination) : null;
    }

    @Override
    public void onAzimuthChanged(float previousAzimuth, float currentAzimuth) {

        final View view = this.view;

        if (view == null) {
            return;
        }

        Animation an = new RotateAnimation(
                -previousAzimuth,
                -currentAzimuth,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);

        an.setDuration(500);
        an.setRepeatCount(0);
        an.setFillAfter(true);

        view.startAnimation(an);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.myLocation = location;

        notifyBearingChanged();
    }

    private void notifyBearingChanged() {
        final Location myLocation = this.myLocation;
        final Location destination = this.destination;

        if(myLocation != null && destination != null) {
            azimuthSupplier.setBearing(myLocation.bearingTo(destination));
        }
    }
}
