package pl.dawidgdanski.compass.inject;

import pl.dawidgdanski.compass.CompassApplication;
import pl.dawidgdanski.compass.inject.module.CompassApplicationModule;
import pl.dawidgdanski.compass.inject.module.CompassModule;
import pl.dawidgdanski.compass.inject.module.DatabaseModule;
import pl.dawidgdanski.compass.inject.module.GeomagneticModule;
import pl.dawidgdanski.compass.inject.module.LocationModule;
import pl.dawidgdanski.compass.inject.module.PersistenceModule;

public interface ModuleProvisionContract {

    CompassApplicationModule getCompassApplicationModule(CompassApplication compassApplication);

    CompassModule getCompassModule(CompassApplication compassApplication);

    DatabaseModule getDatabaseModule(CompassApplication compassApplication);

    GeomagneticModule getGeomagneticModule(CompassApplication compassApplication);

    LocationModule getLocationModule(CompassApplication compassApplication);

    PersistenceModule getPersistenceModule(CompassApplication compassApplication);
}
