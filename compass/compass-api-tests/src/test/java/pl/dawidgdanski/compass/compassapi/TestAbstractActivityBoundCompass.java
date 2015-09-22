package pl.dawidgdanski.compass.compassapi;

import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.ActivityBoundLocationSupplier;
import pl.dawidgdanski.compass.compassapi.location.LocationSupplier;

class TestAbstractActivityBoundCompass extends AbstractActivityBoundCompass {

    TestAbstractActivityBoundCompass(AzimuthSupplier azimuthSupplier,
                                     LocationSupplier LocationSupplier,
                                     ActivityBoundLocationSupplier... activityBoundAdditionalSuppliers) {
        super(azimuthSupplier,
                LocationSupplier,
                activityBoundAdditionalSuppliers);
    }
}
