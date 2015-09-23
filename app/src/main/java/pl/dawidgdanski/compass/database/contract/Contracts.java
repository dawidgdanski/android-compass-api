package pl.dawidgdanski.compass.database.contract;

import android.content.ContentResolver;
import android.net.Uri;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import pl.dawidgdanski.compass.provider.AppContentProvider;
import pl.dawidgdanski.compass.provider.ContentMetaData;

public final class Contracts {

    private Contracts() {
    }

    public static final String BASE_COLLECTION_TYPE = String.format("%s/vnd.pl.dawidgdanski.compass",
            ContentResolver.CURSOR_DIR_BASE_TYPE);

    public static final String BASE_SINGLE_ITEM_TYPE = String.format("%s/vnd.pl.dawidgdanski.compass",
            ContentResolver.CURSOR_ITEM_BASE_TYPE);

    public static final String BASE_CONTENT_URI = String.format("content://%s", AppContentProvider.AUTHORITY);

    public static Collection<Uri> getResourceUris() {
        return FluentIterable.from(Arrays.asList(ContentMetaDataHolder.values()))
                .transform(new Function<ContentMetaDataHolder, Uri>() {
                    @Override
                    public Uri apply(ContentMetaDataHolder input) {
                        return input.getUri();
                    }
                }).toList();
    }

    public static ContentMetaData[] getContentMetaDataHolders() {
        return ContentMetaDataHolder.values();
    }

    static Uri parseUri(String tableName) {
        return Uri.parse(String.format("%s/%s", BASE_CONTENT_URI, tableName));
    }

    static String parseCollectionContentType(String items) {
        return String.format("%s.%s", BASE_COLLECTION_TYPE, items);
    }

    static String parseSingleItemContentType(String item) {
        return String.format("%s.%s", BASE_SINGLE_ITEM_TYPE, item);
    }

    private enum ContentMetaDataHolder implements ContentMetaData {

        LOCATIONS_COLLECTION(
                LOCATION_COLLECTION_URI_INDICATOR,
                MyLocationContract.CONTENT_URI,
                MyLocationContract.Table.TABLE_NAME,
                MyLocationContract.PROJECTION_MAP,
                MyLocationContract.DEFAULT_SORT_ORDER,
                MyLocationContract.CONTENT_TYPE_COLLECTION,
                MyLocationContract.BOUND_NOTIFICATION_URIS,
                false),

        LOCATIONS_ITEM(
                LOCATION_SINGLE_ITEM_URI_INDICATOR,
                MyLocationContract.CONTENT_URI,
                MyLocationContract.Table.TABLE_NAME,
                MyLocationContract.PROJECTION_MAP,
                MyLocationContract.DEFAULT_SORT_ORDER,
                MyLocationContract.CONTENT_TYPE_SINGLE_ITEM,
                MyLocationContract.BOUND_NOTIFICATION_URIS,
                true);

        private final Uri uri;

        private final String tableName;

        private final Map<String, String> projectionMap;

        private final String defaultSortOrder;

        private final String contentType;

        private final Collection<Uri> boundUris;

        private final int code;

        private final boolean isSingeItemType;

        ContentMetaDataHolder(int code,
                              Uri uri,
                              String tableName,
                              Map<String, String> projectionMap,
                              String defaultSortOrder,
                              String contentType,
                              Collection<Uri> boundUris,
                              boolean isSingeItemType) {
            this.code = code;
            this.uri = uri;
            this.tableName = tableName;
            this.projectionMap = projectionMap;
            this.defaultSortOrder = defaultSortOrder;
            this.contentType = contentType;
            this.boundUris = boundUris;
            this.isSingeItemType = isSingeItemType;
        }


        @Override
        public int getCode() {
            return code;
        }

        @Override
        public Uri getUri() {
            return uri;
        }

        @Override
        public String getTableName() {
            return tableName;
        }

        @Override
        public Map<String, String> getProjectionMap() {
            return projectionMap;
        }

        @Override
        public String getDefaultSortOrder() {
            return defaultSortOrder;
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public Collection<Uri> getBoundNotificationUris() {
            return boundUris;
        }

        @Override
        public boolean isSingleItemType() {
            return isSingeItemType;
        }
    }

}
