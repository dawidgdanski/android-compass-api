package pl.dawidgdanski.compass.compassapi;

import android.app.Application;

import pl.dawidgdanski.compass.compassapi.permission.CompassPermissions;

public final class CompassApi {

    private static boolean IS_INITIALIZED;

    private CompassApi() {
    }

    public static synchronized void initialize(final Application application) {
        IS_INITIALIZED = true;
        CompassPermissions.check(application);
    }

    public static synchronized boolean isInitialized() {
        return IS_INITIALIZED;
    }

    public static synchronized void reset() {
        IS_INITIALIZED = false;
    }

}
