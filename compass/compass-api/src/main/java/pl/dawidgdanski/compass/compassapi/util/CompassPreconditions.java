package pl.dawidgdanski.compass.compassapi.util;

public final class CompassPreconditions {

    private CompassPreconditions() {
    }

    public static <T> void checkNotNull(T object, final String message) {
        if(object == null) {
            throw new NullPointerException(message);
        }
    }

    public static <T, E extends Throwable> void checkNotNull(final T object, final E exception) throws E {
        if(object == null) {
            throw exception;
        }
    }

    public static void checkState(final boolean state, final String message) {
        if(!state) {
            throw new IllegalStateException(message);
        }
    }

    public static void checkArgument(final boolean state, final String message) {
        if(!state) {
            throw new IllegalArgumentException(message);
        }
    }

}
