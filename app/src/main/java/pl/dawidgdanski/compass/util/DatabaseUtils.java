package pl.dawidgdanski.compass.util;

import android.database.Cursor;
import android.database.MatrixCursor;

public final class DatabaseUtils {

    private DatabaseUtils() { }

    public static Cursor returnEmptyOrSameCursor(final Cursor cursor) {
        return cursor == null ? new EmptyCursor() : cursor;
    }

    private static class EmptyCursor extends MatrixCursor {

        public EmptyCursor() {
            super(new String[]{}, 1);
        }

        @Override
        public int getColumnIndexOrThrow(String columnName) {
            return -1;
        }

        @Override
        public int getColumnIndex(String columnName) {
            return -1;
        }
    }
}
