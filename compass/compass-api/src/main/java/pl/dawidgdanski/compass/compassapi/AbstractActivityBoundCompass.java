package pl.dawidgdanski.compass.compassapi;

import android.app.Activity;
import android.os.Bundle;

import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.ActivityBoundLocationSupplier;
import pl.dawidgdanski.compass.compassapi.location.LocationSupplier;
import pl.dawidgdanski.compass.compassapi.util.CompassOptional;

abstract class AbstractActivityBoundCompass extends NativeCompass implements ActivityBoundCompass {

    private static final ActivityBoundLocationSupplier[] EMPTY = new ActivityBoundLocationSupplier[0];

    private final ActivityBoundLocationSupplier[] additionalSuppliers;

    private final CompassOptional<ActivityBoundLocationSupplier> optionalActivityBoundLocationSupplier;

    AbstractActivityBoundCompass(AzimuthSupplier azimuthSupplier,
                                 LocationSupplier LocationSupplier,
                                 ActivityBoundLocationSupplier... activityBoundAdditionalSuppliers) {
        super(azimuthSupplier, LocationSupplier);
        this.additionalSuppliers = activityBoundAdditionalSuppliers == null ? EMPTY : activityBoundAdditionalSuppliers;
        this.optionalActivityBoundLocationSupplier =
                (LocationSupplier instanceof ActivityBoundLocationSupplier) ?
                        CompassOptional.of((ActivityBoundLocationSupplier) LocationSupplier) :
                        CompassOptional.<ActivityBoundLocationSupplier>absent();
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
        if(optionalActivityBoundLocationSupplier.isPresent()) {
            optionalActivityBoundLocationSupplier.get().onActivityCreated(activity, savedInstanceState);
        }

        for(ActivityBoundLocationSupplier supplier : additionalSuppliers) {
            supplier.onActivityCreated(activity, savedInstanceState);
        }
    }

    @Override
    public synchronized void onActivityStarted(Activity activity) {
        start();

        if(optionalActivityBoundLocationSupplier.isPresent()) {
            optionalActivityBoundLocationSupplier.get().onActivityStarted(activity);
        }

        for(ActivityBoundLocationSupplier supplier : additionalSuppliers) {
            supplier.onActivityStarted(activity);
        }
    }

    @Override
    public synchronized void onActivityResumed(Activity activity) {
        if(optionalActivityBoundLocationSupplier.isPresent()) {
            optionalActivityBoundLocationSupplier.get().onActivityResumed(activity);
        }

        for(ActivityBoundLocationSupplier supplier : additionalSuppliers) {
            supplier.onActivityResumed(activity);
        }
    }

    @Override
    public synchronized void onActivityPaused(Activity activity) {
        if(optionalActivityBoundLocationSupplier.isPresent()) {
            optionalActivityBoundLocationSupplier.get().onActivityPaused(activity);
        }

        for(ActivityBoundLocationSupplier supplier : additionalSuppliers) {
            supplier.onActivityPaused(activity);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        if(optionalActivityBoundLocationSupplier.isPresent()) {
            optionalActivityBoundLocationSupplier.get().onActivitySaveInstanceState(activity, outState);
        }

        for(ActivityBoundLocationSupplier supplier : additionalSuppliers) {
            supplier.onActivitySaveInstanceState(activity, outState);
        }
    }

    @Override
    public synchronized void onActivityStopped(Activity activity) {
        stop();

        if(optionalActivityBoundLocationSupplier.isPresent()) {
            optionalActivityBoundLocationSupplier.get().onActivityStopped(activity);
        }

        for(ActivityBoundLocationSupplier supplier : additionalSuppliers) {
            supplier.onActivityStopped(activity);
        }
    }

    @Override
    public synchronized void onActivityDestroyed(Activity activity) {
        destroy();

        if(optionalActivityBoundLocationSupplier.isPresent()) {
            optionalActivityBoundLocationSupplier.get().onActivityDestroyed(activity);
        }

        for(ActivityBoundLocationSupplier supplier : additionalSuppliers) {
            supplier.onActivityDestroyed(activity);
        }
    }
}
