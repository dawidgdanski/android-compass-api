package pl.dawidgdanski.compass.compassapi;

import android.app.Activity;
import android.os.Bundle;

import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.ActivityBoundLocationSupplier;
import pl.dawidgdanski.compass.compassapi.location.PlayServicesLocationSupplier;

public class PlayServicesCompass extends AbstractActivityBoundCompass {

    private final ActivityBoundLocationSupplier locationSupplier;

    public PlayServicesCompass(AzimuthSupplier azimuthSupplier,
                               PlayServicesLocationSupplier locationSupplier) {
        super(azimuthSupplier, locationSupplier);
        this.locationSupplier = locationSupplier;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        locationSupplier.onActivityCreated(activity, savedInstanceState);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        locationSupplier.onActivityStarted(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        locationSupplier.onActivityResumed(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        locationSupplier.onActivityPaused(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        super.onActivityStopped(activity);
        locationSupplier.onActivityStopped(activity);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        locationSupplier.onActivitySaveInstanceState(activity, outState);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        locationSupplier.onActivityDestroyed(activity);
    }
}
