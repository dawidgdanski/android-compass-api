package pl.dawidgdanski.compass.compassapi.geomagnetic;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import pl.dawidgdanski.compass.compassapi.util.CompassListeners;
import pl.dawidgdanski.compass.compassapi.util.CompassPreconditions;

public class AzimuthSupplierImpl implements AzimuthSupplier {

    private static final float ALPHA = 0.97f;

    private static final int ANGLE_360 = 360;

    private final SensorManager sensorManager;
    private final Sensor gravitySensor;
    private final Sensor magenticFieldSensor;

    private final Display display;

    private final float[] gravity = new float[3];
    private final float[] geoMagnetic = new float[3];
    private final float[] orientation = new float[3];

    private final float[] R = new float[9];
    private final float[] I = new float[9];

    private float azimuth = 0f;
    private float currentAzimuth = 0f;

    private float bearing = 0f;

    private OnAzimuthChangedListener onAzimuthChangedListener = OnAzimuthChangedListener.NULL_LISTENER;

    public AzimuthSupplierImpl(final SensorManager sensorManager, final WindowManager windowManager) {
        CompassPreconditions.checkNotNull(sensorManager, "SensorManager cannot be null");
        CompassPreconditions.checkNotNull(windowManager, "WindowManager cannot be null");

        this.sensorManager = sensorManager;
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magenticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        this.display = windowManager.getDefaultDisplay();
    }

    @Override
    public synchronized void start(OnAzimuthChangedListener onAzimuthChangedListener) {
        this.onAzimuthChangedListener = CompassListeners.returnSameOrNullListener(onAzimuthChangedListener);
        sensorManager.registerListener(this, gravitySensor,
                SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magenticFieldSensor,
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public synchronized void stop() {
        this.onAzimuthChangedListener = OnAzimuthChangedListener.NULL_LISTENER;
        sensorManager.unregisterListener(this);
    }

    @Override
    public synchronized void setBearing(float bearing) {
        this.bearing = bearing;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        synchronized (this) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                gravity[0] = ALPHA * gravity[0] + (1 - ALPHA)
                        * event.values[0];
                gravity[1] = ALPHA * gravity[1] + (1 - ALPHA)
                        * event.values[1];
                gravity[2] = ALPHA * gravity[2] + (1 - ALPHA)
                        * event.values[2];
            }

            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {

                geoMagnetic[0] = ALPHA * geoMagnetic[0] + (1 - ALPHA)
                        * event.values[0];
                geoMagnetic[1] = ALPHA * geoMagnetic[1] + (1 - ALPHA)
                        * event.values[1];
                geoMagnetic[2] = ALPHA * geoMagnetic[2] + (1 - ALPHA)
                        * event.values[2];
            }

            final boolean success = SensorManager.getRotationMatrix(R, I, gravity, geoMagnetic);

            if (success) {
                SensorManager.getOrientation(R, orientation);
                azimuth = (float) Math.toDegrees(orientation[0]);
                azimuth = (azimuth + ANGLE_360) % ANGLE_360;
                azimuth -= bearing;
                azimuth += compensateOrientationChange(display.getRotation());
                onAzimuthChangedListener.onAzimuthChanged(currentAzimuth, azimuth);
            }
            currentAzimuth = azimuth;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private static float compensateOrientationChange(final int rotation) {
        switch (rotation) {
            case Surface.ROTATION_90:
                return 0.0f;
            case Surface.ROTATION_180:
                return 0.0f;
            case Surface.ROTATION_270:
                return 0.0f;
            default:
                return 0.0f;
        }
    }
}
