package pl.dawidgdanski.compass.inject.module;

import android.hardware.SensorManager;
import android.location.LocationManager;

import dagger.Module;
import dagger.Provides;
import pl.dawidgdanski.compass.compassapi.CompassImpl;

@Module
public class CompassModule {

    @Provides
    public CompassImpl provideCompass(SensorManager sensorManager, LocationManager locationManager) {
        return new CompassImpl(sensorManager, locationManager);
    }

}
