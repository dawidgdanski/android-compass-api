package pl.dawidgdanski.compass.persistence;

import android.content.ContentResolver;
import android.content.Context;

import com.google.common.base.Preconditions;

import pl.dawidgdanski.compass.database.contract.MyLocationContract;
import pl.dawidgdanski.compass.database.model.MyLocation;

public class PersistenceManager {

    private final ContentResolver contentResolver;

    public PersistenceManager(Context context) {
        Preconditions.checkNotNull(context);
        this.contentResolver = context.getApplicationContext().getContentResolver();
    }

    public void persistLocation(final MyLocation location) {
        contentResolver.insert(
                MyLocationContract.CONTENT_URI,
                location.toContentValues());
    }
}
