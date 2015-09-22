package pl.dawidgdanski.compass.inject.module;


import pl.dawidgdanski.compass.compassapi.Compass;
import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.baseLocationSupplier;

import static org.mockito.Mockito.spy;

public class TestCompassModule extends CompassModule {

    @Override
    public Compass provideCompass(AzimuthSupplier azimuthSupplier, baseLocationSupplier baseLocationSupplier) {
        return spy(super.provideCompass(azimuthSupplier, baseLocationSupplier));
    }
}
