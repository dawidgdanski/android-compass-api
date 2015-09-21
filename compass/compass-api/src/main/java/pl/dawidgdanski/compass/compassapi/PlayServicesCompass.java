package pl.dawidgdanski.compass.compassapi;


import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.PlayServicesLocationSupplier;

public class PlayServicesCompass extends AbstractActivityBoundCompass {

    public PlayServicesCompass(AzimuthSupplier azimuthSupplier,
                               PlayServicesLocationSupplier locationSupplier) {
        super(azimuthSupplier, locationSupplier);
    }
}
