package pl.dawidgdanski.compass.inject.module;


import pl.dawidgdanski.compass.compassapi.ActivityBoundCompass;
import pl.dawidgdanski.compass.compassapi.Compass;
import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.DefaultLocationSupplier;
import pl.dawidgdanski.compass.compassapi.location.PlayServicesLocationSupplier;

import static org.mockito.Mockito.spy;

public class TestCompassModule extends CompassModule {

    @Override
    public Compass provideNativeCompass(AzimuthSupplier azimuthSupplier, DefaultLocationSupplier locationSupplier) {
        return spy(super.provideNativeCompass(azimuthSupplier, locationSupplier));
    }

    @Override
    public ActivityBoundCompass providePlayServicesLocationApiBasedCompass(AzimuthSupplier azimuthSupplier, PlayServicesLocationSupplier locationSupplier) {
        return spy(super.providePlayServicesLocationApiBasedCompass(azimuthSupplier, locationSupplier));
    }

    @Override
    public ActivityBoundCompass provideMultiCompass(AzimuthSupplier azimuthSupplier,
                                                    DefaultLocationSupplier nativeLocationSupplier,
                                                    PlayServicesLocationSupplier playServicesLocationSupplier) {
        return spy(super.provideMultiCompass(azimuthSupplier, nativeLocationSupplier, playServicesLocationSupplier));
    }
}
