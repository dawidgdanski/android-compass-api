package pl.dawidgdanski.compass.inject;

public final class Qualifiers {

    private Qualifiers() {
    }

    public static final String READABLE_DATABASE = "readableDatabase";

    public static final String WRITABLE_DATABASE = "writableDatabase";

    public static final String MULTI_COMPASS = "multiCompass";

    public static final String NATIVE_COMPASS = "nativeCompass";

    public static final String PLAY_SERVICES_COMPASS = "playServicesCompass";

    public static final String DEFAULT_LOCATION_CRITERIA = "defaultLocationCriteria";

    public static final String HIGH_ACCURACY_LOCATION_CRITERIA = "highAccuracyLocationCriteria";
}
