package pl.dawidgdanski.compass.inject.module;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import pl.dawidgdanski.compass.compassapi.ActivityBoundCompass;
import pl.dawidgdanski.compass.compassapi.Compass;
import pl.dawidgdanski.compass.compassapi.MultiCompass;
import pl.dawidgdanski.compass.compassapi.NativeCompass;
import pl.dawidgdanski.compass.compassapi.PlayServicesCompass;
import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.LocationSupplier;
import pl.dawidgdanski.compass.compassapi.location.NativeLocationSupplier;
import pl.dawidgdanski.compass.compassapi.location.PlayServicesLocationSupplier;
import pl.dawidgdanski.compass.inject.Qualifiers;

@Module
public class CompassModule {

    @Provides
    @Named(Qualifiers.NATIVE_COMPASS)
    public Compass provideCompass(AzimuthSupplier azimuthSupplier,
                                  NativeLocationSupplier locationSupplier) {
        return new NativeCompass(azimuthSupplier, locationSupplier);
    }

    @Provides
    @Named(Qualifiers.PLAY_SERVICES_COMPASS)
    public ActivityBoundCompass providePlayServicesLocationApiBasedCompass(AzimuthSupplier azimuthSupplier,
                                                                           PlayServicesLocationSupplier locationSupplier) {
        return new PlayServicesCompass(azimuthSupplier, locationSupplier);
    }

    @Provides
    @Named(Qualifiers.MULTI_COMPASS)
    public ActivityBoundCompass provideMultiCompass(AzimuthSupplier azimuthSupplier,
                                                    NativeLocationSupplier nativeLocationSupplier,
                                                    PlayServicesLocationSupplier playServicesLocationSupplier) {
        return new MultiCompass(azimuthSupplier,
                nativeLocationSupplier,
                playServicesLocationSupplier);
    }

}
