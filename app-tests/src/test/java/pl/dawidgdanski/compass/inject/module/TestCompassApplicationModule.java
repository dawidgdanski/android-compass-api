package pl.dawidgdanski.compass.inject.module;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;

import pl.dawidgdanski.compass.CompassApplication;

import static org.mockito.Mockito.spy;

public class TestCompassApplicationModule extends CompassApplicationModule {

    public TestCompassApplicationModule(CompassApplication application) {
        super(application);
    }

    @Override
    public CompassApplication provideApplication() {
        return spy(super.provideApplication());
    }

    @Override
    public Resources provideResources() {
        return spy(super.provideResources());
    }

    @Override
    public Context provideApplicationContext() {
        return spy(super.provideApplicationContext());
    }

    @Override
    public ContentResolver provideContentResolver() {
        return spy(super.provideContentResolver());
    }


}
