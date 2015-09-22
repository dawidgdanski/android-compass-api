package pl.dawidgdanski.compass.compassapi;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import pl.dawidgdanski.compass.compassapi.test.RobolectricUnitTestCase;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN, manifest = Config.NONE)
public class CompassApiTest extends RobolectricUnitTestCase {

    @Before
    public void setUp() {
        CompassApi.reset();
    }

    @Test
    public void compassApiInitializaionTest() {
        assertThat(CompassApi.isInitialized()).isFalse();

        try {
            CompassApi.initialize(getApplication());
        } catch (Exception e) {
            //https://github.com/robolectric/robolectric/issues/2028
        }

        assertThat(CompassApi.isInitialized()).isTrue();
    }

}
