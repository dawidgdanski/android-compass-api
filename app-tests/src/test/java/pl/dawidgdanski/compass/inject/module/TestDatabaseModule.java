package pl.dawidgdanski.compass.inject.module;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.dawidgdanski.compass.database.DatabaseHelper;
import pl.dawidgdanski.compass.database.model.PersistenceManager;
import pl.dawidgdanski.compass.inject.Qualifiers;

import static org.mockito.Mockito.spy;

public class TestDatabaseModule extends DatabaseModule {

    @Override
    public DatabaseHelper provideDatabaseHelper(Context context) {
        return spy(super.provideDatabaseHelper(context));
    }

    @Override
    public SQLiteDatabase provideReadableDatabase(DatabaseHelper databaseHelper) {
        return spy(super.provideReadableDatabase(databaseHelper));
    }

    @Override
    public SQLiteDatabase provideWritableDatabase(DatabaseHelper databaseHelper) {
        return spy(super.provideWritableDatabase(databaseHelper));
    }

    @Override
    public PersistenceManager providePersistenceManager(Context applicationContext) {
        return spy(super.providePersistenceManager(applicationContext));
    }
}
