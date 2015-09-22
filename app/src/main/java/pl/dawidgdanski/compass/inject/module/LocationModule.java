package pl.dawidgdanski.compass.inject.module;

import android.location.Criteria;
import android.location.LocationManager;

import dagger.Module;
import dagger.Provides;
import pl.dawidgdanski.compass.compassapi.location.NativeLocationSupplier;
import pl.dawidgdanski.compass.compassapi.location.PlayServicesLocationSupplier;

@Module
public class LocationModule {

    @Provides
    public Criteria provideLocationCriteria() {
        return NativeLocationSupplier.getDefaultCriteria();
    }

    @Provides
    public NativeLocationSupplier provideLocationSupplier(LocationManager locationManager, Criteria criteria) {
        return new NativeLocationSupplier(locationManager, criteria);
    }

    @Provides
    public PlayServicesLocationSupplier providePlayServicesLocationSupplier() {
        return new PlayServicesLocationSupplier();
    }

}
