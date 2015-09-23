package pl.dawidgdanski.compass.inject.module;

import android.content.Context;

import pl.dawidgdanski.compass.persistence.LocationPreferenceManager;
import pl.dawidgdanski.compass.persistence.PersistenceManager;

import static org.mockito.Mockito.spy;

public class TestPersistenceModule extends PersistenceModule {

    @Override
    public LocationPreferenceManager provideLocationPreferenceManager(Context context) {
        return spy(super.provideLocationPreferenceManager(context));
    }

    @Override
    public PersistenceManager providePersistenceManager(Context context) {
        return spy(super.providePersistenceManager(context));
    }
}
