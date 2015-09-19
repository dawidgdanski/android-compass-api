package pl.dawidgdanski.compass.inject.module;

import android.content.Context;

import pl.dawidgdanski.compass.compassapi.CompassImpl;

import static org.mockito.Mockito.spy;

public class TestCompassModule extends CompassModule {

    @Override
    public CompassImpl provideCompass(Context applicationContext) {
        return spy(super.provideCompass(applicationContext));
    }
}
