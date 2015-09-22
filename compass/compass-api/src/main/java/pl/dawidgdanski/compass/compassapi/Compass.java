package pl.dawidgdanski.compass.compassapi;
import android.view.View;

import pl.dawidgdanski.compass.compassapi.geomagnetic.AzimuthSupplier;
import pl.dawidgdanski.compass.compassapi.location.LocationSupplier;

public interface Compass extends AzimuthSupplier.OnAzimuthChangedListener,
        LocationSupplier.OnLocationChangedListener {

    void start();

    void stop();

    void destroy();

    void setView(View view);

    View getView();

    void navigateTo(double latitude, double longitude);

    void setOnLocationChangedListener(LocationSupplier.OnLocationChangedListener onLocationChangedListener);
}
