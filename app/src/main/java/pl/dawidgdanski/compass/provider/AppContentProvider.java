package pl.dawidgdanski.compass.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

import pl.dawidgdanski.compass.inject.DependencyInjector;
import pl.dawidgdanski.compass.inject.Qualifiers;


public class AppContentProvider extends ContentProvider {

    public static final String AUTHORITY = "pl.dawidgdanski.compass.provider.data";

    @Inject
    @Named(Qualifiers.READABLE_DATABASE)
    SQLiteDatabase readableDatabase;

    @Inject
    @Named(Qualifiers.WRITABLE_DATABASE)
    SQLiteDatabase writableDatabase;

    @Override
    public boolean onCreate() {
        DependencyInjector.getGraph().inject(this);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        ContentMetaData contentMetaData = ContentMetaDataProvider.matchContentMetaData(uri);

        queryBuilder.setTables(contentMetaData.getTableName());
        queryBuilder.setProjectionMap(contentMetaData.getProjectionMap());

        if (contentMetaData.isSingleItemType()) {
            queryBuilder.appendWhere(
                    ProviderUtils.whereEqualTo(BaseColumns._ID, ProviderUtils.getRowId(uri))
            );
        }

        String orderBy = sortOrder;

        if (TextUtils.isEmpty(orderBy)) {
            orderBy = contentMetaData.getDefaultSortOrder();
        }

        final Cursor cursor = queryBuilder.query(readableDatabase,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return ContentMetaDataProvider.matchContentMetaData(uri).getContentType();
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Preconditions.checkNotNull(values, "Inserting null content values");

        final ContentMetaData metaData = ContentMetaDataProvider.matchContentMetaData(uri);

        final long rowId = writableDatabase.insertOrThrow(
                metaData.getTableName(),
                null,
                values);

        ContentResolver contentResolver = getContext().getContentResolver();

        contentResolver.notifyChange(uri, null, false);

        ProviderUtils.notifyChange(contentResolver, metaData.getBoundNotificationUris(), null, false);

        return ContentUris.withAppendedId(uri, rowId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final ContentMetaData contentMetaData = ContentMetaDataProvider.matchContentMetaData(uri);

        int count;
        if (contentMetaData.isSingleItemType()) {
            count = writableDatabase.delete(
                    contentMetaData.getTableName(),
                    Joiner.on(" ").join(
                            ProviderUtils.whereEqualTo(BaseColumns._ID, ProviderUtils.getRowId(uri)),
                            ProviderUtils.withOptionalSelection(selection)
                    ),
                    selectionArgs);
        } else {
            count = writableDatabase.delete(
                    contentMetaData.getTableName(),
                    selection,
                    selectionArgs);
        }

        ContentResolver contentResolver = getContext().getContentResolver();
        contentResolver.notifyChange(uri, null, false);

        ProviderUtils.notifyChange(contentResolver, contentMetaData.getBoundNotificationUris(), null, false);

        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count;

        final ContentMetaData contentMetaData = ContentMetaDataProvider.matchContentMetaData(uri);

        if (contentMetaData.isSingleItemType()) {
            count = writableDatabase.update(
                    contentMetaData.getTableName(),
                    values,
                    Joiner.on(" ").join(
                            ProviderUtils.whereEqualTo(BaseColumns._ID, ProviderUtils.getRowId(uri)),
                            ProviderUtils.withOptionalSelection(selection)
                    ),
                    selectionArgs);
        } else {
            count = writableDatabase.update(
                    contentMetaData.getTableName(),
                    values,
                    selection,
                    selectionArgs);
        }

        ContentResolver contentResolver = getContext().getContentResolver();
        contentResolver.notifyChange(uri, null, false);

        ProviderUtils.notifyChange(contentResolver, contentMetaData.getBoundNotificationUris(), null, false);

        return count;
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {

        writableDatabase.beginTransaction();
        try {
            final int operationsCount = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[operationsCount];
            for (int i = 0; i < operationsCount; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            writableDatabase.setTransactionSuccessful();
            return results;
        } finally {
            writableDatabase.endTransaction();
        }
    }
}
