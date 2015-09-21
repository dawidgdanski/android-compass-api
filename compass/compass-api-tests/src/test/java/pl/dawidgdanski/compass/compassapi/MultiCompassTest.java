package pl.dawidgdanski.compass.compassapi;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.NativeLocationSupplier;
import pl.dawidgdanski.compass.compassapi.location.PlayServicesLocationSupplier;
import pl.dawidgdanski.compass.compassapi.test.RobolectricUnitTestCase;

import static org.mockito.Matchers.same;
import static org.mockito.Mockito.inOrder;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN, manifest = Config.NONE)
@PrepareForTest({Bundle.class})
public class MultiCompassTest extends RobolectricUnitTestCase {

    @Mock
    private AzimuthSupplier azimuthSupplier;

    @Mock
    private NativeLocationSupplier nativeLocationSupplier;

    @Mock
    private PlayServicesLocationSupplier playServicesLocationSupplier;

    @Mock
    private Activity activity;

    private Bundle savedInstanceState;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        savedInstanceState = PowerMockito.mock(Bundle.class);
    }

    @Test
    public void shouldCallLifecycleSpecificMethodsOnPlayServicesLocationSupplier() {

        try {
            CompassApi.initialize(getApplication());
        } catch (NullPointerException ignored) {
            //https://github.com/robolectric/robolectric/issues/2028
        }

        final Application.ActivityLifecycleCallbacks SUT = new MultiCompass(
                azimuthSupplier,
                nativeLocationSupplier,
                playServicesLocationSupplier);

        SUT.onActivityCreated(activity, savedInstanceState);

        SUT.onActivityStarted(activity);

        SUT.onActivityResumed(activity);

        SUT.onActivityPaused(activity);

        SUT.onActivitySaveInstanceState(activity, savedInstanceState);

        SUT.onActivityStopped(activity);

        SUT.onActivityDestroyed(activity);

        InOrder order = inOrder(playServicesLocationSupplier);
        order.verify(playServicesLocationSupplier).onActivityCreated(same(activity), same(savedInstanceState));
        order.verify(playServicesLocationSupplier).onActivityStarted(same(activity));
        order.verify(playServicesLocationSupplier).onActivityResumed(same(activity));
        order.verify(playServicesLocationSupplier).onActivityPaused(same(activity));
        order.verify(playServicesLocationSupplier).onActivitySaveInstanceState(same(activity), same(savedInstanceState));
        order.verify(playServicesLocationSupplier).onActivityStopped(same(activity));
        order.verify(playServicesLocationSupplier).onActivityDestroyed(same(activity));
    }
}
