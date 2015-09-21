package pl.dawidgdanski.compass.compassapi.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.text.TextUtils;

import java.util.Collection;
import java.util.Collections;

import pl.dawidgdanski.compass.compassapi.util.CompassPreconditions;

public class LocationProviderStateChangeReceiver extends BroadcastReceiver {

    public static final String ACTION = LocationManager.PROVIDERS_CHANGED_ACTION;

    private final LocationManager locationManager;

    private final OnLocationProviderStateChangeListener listener;

    public LocationProviderStateChangeReceiver(LocationManager locationManager, OnLocationProviderStateChangeListener listener) {
        CompassPreconditions.checkNotNull(listener, "Listener is null");
        CompassPreconditions.checkNotNull(locationManager, "Context is null");
        this.listener = listener;
        this.locationManager = locationManager;
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if (TextUtils.isEmpty(action) || !ACTION.equals(action)) {
            return;
        }

        final Collection<String> availableProviders = locationManager.getAllProviders();

        listener.onLocationProvidersStateChange(
                availableProviders == null ?
                        Collections.<String>emptyList() :
                        Collections.unmodifiableCollection(availableProviders)
        );
    }
}
