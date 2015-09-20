package pl.dawidgdanski.compass.compassapi;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.LocationSupplier;

import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN, manifest = Config.NONE)
public class CompassTest {

    @Mock
    AzimuthSupplier azimuthSupplier;

    @Mock
    LocationSupplier locationSupplier;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRaise_NPE_DuringConstruction_When_NullAzimuthSupplierPassed() {
        new CompassImpl(null, locationSupplier);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRaise_NPE_DuringConstruction_When_NullLocationSupplierPassed() {
        new CompassImpl(azimuthSupplier, null);
    }

    @Test
    public void shouldStartMembersOnStartMethodCall() {
        final Compass SUT = new CompassImpl(azimuthSupplier, locationSupplier);

        SUT.start();

        verify(azimuthSupplier).start(same(SUT));
        verify(locationSupplier).start(same(SUT));
    }

    public void shouldStopMembersOnStopMethodCall() {
        final Compass SUT = new CompassImpl(azimuthSupplier, locationSupplier);

        SUT.stop();

        verify(azimuthSupplier).stop();
        verify(locationSupplier).stop();
    }
}
