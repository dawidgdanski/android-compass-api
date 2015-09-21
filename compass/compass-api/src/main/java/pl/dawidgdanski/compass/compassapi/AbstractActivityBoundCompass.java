package pl.dawidgdanski.compass.compassapi;

import android.app.Activity;

import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.LocationSupplier;

abstract class AbstractActivityBoundCompass extends NativeCompass implements ActivityBoundCompass {

    AbstractActivityBoundCompass(AzimuthSupplier azimuthSupplier, LocationSupplier nativeLocationSupplier) {
        super(azimuthSupplier, nativeLocationSupplier);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        start();
    }

    @Override
    public void onActivityStopped(Activity activity) {
        stop();
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        destroy();
    }
}
