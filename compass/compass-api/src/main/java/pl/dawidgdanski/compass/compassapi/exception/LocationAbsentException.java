package pl.dawidgdanski.compass.compassapi.exception;

public class LocationAbsentException extends Exception {

    public LocationAbsentException() {
        super("Last known location is not cached by the system.");
    }
}
