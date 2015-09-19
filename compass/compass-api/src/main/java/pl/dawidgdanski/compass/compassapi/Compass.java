package pl.dawidgdanski.compass.compassapi;

import pl.dawidgdanski.compass.compassapi.geo.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.LocationSupplier;

public interface Compass extends AzimuthSupplier.OnAzimuthChangedListener,
        LocationSupplier.OnLocationChangedListener {

    void start();

    void stop();

}
