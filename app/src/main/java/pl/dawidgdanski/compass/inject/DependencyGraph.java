package pl.dawidgdanski.compass.inject;

import javax.inject.Singleton;

import dagger.Component;
import pl.dawidgdanski.compass.exception.ValidationException;
import pl.dawidgdanski.compass.inject.module.CompassApplicationModule;
import pl.dawidgdanski.compass.inject.module.CompassModule;
import pl.dawidgdanski.compass.inject.module.DatabaseModule;
import pl.dawidgdanski.compass.inject.module.GeomagneticModule;
import pl.dawidgdanski.compass.inject.module.LocationModule;
import pl.dawidgdanski.compass.inject.module.PersistenceModule;
import pl.dawidgdanski.compass.provider.AppContentProvider;
import pl.dawidgdanski.compass.ui.activity.MainActivity;
import pl.dawidgdanski.compass.ui.dialog.LocationCreationDialogFragment;

@Singleton
@Component(modules = {
        CompassApplicationModule.class,
        CompassModule.class,
        DatabaseModule.class,
        GeomagneticModule.class,
        LocationModule.class,
        PersistenceModule.class})
public interface DependencyGraph {

        void inject(AppContentProvider contentProvider);

        void inject(MainActivity mainActivity);

        void inject(ValidationException exception);

        void inject(LocationCreationDialogFragment fragment);
}
