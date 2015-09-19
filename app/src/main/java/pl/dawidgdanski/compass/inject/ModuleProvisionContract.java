package pl.dawidgdanski.compass.inject;

import pl.dawidgdanski.compass.CompassApplication;
import pl.dawidgdanski.compass.inject.module.CompassApplicationModule;

public interface ModuleProvisionContract {

    CompassApplicationModule getCompassApplicationModule(CompassApplication compassApplication);
}
