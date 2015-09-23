package pl.dawidgdanski.compass.inject.module;

import android.location.Criteria;
import android.location.LocationManager;

import dagger.Module;
import dagger.Provides;
import pl.dawidgdanski.compass.compassapi.location.DefaultLocationSupplier;
import pl.dawidgdanski.compass.compassapi.location.PlayServicesLocationSupplier;

@Module
public class LocationModule {

    @Provides
    public Criteria provideLocationCriteria() {
        return DefaultLocationSupplier.getDefaultCriteria();
    }

    @Provides
    public DefaultLocationSupplier provideDefaultLocationSupplier(LocationManager locationManager, Criteria criteria) {
        return new DefaultLocationSupplier(locationManager, criteria);
    }

    @Provides
    public PlayServicesLocationSupplier providePlayServicesLocationSupplier() {
        return new PlayServicesLocationSupplier();
    }

}
