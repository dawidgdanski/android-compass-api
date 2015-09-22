package pl.dawidgdanski.compass.compassapi.util;

public abstract class CompassOptional<T> {

    private static final Absent ABSENT = new Absent();

    public static <T> CompassOptional<T> absent() {
        return ABSENT;
    }

    public static <T> CompassOptional<T> of(final T instance) {
        CompassPreconditions.checkNotNull(instance, "Instance cannot be null");
        return new Present<T>(instance);
    }

    public abstract boolean isPresent();

    public abstract T get();

    private static class Present<T> extends CompassOptional<T> {

        private final T instance;

        private Present(T instance) {
            this.instance = instance;
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public T get() {
            return instance;
        }
    }

    private static class Absent<T> extends CompassOptional<T> {

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public T get() {
            throw new IllegalStateException("Cannot invoke get method on absent instance");
        }
    }
}
