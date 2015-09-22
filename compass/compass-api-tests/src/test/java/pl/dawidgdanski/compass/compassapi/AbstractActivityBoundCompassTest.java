package pl.dawidgdanski.compass.compassapi;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.ActivityBoundLocationSupplier;
import pl.dawidgdanski.compass.compassapi.location.LocationSupplier;
import pl.dawidgdanski.compass.compassapi.test.RobolectricUnitTestCase;
import pl.dawidgdanski.compass.compassapi.test.rules.TestExecutionTimeRule;

import static org.mockito.Matchers.same;
import static org.mockito.Mockito.inOrder;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN, manifest = Config.NONE)
@PrepareForTest({Bundle.class})
public class AbstractActivityBoundCompassTest extends RobolectricUnitTestCase {

    @Rule
    public final TestRule TEST_EXECUTION_TIME_RULE = new TestExecutionTimeRule();

    @Mock
    private AzimuthSupplier azimuthSupplier;

    @Mock
    private LocationSupplier nativeLocationSupplier;

    @Mock
    private ActivityBoundLocationSupplier activityBoundLocationSupplier1;

    @Mock
    private ActivityBoundLocationSupplier activityBoundLocationSupplier2;

    @Mock
    private Activity activity;

    private Bundle instanceState;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        instanceState = PowerMockito.mock(Bundle.class);

        try {
            CompassApi.initialize(getApplication());
        } catch (Exception e) {
            //https://github.com/robolectric/robolectric/issues/2028
        }
    }

    @Test
    public void shouldCallLifecycleSpecificMethodsInCorrectOrderOnAzimuthSupplierTwoActivityBoundLocationSuppliers_1() {
        //Given
        AbstractActivityBoundCompass SUT = new TestAbstractActivityBoundCompass(
                azimuthSupplier,
                nativeLocationSupplier,
                activityBoundLocationSupplier1);

        //When
        SUT.onActivityCreated(activity, instanceState);

        SUT.onActivityStarted(activity);

        SUT.onActivityResumed(activity);

        SUT.onActivityPaused(activity);

        SUT.onActivitySaveInstanceState(activity, instanceState);

        SUT.onActivityStopped(activity);

        SUT.onActivityDestroyed(activity);

        //Then
        final InOrder executionOrder = inOrder(azimuthSupplier, nativeLocationSupplier, activityBoundLocationSupplier1);

        executionOrder.verify(activityBoundLocationSupplier1).onActivityCreated(same(activity), same(instanceState));

        executionOrder.verify(azimuthSupplier).start(same(SUT));
        executionOrder.verify(nativeLocationSupplier).start(same(SUT));
        executionOrder.verify(activityBoundLocationSupplier1).start(same(SUT));
        executionOrder.verify(activityBoundLocationSupplier1).onActivityStarted(same(activity));

        executionOrder.verify(activityBoundLocationSupplier1).onActivityResumed(same(activity));

        executionOrder.verify(activityBoundLocationSupplier1).onActivityPaused(same(activity));

        executionOrder.verify(azimuthSupplier).stop();
        executionOrder.verify(nativeLocationSupplier).stop();
        executionOrder.verify(activityBoundLocationSupplier1).stop();
        executionOrder.verify(activityBoundLocationSupplier1).onActivityStopped(same(activity));

        executionOrder.verify(activityBoundLocationSupplier1).onActivityDestroyed(same(activity));

        executionOrder.verifyNoMoreInteractions();
    }

    @Test
    public void shouldCallLifecycleSpecificMethodsInCorrectOrderOnAzimuthSupplierTwoActivityBoundLocationSuppliers_2() {
        //Given
        AbstractActivityBoundCompass SUT = new TestAbstractActivityBoundCompass(
                azimuthSupplier,
                activityBoundLocationSupplier1,
                activityBoundLocationSupplier2);

        //When
        SUT.onActivityCreated(activity, instanceState);

        SUT.onActivityStarted(activity);

        SUT.onActivityResumed(activity);

        SUT.onActivityPaused(activity);

        SUT.onActivitySaveInstanceState(activity, instanceState);

        SUT.onActivityStopped(activity);

        SUT.onActivityDestroyed(activity);

        //Then
        final InOrder executionOrder = inOrder(
                azimuthSupplier,
                activityBoundLocationSupplier1,
                activityBoundLocationSupplier2);

        executionOrder.verify(activityBoundLocationSupplier1).onActivityCreated(same(activity), same(instanceState));
        executionOrder.verify(activityBoundLocationSupplier2).onActivityCreated(same(activity), same(instanceState));

        executionOrder.verify(azimuthSupplier).start(same(SUT));
        executionOrder.verify(activityBoundLocationSupplier1).start(same(SUT));
        executionOrder.verify(activityBoundLocationSupplier2).start(same(SUT));
        executionOrder.verify(activityBoundLocationSupplier1).onActivityStarted(same(activity));
        executionOrder.verify(activityBoundLocationSupplier2).onActivityStarted(same(activity));

        executionOrder.verify(activityBoundLocationSupplier1).onActivityResumed(same(activity));
        executionOrder.verify(activityBoundLocationSupplier2).onActivityResumed(same(activity));

        executionOrder.verify(activityBoundLocationSupplier1).onActivityPaused(same(activity));
        executionOrder.verify(activityBoundLocationSupplier2).onActivityPaused(same(activity));

        executionOrder.verify(azimuthSupplier).stop();
        executionOrder.verify(activityBoundLocationSupplier1).stop();
        executionOrder.verify(activityBoundLocationSupplier2).stop();
        executionOrder.verify(activityBoundLocationSupplier1).onActivityStopped(same(activity));
        executionOrder.verify(activityBoundLocationSupplier2).onActivityStopped(same(activity));

        executionOrder.verify(activityBoundLocationSupplier1).onActivityDestroyed(same(activity));
        executionOrder.verify(activityBoundLocationSupplier2).onActivityDestroyed(same(activity));

        executionOrder.verifyNoMoreInteractions();
    }


}
