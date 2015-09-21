package pl.dawidgdanski.compass.compassapi.test.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Arrays;
import java.util.Collection;

public final class CompassMatchers {

    private CompassMatchers() { }

    public static Matcher<Collection<String>> containsLocationProviders(final String... locationProviders) {

        return new BaseMatcher<Collection<String>>() {
            @Override
            public boolean matches(Object o) {
                Collection<String> providers = (Collection<String>) o;

                return providers.containsAll(Arrays.asList(locationProviders));
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }
}
