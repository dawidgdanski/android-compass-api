package pl.dawidgdanski.compass.inject;

import com.google.common.base.Preconditions;

import pl.dawidgdanski.compass.CompassApplication;

public class DependencyInjector {

    private static DependencyGraph dependencyGraph;

    public static synchronized void initialize(final CompassApplication app) {
        dependencyGraph = MainComponent.Initializer.initialize(app, app);
    }

    public static synchronized boolean isInitialized() {
        return dependencyGraph != null;
    }

    public static synchronized DependencyGraph getGraph() {
        Preconditions.checkNotNull(isInitialized(), "Dependency Graph not initialized");

        return dependencyGraph;
    }

    protected DependencyInjector() {

    }
}
