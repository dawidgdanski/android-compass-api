package pl.dawidgdanski.compass.compassapi.test;

import android.app.Application;
import android.content.Context;
import android.location.LocationManager;

import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowLocationManager;

public abstract class RobolectricUnitTestCase {

    public Application getApplication() {
        return RuntimeEnvironment.application;
    }

    public Context getApplicationContext() {
        return getApplication();
    }

    public ShadowApplication getShadowApplication() {
        return ShadowApplication.getInstance();
    }

    public LocationManager getLocationManager() {
        return (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
    }
}
