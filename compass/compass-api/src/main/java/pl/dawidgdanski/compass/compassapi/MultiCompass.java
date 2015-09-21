package pl.dawidgdanski.compass.compassapi;

import android.app.Activity;
import android.os.Bundle;

import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.NativeLocationSupplier;
import pl.dawidgdanski.compass.compassapi.location.PlayServicesLocationSupplier;
import pl.dawidgdanski.compass.compassapi.util.CompassPreconditions;

public class MultiCompass extends AbstractActivityBoundCompass {

    private final PlayServicesLocationSupplier playServicesLocationSupplier;

    public MultiCompass(AzimuthSupplier azimuthSupplier,
                        NativeLocationSupplier nativeLocationSupplier,
                        PlayServicesLocationSupplier playServicesLocationSupplier) {
        super(azimuthSupplier, nativeLocationSupplier);
        CompassPreconditions.checkNotNull(playServicesLocationSupplier, "Location Supplier cannot be null");

        this.playServicesLocationSupplier = playServicesLocationSupplier;
    }

    @Override
    public synchronized void start() {
        super.start();
        playServicesLocationSupplier.start(this);
    }

    @Override
    public synchronized void stop() {
        super.stop();
        playServicesLocationSupplier.stop();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        playServicesLocationSupplier.onActivityCreated(activity, savedInstanceState);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        playServicesLocationSupplier.onActivityStarted(activity);
        super.onActivityStarted(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        playServicesLocationSupplier.onActivityResumed(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        playServicesLocationSupplier.onActivityPaused(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        playServicesLocationSupplier.onActivityStopped(activity);
        super.onActivityStopped(activity);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        playServicesLocationSupplier.onActivitySaveInstanceState(activity, outState);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        playServicesLocationSupplier.onActivityDestroyed(activity);
        super.onActivityDestroyed(activity);
    }
}

