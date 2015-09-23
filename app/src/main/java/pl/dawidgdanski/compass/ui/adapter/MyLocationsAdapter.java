package pl.dawidgdanski.compass.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.ArrayAdapter;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

import pl.dawidgdanski.compass.database.contract.MyLocationContract;
import pl.dawidgdanski.compass.database.model.MyLocation;

public class MyLocationsAdapter extends ArrayAdapter<MyLocation>{

    public static Uri getContentUri() {
        return MyLocationContract.CONTENT_URI;
    }

    public static String[] getProjection() {
        return new String[]{
                MyLocationContract.Table._ID,
                MyLocationContract.Table.COLUMN_LATITUDE,
                MyLocationContract.Table.COLUMN_LONGITUDE
        };
    }

    public static Collection<MyLocation> transform(final Cursor cursor) {
        return TRANSFORM_FUNCTION.apply(cursor);
    }

    private static final Function<Cursor, Collection<MyLocation>> TRANSFORM_FUNCTION = new Function<Cursor, Collection<MyLocation>>() {
        @Override
        public Collection<MyLocation> apply(Cursor input) {
            List<MyLocation> elements = Lists.newArrayList();

            if (input.moveToFirst()) {

                final int columnLatitude = input.getColumnIndexOrThrow(MyLocationContract.Table.COLUMN_LATITUDE);
                final int columnLongitude = input.getColumnIndexOrThrow(MyLocationContract.Table.COLUMN_LONGITUDE);

                while (!input.isAfterLast()) {
                    elements.add(new MyLocation(
                                    Double.valueOf(input.getString(columnLatitude)),
                                    Double.valueOf(input.getString(columnLongitude)))
                    );
                    input.moveToNext();
                }
            }

            return elements;
        }
    };

    private final List<MyLocation> elements;

    public MyLocationsAdapter(Context context, List<MyLocation> elements) {
        super(context, android.R.layout.simple_list_item_1, elements);
        this.elements = elements;
    }

    public void replaceAll(final Collection<MyLocation> elements) {
        this.elements.clear();
        this.elements.addAll(elements);
        notifyDataSetChanged();
    }




}
