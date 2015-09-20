package pl.dawidgdanski.compass.compassapi;

import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.LocationSupplier;

public interface Compass extends AzimuthSupplier.OnMagneticAzimuthChangedListener,
        LocationSupplier.OnLocationChangedListener {

    void start();

    void stop();

    void destroy();

}
