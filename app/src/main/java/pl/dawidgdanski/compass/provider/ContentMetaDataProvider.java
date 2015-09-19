package pl.dawidgdanski.compass.provider;

import android.content.UriMatcher;
import android.net.Uri;

import pl.dawidgdanski.compass.database.contract.Contracts;
import pl.dawidgdanski.compass.database.contract.LocationContract;

final class ContentMetaDataProvider {

    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

        URI_MATCHER.addURI(
                AppContentProvider.AUTHORITY,
                LocationContract.Table.TABLE_NAME,
                ContentMetaData.LOCATION_COLLECTION_URI_INDICATOR);
        URI_MATCHER.addURI(
                AppContentProvider.AUTHORITY,
                LocationContract.Table.TABLE_NAME + "/#",
                ContentMetaData.LOCATION_SINGLE_ITEM_URI_INDICATOR);
    }

    private ContentMetaDataProvider() {
    }

    static ContentMetaData matchContentMetaData(final Uri uri) {
        final int code = URI_MATCHER.match(uri);

        for(ContentMetaData contentMetaData : Contracts.getContentMetaDataHolders()) {
            if(contentMetaData.getCode() == code) {
                return contentMetaData;
            }
        }

        throw new IllegalArgumentException("Unsupported Uri:  " + uri.getEncodedPath());
    }
}
