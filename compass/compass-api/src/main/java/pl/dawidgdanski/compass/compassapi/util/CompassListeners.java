package pl.dawidgdanski.compass.compassapi.util;

import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.baseLocationSupplier;

public final class CompassListeners {

    private CompassListeners() { }

    public static AzimuthSupplier.OnAzimuthChangedListener returnSameOrNullListener(final AzimuthSupplier.OnAzimuthChangedListener listener) {
        return listener == null ? AzimuthSupplier.OnAzimuthChangedListener.NULL_LISTENER : listener;
    }

    public static baseLocationSupplier.OnLocationChangedListener returnSameOrNullListener(final baseLocationSupplier.OnLocationChangedListener locationChangedListener) {
        return locationChangedListener == null ? baseLocationSupplier.OnLocationChangedListener.NULL_LISTENER : locationChangedListener;
    }
}
