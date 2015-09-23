package pl.dawidgdanski.compass.inject.module;

import android.hardware.SensorManager;
import android.view.WindowManager;

import dagger.Module;
import dagger.Provides;
import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplierImpl;

@Module
public class GeomagneticModule {

    @Provides
    public AzimuthSupplier provideAzimuthSupplier(SensorManager sensorManager, WindowManager windowManager) {
        return new AzimuthSupplierImpl(sensorManager, windowManager);
    }

}
