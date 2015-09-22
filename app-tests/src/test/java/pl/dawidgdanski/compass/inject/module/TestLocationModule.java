package pl.dawidgdanski.compass.inject.module;

import android.location.Criteria;
import android.location.LocationManager;

import pl.dawidgdanski.compass.compassapi.location.LocationSupplier;
import pl.dawidgdanski.compass.compassapi.location.NativeLocationSupplier;
import pl.dawidgdanski.compass.compassapi.location.PlayServicesLocationSupplier;

import static org.mockito.Mockito.spy;

public class TestLocationModule extends LocationModule {

    @Override
    public Criteria provideLocationCriteria() {
        return spy(super.provideLocationCriteria());
    }

    @Override
    public NativeLocationSupplier provideLocationSupplier(LocationManager locationManager, Criteria criteria) {
        return spy(super.provideLocationSupplier(locationManager, criteria));
    }

    @Override
    public PlayServicesLocationSupplier providePlayServicesLocationSupplier() {
        return spy(super.providePlayServicesLocationSupplier());
    }
}
