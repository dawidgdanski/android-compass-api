package pl.dawidgdanski.compass.inject;

import pl.dawidgdanski.compass.CompassApplication;

interface MainComponent extends DependencyGraph {

    final class Initializer {
        private Initializer() {
        }

        static DependencyGraph initialize(CompassApplication app, ModuleProvisionContract moduleProvisionContract) {
            return DaggerDependencyGraph.builder()
                    .compassApplicationModule(moduleProvisionContract.getCompassApplicationModule(app))
                    .build();
        }
    }

}
