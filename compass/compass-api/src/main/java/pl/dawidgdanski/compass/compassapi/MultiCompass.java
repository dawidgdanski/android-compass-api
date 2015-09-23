package pl.dawidgdanski.compass.compassapi;

import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.DefaultLocationSupplier;
import pl.dawidgdanski.compass.compassapi.location.PlayServicesLocationSupplier;

public class MultiCompass extends AbstractActivityBoundCompass {

    public MultiCompass(AzimuthSupplier azimuthSupplier,
                        DefaultLocationSupplier nativeLocationSupplier,
                        PlayServicesLocationSupplier playServicesLocationSupplier) {
        super(azimuthSupplier,
                nativeLocationSupplier,
                playServicesLocationSupplier);
    }

}

