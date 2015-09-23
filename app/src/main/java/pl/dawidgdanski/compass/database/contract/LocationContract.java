package pl.dawidgdanski.compass.database.contract;

import android.net.Uri;
import android.provider.BaseColumns;

import com.google.common.collect.ImmutableMap;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public final class LocationContract {

    public static final Uri CONTENT_URI = Contracts.parseUri(Table.TABLE_NAME);

    public static final Map<String, String> PROJECTION_MAP = ImmutableMap.<String, String>builder()
            .put(Table._ID, Table._ID)
            .put(Table.COLUMN_LATITUDE, Table.COLUMN_LATITUDE)
            .put(Table.COLUMN_LONGITUDE, Table.COLUMN_LONGITUDE)
            .build();

    public static final String DEFAULT_SORT_ORDER = String.format("%s ASC", Table.COLUMN_LATITUDE);

    public static final String CONTENT_TYPE_COLLECTION = Contracts.parseCollectionContentType("location");

    public static final String CONTENT_TYPE_SINGLE_ITEM = Contracts.parseSingleItemContentType("locations");

    public static final Collection<Uri> BOUND_NOTIFICATION_URIS = Collections.emptyList();

    public static final class Table implements BaseColumns {

        public static final String TABLE_NAME = "location";

        public static final String COLUMN_LATITUDE = "latitude";

        public static final String COLUMN_LONGITUDE = "longitude";
    }

    private LocationContract() { }

}
