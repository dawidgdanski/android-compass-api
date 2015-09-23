package pl.dawidgdanski.compass;

import android.app.Application;
import android.content.Context;

import pl.dawidgdanski.compass.compassapi.CompassApi;
import pl.dawidgdanski.compass.inject.DependencyInjector;
import pl.dawidgdanski.compass.inject.ModuleProvisionContract;
import pl.dawidgdanski.compass.inject.module.CompassApplicationModule;
import pl.dawidgdanski.compass.inject.module.CompassModule;
import pl.dawidgdanski.compass.inject.module.DatabaseModule;
import pl.dawidgdanski.compass.inject.module.GeomagneticModule;
import pl.dawidgdanski.compass.inject.module.LocationModule;
import pl.dawidgdanski.compass.inject.module.PersistenceModule;

import com.squareup.leakcanary.LeakCanary;

public class CompassApplication extends Application implements ModuleProvisionContract {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        CompassApi.initialize(this);
        DependencyInjector.initialize(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        installLeakCanary();
    }

    protected void installLeakCanary() {
        if(BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }

    @Override
    public CompassApplicationModule getCompassApplicationModule(CompassApplication compassApplication) {
        return new CompassApplicationModule(compassApplication);
    }

    @Override
    public CompassModule getCompassModule(CompassApplication compassApplication) {
        return new CompassModule();
    }

    @Override
    public DatabaseModule getDatabaseModule(CompassApplication compassApplication) {
        return new DatabaseModule();
    }

    @Override
    public GeomagneticModule getGeomagneticModule(CompassApplication compassApplication) {
        return new GeomagneticModule();
    }

    @Override
    public LocationModule getLocationModule(CompassApplication compassApplication) {
        return new LocationModule();
    }

    @Override
    public PersistenceModule getPersistenceModule(CompassApplication compassApplication) {
        return new PersistenceModule();
    }
}
