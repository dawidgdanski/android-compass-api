package pl.dawidgdanski.compass.ui.loader;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class CursorLoader extends AbstractLoader<Cursor> {

    private final DisableableContentObserver contentObserver;

    private final Uri uri;
    private final String[] projection;
    private final String selection;
    private final String[] selectionArgs;
    private final String sortOrder;

    private CursorLoader(Builder builder) {
        super(builder.context);

        contentObserver = new DisableableContentObserver(new ForceLoadContentObserver());

        uri = builder.uri;
        projection = builder.projection;
        selection = builder.selection;
        selectionArgs = builder.selectionArgs;
        sortOrder = builder.sortOrder;
    }

    @Override
    protected void onStartLoading() {
        contentObserver.setEnabled(true);
        super.onStartLoading();
    }

    @Override
    protected void onAbandon() {
        contentObserver.setEnabled(false);
    }

    @Override
    protected void onReset() {
        contentObserver.setEnabled(false);
        super.onReset();
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = getContext().getContentResolver().query(uri, projection, selection,
                selectionArgs, sortOrder);
        if (cursor != null) {
            // Ensure the cursor window is filled
            cursor.getCount();
        }
        return cursor;
    }

    @Override
    protected void onNewDataDelivered(Cursor data) {
        data.registerContentObserver(contentObserver);
    }

    @Override
    protected void releaseResources(Cursor data) {
        if (data != null && !data.isClosed()) {
            data.close();
        }
    }

    public static class Builder {

        private Context context;

        private Uri uri;
        private String[] projection;
        private String selection;
        private String[] selectionArgs;
        private String sortOrder;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setUri(Uri uri) {
            this.uri = uri;
            return this;
        }

        public Builder setProjection(String[] projection) {
            this.projection = projection;
            return this;
        }

        public Builder setSelection(String selection) {
            this.selection = selection;
            return this;
        }

        public Builder setSelectionArgs(String[] selectionArgs) {
            this.selectionArgs = selectionArgs;
            return this;
        }

        public Builder setSortOrder(String sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        public CursorLoader build() {
            return new CursorLoader(this);
        }
    }
}
