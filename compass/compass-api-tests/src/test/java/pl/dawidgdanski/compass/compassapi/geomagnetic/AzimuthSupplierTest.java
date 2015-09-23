package pl.dawidgdanski.compass.compassapi.geomagnetic;

import android.hardware.SensorManager;
import android.os.Build;
import android.view.WindowManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import pl.dawidgdanski.compass.compassapi.test.RobolectricUnitTestCase;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN, manifest = Config.NONE)
public class AzimuthSupplierTest extends RobolectricUnitTestCase {

    @Mock
    SensorManager sensorManager;

    @Mock
    WindowManager windowManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullPointerException.class)
    public void shouldValidateIfNullSensorManagerAppliedDuringConstruction() {
        new AzimuthSupplierImpl(null, windowManager);
    }

    @Test(expected = NullPointerException.class)
    public void shouldValidateIfNullWindowManagerAppliedDuringConstruction() {
        new AzimuthSupplierImpl(sensorManager, null);
    }

}
