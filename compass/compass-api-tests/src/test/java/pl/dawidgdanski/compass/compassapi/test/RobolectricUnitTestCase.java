package pl.dawidgdanski.compass.compassapi.test;

import android.app.Application;
import android.content.Context;

import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowApplication;

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

}
