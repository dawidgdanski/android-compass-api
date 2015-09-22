package pl.dawidgdanski.compass.compassapi;

import android.location.Location;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import pl.dawidgdanski.compass.compassapi.exception.LocationAbsentException;
import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.LocationSupplier;
import pl.dawidgdanski.compass.compassapi.util.CompassListeners;
import pl.dawidgdanski.compass.compassapi.util.CompassPreconditions;

public class NativeCompass implements Compass {

    private final LocationSupplier nativeLocationSupplier;

    private final AzimuthSupplier azimuthSupplier;

    private View view;

    private Location destination;

    private Location myLocation;

    private LocationSupplier.OnLocationChangedListener onLocationChangedListener = LocationSupplier.OnLocationChangedListener.NULL_LISTENER;

    public NativeCompass(AzimuthSupplier azimuthSupplier,
                         LocationSupplier nativeLocationSupplier) {

        CompassPreconditions.checkState(CompassApi.isInitialized(), "Compass API is not initialized, please initialize it in your application.");

        CompassPreconditions.checkNotNull(azimuthSupplier, "Azimuth Supplier cannot be null");
        CompassPreconditions.checkNotNull(nativeLocationSupplier, "Location supplier cannot be null");

        this.azimuthSupplier = azimuthSupplier;
        this.nativeLocationSupplier = nativeLocationSupplier;

        try {
            this.myLocation = nativeLocationSupplier.getLastKnownLocation();
        } catch (LocationAbsentException ignored) {
        }
    }

    @Override
    public synchronized void start() {
        azimuthSupplier.start(this);
        nativeLocationSupplier.start(this);
    }

    @Override
    public synchronized void stop() {
        azimuthSupplier.stop();
        nativeLocationSupplier.stop();

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

    @Override
    public synchronized void setView(final View view) {
        this.view = view;
    }

    @Override
    public synchronized View getView() {
        return view;
    }

    @Override
    public synchronized void navigateTo(final double latitude, final double longitude) {
        destination = new Location((String) null);
        destination.setLatitude(latitude);
        destination.setLongitude(longitude);
    }

    @Override
    public void setOnLocationChangedListener(LocationSupplier.OnLocationChangedListener onLocationChangedListener) {
        this.onLocationChangedListener = CompassListeners.returnSameOrNullListener(onLocationChangedListener);
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
        myLocation = location;
        onLocationChangedListener.onLocationChanged(new Location(location));
        notifyBearingChanged();
    }

    private void notifyBearingChanged() {
        final Location myLocation = this.myLocation;
        final Location destination = this.destination;

        if (myLocation != null && destination != null) {
            azimuthSupplier.setBearing(myLocation.bearingTo(destination));
        }
    }
}
