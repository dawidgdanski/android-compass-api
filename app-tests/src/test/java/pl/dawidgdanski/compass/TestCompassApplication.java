package pl.dawidgdanski.compass;

import org.robolectric.RuntimeEnvironment;

import pl.dawidgdanski.compass.inject.module.CompassApplicationModule;
import pl.dawidgdanski.compass.inject.module.TestCompassApplicationModule;

public class TestCompassApplication extends CompassApplication {

    public static TestCompassApplication get() {
        return  (TestCompassApplication)RuntimeEnvironment.application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public CompassApplicationModule getCompassApplicationModule(CompassApplication compassApplication) {
        return new TestCompassApplicationModule(compassApplication);
    }

    @Override
    protected void installLeakCanary() {

    }
}
