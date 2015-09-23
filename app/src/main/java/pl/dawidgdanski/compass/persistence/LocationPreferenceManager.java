package pl.dawidgdanski.compass.persistence;

import android.content.Context;

import pl.dawidgdanski.compass.database.model.MyLocation;

public class LocationPreferenceManager {

    private static final String PREFERENCE_HAS_LAST_DESTINATION = "has_last_dest";

    private static final String PREFERENCE_DESTINATION_LATITUDE = "latitude";
    private static final String PREFERENCE_DESTINATION_LONGITUDE = "longitude";

    private final Preferences preferences;

    public LocationPreferenceManager(Context context) {
        this.preferences = new Preferences("location_prefs", context);
    }

    public void saveDestination(MyLocation myLocation) {
        preferences.edit()
                .putBoolean(PREFERENCE_HAS_LAST_DESTINATION, true)
                .putString(PREFERENCE_DESTINATION_LATITUDE, String.valueOf(myLocation.getLatitude()))
                .putString(PREFERENCE_DESTINATION_LONGITUDE, String.valueOf(myLocation.getLongitude()))
                .apply();
    }

    public MyLocation getLastDestination() {
        if(!hasLastDestination()) {
            return null;
        }

        return new MyLocation(
                Double.valueOf(preferences.getString(PREFERENCE_DESTINATION_LATITUDE)),
                Double.valueOf(preferences.getString(PREFERENCE_DESTINATION_LONGITUDE)));
    }

    public boolean hasLastDestination() {
        return preferences.getBoolean(PREFERENCE_HAS_LAST_DESTINATION);
    }
}
