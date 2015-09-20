package pl.dawidgdanski.compass.inject.module;

import android.hardware.SensorManager;

import dagger.Module;
import dagger.Provides;
import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplierImpl;

import static org.mockito.Mockito.spy;

public class TestGeomagneticModule extends GeomagneticModule {

    @Override
    public AzimuthSupplier provideAzimuthSupplier(SensorManager sensorManager) {
        return spy(super.provideAzimuthSupplier(sensorManager));
    }
}
