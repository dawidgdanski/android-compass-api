package pl.dawidgdanski.compass.compassapi;

import android.app.Activity;
import android.os.Bundle;

import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.ActivityBoundLocationSupplier;
import pl.dawidgdanski.compass.compassapi.location.LocationSupplier;
import pl.dawidgdanski.compass.compassapi.location.NativeLocationSupplier;

abstract class AbstractActivityBoundCompass extends NativeCompass implements ActivityBoundCompass {

    private static final ActivityBoundLocationSupplier[] EMPTY = new ActivityBoundLocationSupplier[0];

    private final ActivityBoundLocationSupplier[] additionalSuppliers;

    AbstractActivityBoundCompass(AzimuthSupplier azimuthSupplier,
                                 ActivityBoundLocationSupplier locationSupplier) {
        this(azimuthSupplier, locationSupplier, EMPTY);
    }

    AbstractActivityBoundCompass(AzimuthSupplier azimuthSupplier,
                                 LocationSupplier locationSupplier,
                                 ActivityBoundLocationSupplier... activityBoundSuppliers) {
        super(azimuthSupplier, locationSupplier);
        this.additionalSuppliers = activityBoundSuppliers == null ? EMPTY : activityBoundSuppliers;
    }

    @Override
    public synchronized void start() {
        super.start();
        for(LocationSupplier supplier : additionalSuppliers) {
            supplier.start(this);
        }
    }

    @Override
    public synchronized void stop() {
        super.stop();
        for(LocationSupplier supplier: additionalSuppliers) {
            supplier.stop();
        }
    }

    @Override
    public synchronized void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        for(ActivityBoundLocationSupplier supplier : additionalSuppliers) {
            supplier.onActivityCreated(activity, savedInstanceState);
        }
    }

    @Override
    public synchronized void onActivityStarted(Activity activity) {
        start();
        for(ActivityBoundLocationSupplier supplier : additionalSuppliers) {
            supplier.onActivityStarted(activity);
        }
    }

    @Override
    public synchronized void onActivityResumed(Activity activity) {
        for(ActivityBoundLocationSupplier supplier : additionalSuppliers) {
            supplier.onActivityResumed(activity);
        }
    }

    @Override
    public synchronized void onActivityPaused(Activity activity) {
        for(ActivityBoundLocationSupplier supplier : additionalSuppliers) {
            supplier.onActivityPaused(activity);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        for(ActivityBoundLocationSupplier supplier : additionalSuppliers) {
            supplier.onActivitySaveInstanceState(activity, outState);
        }
    }

    @Override
    public synchronized void onActivityStopped(Activity activity) {
        stop();
        for(ActivityBoundLocationSupplier supplier : additionalSuppliers) {
            supplier.stop();
            supplier.onActivityStopped(activity);
        }
    }

    @Override
    public synchronized void onActivityDestroyed(Activity activity) {
        destroy();
        for(ActivityBoundLocationSupplier supplier : additionalSuppliers) {
            supplier.onActivityDestroyed(activity);
        }
    }
}
