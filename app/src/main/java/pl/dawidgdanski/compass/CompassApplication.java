package pl.dawidgdanski.compass;

import android.app.Application;
import android.content.Context;

import pl.dawidgdanski.compass.inject.DependencyInjector;
import pl.dawidgdanski.compass.inject.ModuleProvisionContract;
import pl.dawidgdanski.compass.inject.module.CompassApplicationModule;
import com.squareup.leakcanary.LeakCanary;

public class CompassApplication extends Application implements ModuleProvisionContract {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        DependencyInjector.initialize(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        installLeakCanary();
    }

    @Override
    public CompassApplicationModule getCompassApplicationModule(CompassApplication compassApplication) {
        return new CompassApplicationModule(compassApplication);
    }

    protected void installLeakCanary() {
        if(BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }
}
