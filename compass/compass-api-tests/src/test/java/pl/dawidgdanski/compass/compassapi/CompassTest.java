package pl.dawidgdanski.compass.compassapi;

import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN, manifest = Config.NONE)
public class CompassTest {

    @Mock
    LocationManager locationManager;

    @Mock
    SensorManager sensorManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRaise_NPE_DuringConstruction_When_NullSensorManagerPassed() {
        new CompassImpl(null, locationManager);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRaise_NPE_DuringConstruction_When_NullLocationManagerPassed() {
        new CompassImpl(sensorManager, null);
    }


}
