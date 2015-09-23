package pl.dawidgdanski.compass.inject.module;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.dawidgdanski.compass.CompassApplication;

@Module
public class CompassApplicationModule {

    private final CompassApplication application;

    public CompassApplicationModule(CompassApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public CompassApplication provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    public Resources provideResources() {
        return application.getResources();
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public ContentResolver provideContentResolver() {
        return application.getContentResolver();
    }

    @Provides
    @Singleton
    public SensorManager provideSensorManager() {
        return (SensorManager) application.getSystemService(Context.SENSOR_SERVICE);
    }

    @Provides
    @Singleton
    public LocationManager provideLocationManager() {
        return (LocationManager) application.getSystemService(Context.LOCATION_SERVICE);
    }

    @Provides
    @Singleton
    LayoutInflater provideLayoutInflater() {
        return (LayoutInflater) application.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Provides
    @Singleton
    LocalBroadcastManager provideLocalBroadcastManager() {
        return LocalBroadcastManager.getInstance(application);
    }

}
