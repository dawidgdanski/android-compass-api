package pl.dawidgdanski.compass.inject.module;

import android.location.Criteria;
import android.location.LocationManager;

import pl.dawidgdanski.compass.compassapi.location.LocationSupplier;

import static org.mockito.Mockito.spy;

public class TestLocationModule extends LocationModule {

    @Override
    public Criteria provideLocationCriteria() {
        return spy(super.provideLocationCriteria());
    }

    @Override
    public LocationSupplier provideLocationSupplier(LocationManager locationManager, Criteria criteria) {
        return spy(super.provideLocationSupplier(locationManager, criteria));
    }
}
