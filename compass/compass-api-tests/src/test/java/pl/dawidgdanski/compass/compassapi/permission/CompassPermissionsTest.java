package pl.dawidgdanski.compass.compassapi.permission;

import android.Manifest;
import android.content.Context;
import android.os.Build;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import pl.dawidgdanski.compass.compassapi.test.RobolectricUnitTestCase;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN, manifest = Config.NONE)
public class CompassPermissionsTest extends RobolectricUnitTestCase {

    @Ignore //https://github.com/robolectric/robolectric/issues/2028
    @Test(expected = IllegalStateException.class)
    public void shouldRaise_IAE_WhenNoPermissionsIncludedToManifest() {
        CompassPermissions.check(getApplicationContext());
    }

    @Ignore //https://github.com/robolectric/robolectric/issues/2028
    @Test
    public void shouldNotRaiseWhenAccessCoarseLocationPermissionSet() {
        getShadowApplication().grantPermissions(Manifest.permission.ACCESS_COARSE_LOCATION);

        CompassPermissions.check(getApplicationContext());
    }

    @Ignore //https://github.com/robolectric/robolectric/issues/2028
    @Test
    public void shouldNotRaiseWhenAccessFineLocationPermissionSet() {
        getShadowApplication().grantPermissions(Manifest.permission.ACCESS_FINE_LOCATION);

        CompassPermissions.check(getApplicationContext());
    }
}
