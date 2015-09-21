package pl.dawidgdanski.compass.compassapi.broadcast;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLocationManager;

import pl.dawidgdanski.compass.compassapi.test.RobolectricUnitTestCase;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.robolectric.Shadows.shadowOf;
import static pl.dawidgdanski.compass.compassapi.test.matchers.CompassMatchers.containsLocationProviders;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN, manifest = Config.NONE)
public class LocationProviderStateChangeReceiverTest extends RobolectricUnitTestCase {

    @Mock
    private LocationManager locationManagerMock;

    @Mock
    private OnLocationProviderStateChangeListener onLocationProviderStateChangeListenerMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRaise_NPE_WhenNullLocationManagerPassed() {
        new LocationProviderStateChangeReceiver(null, onLocationProviderStateChangeListenerMock);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRaise_NPE_WhenNullListenerPassed() {
        new LocationProviderStateChangeReceiver(locationManagerMock, null);
    }

    @Test
    public void shouldNotifyAvailableProviders() {
        ShadowLocationManager shadowLocationManager = shadowOf(getLocationManager());
        shadowLocationManager.setProviderEnabled(LocationManager.PASSIVE_PROVIDER, true);
        shadowLocationManager.setProviderEnabled(LocationManager.NETWORK_PROVIDER, true);

        LocationProviderStateChangeReceiver providerStateChangeReceiver = new LocationProviderStateChangeReceiver(
                getLocationManager(),
                onLocationProviderStateChangeListenerMock);

        Intent intent = new Intent(LocationProviderStateChangeReceiver.ACTION);

        providerStateChangeReceiver.onReceive(getApplicationContext(), intent);

        verify(onLocationProviderStateChangeListenerMock).onLocationProvidersStateChange(argThat(containsLocationProviders(
                LocationManager.PASSIVE_PROVIDER,
                LocationManager.NETWORK_PROVIDER)));
    }

    @Test
    public void shouldNotNotifyWhenDifferenctBroadcastReceived() {
        Intent intent = new Intent(BluetoothAdapter.ACTION_STATE_CHANGED);

        LocationProviderStateChangeReceiver receiver = new LocationProviderStateChangeReceiver(
                locationManagerMock,
                onLocationProviderStateChangeListenerMock);

        receiver.onReceive(getApplicationContext(), intent);

        verifyZeroInteractions(locationManagerMock);
        verifyZeroInteractions(onLocationProviderStateChangeListenerMock);
    }
}
