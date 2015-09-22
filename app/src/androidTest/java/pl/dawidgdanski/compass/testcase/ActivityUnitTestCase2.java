package pl.dawidgdanski.compass.testcase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.test.ActivityUnitTestCase;

import java.util.concurrent.atomic.AtomicReference;

public abstract class ActivityUnitTestCase2<T extends Activity> extends ActivityUnitTestCase<T> {
    
    public ActivityUnitTestCase2(Class<T> activityClass, final String name) {
        super(activityClass);
        setName(name);
    }

    @Override
    protected T startActivity(Intent intent, Bundle savedInstanceState, Object lastNonConfigurationInstance) {
        return startActivityOnMainThread(intent, savedInstanceState, lastNonConfigurationInstance);
    }

    private T startActivityOnMainThread(final Intent intent, final Bundle savedInstanceState,
                                        final Object lastNonConfigurationInstance) {
        final AtomicReference<T> activityRef = new AtomicReference<>();
        final Runnable activityRunnable = new Runnable() {
            @Override
            public void run() {
                activityRef.set(ActivityUnitTestCase2.super.startActivity(
                        intent, savedInstanceState, lastNonConfigurationInstance));
            }
        };

        if (Looper.myLooper() != Looper.getMainLooper()) {
            getInstrumentation().runOnMainSync(activityRunnable);
        } else {
            activityRunnable.run();
        }

        return activityRef.get();
    }
}
