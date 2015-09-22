package pl.dawidgdanski.compass.compassapi.location;

import android.app.Activity;
import android.location.Location;
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

import pl.dawidgdanski.compass.compassapi.test.RobolectricUnitTestCase;
import pl.dawidgdanski.compass.compassapi.test.rules.TestExecutionTimeRule;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN, manifest = Config.NONE)
@PrepareForTest({Bundle.class})
public class PlayServicesLocationSupplierTest extends RobolectricUnitTestCase {

    @Rule
    public final TestRule TEST_EXECUTION_TIME_RULE = new TestExecutionTimeRule();

    @Mock
    private Activity activity;

    private Bundle savedInstanceState;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        savedInstanceState = PowerMockito.mock(Bundle.class);
    }

    @Test
    public void shouldPersistLocationIn_onSaveInstanceState_andRestoreItIn_onActivityCreated() {
        PlayServicesLocationSupplier SUT = new PlayServicesLocationSupplier();

        SUT.onActivitySaveInstanceState(activity, savedInstanceState);

        SUT.onActivityCreated(null, savedInstanceState);

        InOrder executionOrder = inOrder(savedInstanceState);

        executionOrder.verify(savedInstanceState).putParcelable(eq(PlayServicesLocationSupplier.SAVED_STATE_LAST_KNOWN_LOCATION), any(Location.class));

        executionOrder.verify(savedInstanceState).getParcelable(eq(PlayServicesLocationSupplier.SAVED_STATE_LAST_KNOWN_LOCATION));
    }

}
