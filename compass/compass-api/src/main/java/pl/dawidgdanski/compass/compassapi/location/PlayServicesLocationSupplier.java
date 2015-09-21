package pl.dawidgdanski.compass.compassapi.location;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.concurrent.TimeUnit;

import pl.dawidgdanski.compass.compassapi.exception.LocationAbsentException;
import pl.dawidgdanski.compass.compassapi.util.CompassListeners;
import pl.dawidgdanski.compass.compassapi.util.CompassPreconditions;

public class PlayServicesLocationSupplier implements ActivityBoundLocationSupplier,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    static final String SAVED_STATE_LAST_KNOWN_LOCATION = "location_supplier:last_known_location";

    private static final long UPDATE_INTERVAL_MILLIS = TimeUnit.SECONDS.toMillis(8);

    private static final long FASTEST_UPDATE_INTERVAL_MILLIS = UPDATE_INTERVAL_MILLIS / 2;

    private GoogleApiClient googleApiClient;

    private LocationRequest locationRequest;

    private OnLocationChangedListener onLocationChangedListener = OnLocationChangedListener.NULL_LISTENER;

    private Location lastKnownLocation;

    public PlayServicesLocationSupplier() {
        locationRequest = new LocationRequest()
                .setInterval(UPDATE_INTERVAL_MILLIS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MILLIS)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public boolean hasLastKnownLocation() {
        try {
            getLastKnownLocation();
            return true;
        } catch (LocationAbsentException e) {
            return false;
        }
    }

    @Override
    public Location getLastKnownLocation() throws LocationAbsentException {
        if(isConnected() && lastKnownLocation == null) {
            lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        }

        CompassPreconditions.checkNotNull(lastKnownLocation, new LocationAbsentException());

        return lastKnownLocation;
    }

    @Override
    public void start(OnLocationChangedListener locationUpdateListener) {
        this.onLocationChangedListener = CompassListeners.returnSameOrNullListener(locationUpdateListener);
        googleApiClient.connect();
    }

    @Override
    public void stop() {
        onLocationChangedListener = CompassListeners.returnSameOrNullListener((OnLocationChangedListener) null);
        if(isConnected()) {
            stopRequestingUpdates();
        }
        googleApiClient.disconnect();
    }

    @Override
    public void onLocationChanged(Location location) {
        onLocationChangedListener.onLocationChanged(location);
    }

    @Override
    public void onConnected(Bundle bundle) {
        if(lastKnownLocation == null) {
            lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        }

        requestUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public synchronized void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(SAVED_STATE_LAST_KNOWN_LOCATION);
        }

        if(activity != null && googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(activity)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

    }

    @Override
    public synchronized void onActivityStarted(Activity activity) {
        googleApiClient.connect();
    }

    @Override
    public synchronized void onActivityResumed(Activity activity) {
    }

    @Override
    public synchronized void onActivityPaused(Activity activity) {
    }

    @Override
    public synchronized void onActivityStopped(Activity activity) {
        stop();
    }

    @Override
    public synchronized void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        if(! isInitialized()) {
            return;
        }

        outState.putParcelable(SAVED_STATE_LAST_KNOWN_LOCATION, lastKnownLocation);
    }

    @Override
    public synchronized void onActivityDestroyed(Activity activity) {
        stop();
        googleApiClient = null;
    }

    protected boolean isInitialized() {
        return googleApiClient != null;
    }

    protected boolean isConnected() {
        return isInitialized() && googleApiClient.isConnected();
    }

    private void requestUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    private void stopRequestingUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }
}
