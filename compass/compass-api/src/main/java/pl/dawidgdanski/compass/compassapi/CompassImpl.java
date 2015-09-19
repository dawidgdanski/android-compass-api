package pl.dawidgdanski.compass.compassapi;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.widget.ImageView;

import pl.dawidgdanski.compass.compassapi.location.LocationSupplier;
import pl.dawidgdanski.compass.compassapi.location.LocationSupplierImpl;
import pl.dawidgdanski.compass.compassapi.util.CompassPreconditions;

public class CompassImpl implements Compass {

    private final LocationSupplier locationSupplier;

    private final SensorManager sensorManager;
    private final Sensor gravitySensor;
    private final Sensor magenticFieldSensor;

    private float azimuth = 0f;
    private float currentAzimuth = 0;
    private float[] gravity = new float[3];
    private float[] geoMagnetic = new float[3];

    public ImageView arrowView = null;

    public CompassImpl(final SensorManager sensorManager, final LocationManager locationManager) {
        CompassPreconditions.checkNotNull(sensorManager, "SensorManager cannot be null");
        CompassPreconditions.checkNotNull(locationManager, "LocationManager cannot be null");

        this.sensorManager = sensorManager;
        this.locationSupplier = new LocationSupplierImpl(locationManager);

        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magenticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public synchronized void start() {
        sensorManager.registerListener(this, gravitySensor,
                SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magenticFieldSensor,
                SensorManager.SENSOR_DELAY_GAME);
    }

    public synchronized void stop() {
        sensorManager.unregisterListener(this);
    }

    /*private void adjustArrow() {
        if (arrowView == null) {
            return;
        }

        Animation an = new RotateAnimation(-currentAzimuth, -azimuth,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        currentAzimuth = azimuth;

        an.setDuration(500);
        an.setRepeatCount(0);
        an.setFillAfter(true);

        arrowView.startAnimation(an);
    }*/

    @Override
    public void onSensorChanged(SensorEvent event) {
        final float alpha = 0.97f;

        synchronized (this) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                gravity[0] = alpha * gravity[0] + (1 - alpha)
                        * event.values[0];
                gravity[1] = alpha * gravity[1] + (1 - alpha)
                        * event.values[1];
                gravity[2] = alpha * gravity[2] + (1 - alpha)
                        * event.values[2];
            }

            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {

                geoMagnetic[0] = alpha * geoMagnetic[0] + (1 - alpha)
                        * event.values[0];
                geoMagnetic[1] = alpha * geoMagnetic[1] + (1 - alpha)
                        * event.values[1];
                geoMagnetic[2] = alpha * geoMagnetic[2] + (1 - alpha)
                        * event.values[2];
            }

            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, gravity,
                    geoMagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimuth = (float) Math.toDegrees(orientation[0]);
                azimuth = (azimuth + 360) % 360;
                //adjustArrow();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
