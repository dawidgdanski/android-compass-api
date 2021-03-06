package pl.dawidgdanski.compass.compassapi.util;

import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.LocationSupplier;

public final class CompassListeners {

    private CompassListeners() { }

    public static AzimuthSupplier.OnAzimuthChangedListener returnSameOrNullListener(final AzimuthSupplier.OnAzimuthChangedListener listener) {
        return listener == null ? AzimuthSupplier.OnAzimuthChangedListener.NULL_LISTENER : listener;
    }

    public static LocationSupplier.OnLocationChangedListener returnSameOrNullListener(final LocationSupplier.OnLocationChangedListener locationChangedListener) {
        return locationChangedListener == null ? LocationSupplier.OnLocationChangedListener.NULL_LISTENER : locationChangedListener;
    }
}
