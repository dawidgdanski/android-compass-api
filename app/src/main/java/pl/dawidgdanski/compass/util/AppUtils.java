package pl.dawidgdanski.compass.util;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Looper;
import android.os.StrictMode;
import android.view.Surface;

import pl.dawidgdanski.compass.BuildConfig;

public final class AppUtils {

    private AppUtils() { }

    public static boolean isThisThreadAMainOne() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public static void runOnUiThread(final Activity activity, final Runnable runnable) {
        if(activity == null || runnable == null) {
            return;
        }

        activity.runOnUiThread(runnable);
    }

    public static void setOrientationChangeEnabled(final boolean state, final Activity activity) {

        if (!state) {
            int orientation = 0;
            int tempOrientation = activity.getResources().getConfiguration().orientation;
            final int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
            switch (tempOrientation) {
                case Configuration.ORIENTATION_LANDSCAPE:
                    if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_90) {
                        orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    } else {
                        orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    }
                    break;
                case Configuration.ORIENTATION_PORTRAIT:
                    if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_270) {
                        orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    } else {
                        orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    }
                    break;
            }
            activity.setRequestedOrientation(orientation);
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }

    public static void initThreadPolicyStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .detectCustomSlowCalls()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build());
        }
    }

    public static void initVmPolicyStrictMode(final Class clazz) {
        if (BuildConfig.DEBUG) {
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .detectActivityLeaks()
                    .detectLeakedClosableObjects()
                    .detectLeakedSqlLiteObjects()
                    .detectFileUriExposure()
                    .setClassInstanceLimit(clazz, 1)
                    .penaltyLog()
                    .build());
        }
    }

    public static void removeDiskReadsThreadPolicyThreadMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .permitDiskReads()
                    .build());
        }
    }
}
