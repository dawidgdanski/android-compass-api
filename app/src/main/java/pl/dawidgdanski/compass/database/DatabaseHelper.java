package pl.dawidgdanski.compass.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.google.common.io.Closeables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import pl.dawidgdanski.compass.R;
import pl.dawidgdanski.compass.database.contract.Contracts;

public final class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "compass_db.db";

    private static final String SEPARATOR = "#";

    private static final int DATABASE_VERSION = 1;

    private final Context context;

    public DatabaseHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        final String[] queries = getQueriesArray();

        for (final String query : queries) {
            database.execSQL(query.trim());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        for (final Uri resourceUri : Contracts.getResourceUris()) {
            String statement = String.format("DROP TABLE IF EXISTS %s", resourceUri.getLastPathSegment());
            database.execSQL(statement);
        }
        onCreate(database);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private InputStream openDatabaseMetaResource() {
        return context.getResources().openRawResource(R.raw.compass_database);
    }

    private String[] getQueriesArray() {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openDatabaseMetaResource()));
        String line;
        final StringBuilder queryBuilder = new StringBuilder();

        try {
            while ((line = bufferedReader.readLine()) != null) {
                queryBuilder.append(line);
            }
            final String databaseString = queryBuilder.toString();

            return databaseString.split(SEPARATOR);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(getClass().getSimpleName() + ": could not create database!");
        } finally {
            Closeables.closeQuietly(bufferedReader);
        }
    }


}
