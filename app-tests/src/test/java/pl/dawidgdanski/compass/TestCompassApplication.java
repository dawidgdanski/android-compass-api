package pl.dawidgdanski.compass;

import org.robolectric.RuntimeEnvironment;

import pl.dawidgdanski.compass.inject.module.CompassApplicationModule;
import pl.dawidgdanski.compass.inject.module.CompassModule;
import pl.dawidgdanski.compass.inject.module.DatabaseModule;
import pl.dawidgdanski.compass.inject.module.GeomagneticModule;
import pl.dawidgdanski.compass.inject.module.LocationModule;
import pl.dawidgdanski.compass.inject.module.TestCompassApplicationModule;
import pl.dawidgdanski.compass.inject.module.TestCompassModule;
import pl.dawidgdanski.compass.inject.module.TestDatabaseModule;
import pl.dawidgdanski.compass.inject.module.TestGeomagneticModule;
import pl.dawidgdanski.compass.inject.module.TestLocationModule;

public class TestCompassApplication extends CompassApplication {

    public static TestCompassApplication get() {
        return  (TestCompassApplication)RuntimeEnvironment.application;
    }

    @Override
    protected void installLeakCanary() {

    }

    @Override
    public CompassApplicationModule getCompassApplicationModule(CompassApplication compassApplication) {
        return new TestCompassApplicationModule(compassApplication);
    }

    @Override
    public CompassModule getCompassModule(CompassApplication compassApplication) {
        return new TestCompassModule();
    }

    @Override
    public DatabaseModule getDatabaseModule(CompassApplication compassApplication) {
        return new TestDatabaseModule();
    }

    @Override
    public GeomagneticModule getGeomagneticModule(CompassApplication compassApplication) {
        return new TestGeomagneticModule();
    }

    @Override
    public LocationModule getLocationModule(CompassApplication compassApplication) {
        return new TestLocationModule();
    }
}
