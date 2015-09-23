package pl.dawidgdanski.compass.compassapi;

import android.os.Build;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.DefaultLocationSupplier;
import pl.dawidgdanski.compass.compassapi.test.RobolectricUnitTestCase;
import pl.dawidgdanski.compass.compassapi.test.rules.TestExecutionTimeRule;

import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN, manifest = Config.NONE)
public class DefaultCompassTest extends RobolectricUnitTestCase {

    @Rule
    public final TestRule TEST_EXECUTION_TIME_RULE = new TestExecutionTimeRule();

    @Mock
    AzimuthSupplier azimuthSupplier;

    @Mock
    DefaultLocationSupplier locationSupplier;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRaise_NPE_DuringConstruction_When_NullAzimuthSupplierPassed() {
        try {
            CompassApi.initialize(getApplication());
        } catch (NullPointerException ignored) {
            //https://github.com/robolectric/robolectric/issues/2028
        }

        new DefaultCompass(null, locationSupplier);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRaise_NPE_DuringConstruction_When_NullLocationSupplierPassed() {
        try {
            CompassApi.initialize(getApplication());
        } catch (NullPointerException ignored) {
            //https://github.com/robolectric/robolectric/issues/2028
        }

        new DefaultCompass(azimuthSupplier, null);
    }

    @Test
    public void shouldStartMembersOnStartMethodCall() {
        try {
            CompassApi.initialize(getApplication());
        } catch (NullPointerException ignored) {
            //https://github.com/robolectric/robolectric/issues/2028
        }

        final Compass SUT = new DefaultCompass(azimuthSupplier, locationSupplier);

        SUT.start();

        verify(azimuthSupplier).start(same(SUT));
        verify(locationSupplier).start(same(SUT));
    }

    @Test
    public void shouldStopMembersOnStopMethodCall() {
        try {
            CompassApi.initialize(getApplication());
        } catch (NullPointerException ignored) {
            //https://github.com/robolectric/robolectric/issues/2028
        }

        final Compass SUT = new DefaultCompass(azimuthSupplier, locationSupplier);

        SUT.stop();

        verify(azimuthSupplier).stop();
        verify(locationSupplier).stop();
    }
}
