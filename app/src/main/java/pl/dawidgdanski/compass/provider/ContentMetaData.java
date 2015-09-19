package pl.dawidgdanski.compass.provider;

import android.net.Uri;

import java.util.Collection;
import java.util.Map;

public interface ContentMetaData {

    int LOCATION_COLLECTION_URI_INDICATOR = 1;

    int LOCATION_SINGLE_ITEM_URI_INDICATOR = 2;

    int getCode();

    Uri getUri();

    String getTableName();

    Map<String, String> getProjectionMap();

    String getDefaultSortOrder();

    String getContentType();

    Collection<Uri> getBoundNotificationUris();

    boolean isSingleItemType();
}
