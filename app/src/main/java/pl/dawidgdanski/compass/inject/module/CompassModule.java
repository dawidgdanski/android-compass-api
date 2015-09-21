package pl.dawidgdanski.compass.inject.module;

import dagger.Module;
import dagger.Provides;
import pl.dawidgdanski.compass.compassapi.Compass;
import pl.dawidgdanski.compass.compassapi.NativeCompass;
import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.LocationSupplier;

@Module
public class CompassModule {

    @Provides
    public Compass provideCompass(AzimuthSupplier azimuthSupplier, LocationSupplier locationSupplier) {
        return new NativeCompass(azimuthSupplier, locationSupplier);
    }

}
