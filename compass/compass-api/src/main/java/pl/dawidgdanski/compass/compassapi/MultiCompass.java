package pl.dawidgdanski.compass.compassapi;

import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.NativeLocationSupplier;
import pl.dawidgdanski.compass.compassapi.location.PlayServicesLocationSupplier;

public class MultiCompass extends AbstractActivityBoundCompass {

    public MultiCompass(AzimuthSupplier azimuthSupplier,
                        NativeLocationSupplier nativeLocationSupplier,
                        PlayServicesLocationSupplier playServicesLocationSupplier) {
        super(azimuthSupplier, nativeLocationSupplier, playServicesLocationSupplier);
    }

}

