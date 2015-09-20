package pl.dawidgdanski.compass.inject.module;

import android.location.Criteria;
import android.location.LocationManager;

import dagger.Module;
import dagger.Provides;
import pl.dawidgdanski.compass.compassapi.location.LocationSupplier;
import pl.dawidgdanski.compass.compassapi.location.LocationSupplierImpl;

@Module
public class LocationModule {

    @Provides
    public Criteria provideLocationCriteria() {
        return LocationSupplierImpl.getDefaultCriteria();
    }

    @Provides
    public LocationSupplier provideLocationSupplier(LocationManager locationManager, Criteria criteria) {
        return new LocationSupplierImpl(locationManager, criteria);
    }

}
