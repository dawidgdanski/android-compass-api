package pl.dawidgdanski.compass.compassapi.location;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.Collections;
import java.util.List;

import pl.dawidgdanski.compass.compassapi.exception.LocationAbsentException;
import pl.dawidgdanski.compass.compassapi.util.CompassPreconditions;

public class LocationSupplierImpl implements LocationSupplier {

    private static final Criteria DEFAULT_CRITERIA;

    static {
        DEFAULT_CRITERIA = new Criteria();

        DEFAULT_CRITERIA.setAccuracy(Criteria.ACCURACY_COARSE);
        DEFAULT_CRITERIA.setAltitudeRequired(true);
        DEFAULT_CRITERIA.setBearingAccuracy(Criteria.NO_REQUIREMENT);
        DEFAULT_CRITERIA.setBearingRequired(true);
        DEFAULT_CRITERIA.setCostAllowed(true);

        DEFAULT_CRITERIA.setHorizontalAccuracy(Criteria.ACCURACY_LOW);
        DEFAULT_CRITERIA.setPowerRequirement(Criteria.POWER_LOW);
        DEFAULT_CRITERIA.setSpeedAccuracy(Criteria.ACCURACY_MEDIUM);
        DEFAULT_CRITERIA.setSpeedRequired(false);
        DEFAULT_CRITERIA.setVerticalAccuracy(Criteria.ACCURACY_LOW);
    }

    private final LocationManager locationManager;

    private final Criteria criteria;

    private OnLocationChangedListener onLocationChangedListener = OnLocationChangedListener.NULL_LISTENER;

    public LocationSupplierImpl(final LocationManager locationManager) {
        this(locationManager, new Criteria(DEFAULT_CRITERIA));
    }

    public LocationSupplierImpl(final LocationManager locationManager, final Criteria criteria) {
        CompassPreconditions.checkNotNull(locationManager, "Context cannot be null");
        CompassPreconditions.checkNotNull(criteria, "Location criteria cannot be null");
        this.locationManager = locationManager;
        this.criteria = criteria;
    }

    @Override
    public void onLocationChanged(Location location) {
        onLocationChangedListener.onLocationChanged(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public synchronized boolean hasLastKnownLocation() {
        try {
            getLastKnownLocation();
            return true;
        } catch (LocationAbsentException e) {
            return false;
        }
    }

    @Override
    public synchronized Location getLastKnownLocation() throws LocationAbsentException {

        Location location = null;

        for(String provider : getAllProviders()) {
            if(! LocationManager.PASSIVE_PROVIDER.equals(provider)) {
                final Location foundLocation = locationManager.getLastKnownLocation(provider);

                if((foundLocation != null) && (location == null || foundLocation.getTime() > location.getTime())) {
                    location = foundLocation;
                }
            }
        }

        CompassPreconditions.checkNotNull(location, new LocationAbsentException());

        return location;
    }

    @Override
    public synchronized void start(OnLocationChangedListener onLocationChangedListener) {
        this.onLocationChangedListener = onLocationChangedListener;
    }

    @Override
    public synchronized void stop() {
        onLocationChangedListener = OnLocationChangedListener.NULL_LISTENER;
    }

    private List<String> getAllProviders() {
        final List<String> allProviders = locationManager.getAllProviders();

        return allProviders == null ? Collections.<String>emptyList() : allProviders;
    }
}
