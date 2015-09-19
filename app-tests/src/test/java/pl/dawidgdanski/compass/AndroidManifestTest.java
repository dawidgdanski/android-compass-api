package pl.dawidgdanski.compass;

import android.app.Application;

import org.fest.assertions.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

@RunWith(AppRobolectricTestRunner.class)
public class AndroidManifestTest {

    @Test
    public void shouldLaunchTestApp() {
        final Application application = RuntimeEnvironment.application;

        Assertions.assertThat(application).isInstanceOf(TestCompassApplication.class);
    }

}
