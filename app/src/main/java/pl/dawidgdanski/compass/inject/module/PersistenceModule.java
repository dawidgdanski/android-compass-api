package pl.dawidgdanski.compass.inject.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.dawidgdanski.compass.persistence.LocationPreferenceManager;
import pl.dawidgdanski.compass.persistence.PersistenceManager;

@Module
public class PersistenceModule {

    @Provides
    @Singleton
    public LocationPreferenceManager provideLocationPreferenceManager(Context context) {
        return new LocationPreferenceManager(context);
    }

    @Provides
    @Singleton
    public PersistenceManager providePersistenceManager(Context context) {
        return new PersistenceManager(context);
    }

}
