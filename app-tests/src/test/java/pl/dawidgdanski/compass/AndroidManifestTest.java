package pl.dawidgdanski.compass;

import android.app.Application;

import org.fest.assertions.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import pl.dawidgdanski.compass.test.rules.TestExecutionTimeRule;

@RunWith(CompassApplicationRobolectricTestRunner.class)
public class AndroidManifestTest {

    @Rule
    public final TestRule TEST_EXECUTION_TIME_RULE = new TestExecutionTimeRule();

    @Test
    public void shouldLaunchTestApp() {
        final Application application = RuntimeEnvironment.application;

        Assertions.assertThat(application).isInstanceOf(TestCompassApplication.class);
    }

}
