package pl.dawidgdanski.compass.database.model;

import android.os.Build;
import android.os.Parcel;

import org.fest.assertions.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN, manifest = Config.NONE)
public class MyLocationTest {

    @Test
    public void shouldParcelAndRestore() {
        Parcel parcel = Parcel.obtain();

        MyLocation location = new MyLocation(34.0, 34.5);

        location.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        MyLocation recreated = MyLocation.CREATOR.createFromParcel(parcel);

        Assertions.assertThat(recreated).isEqualTo(location);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotConstructWithIncorrectLatitude() {
        MyLocation location = new MyLocation(91.0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotConstructWIthIncorrectLongitude() {
        MyLocation location = new MyLocation(0, 181.0);
    }

}
