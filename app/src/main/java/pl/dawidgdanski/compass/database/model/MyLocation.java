package pl.dawidgdanski.compass.database.model;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import pl.dawidgdanski.compass.database.contract.MyLocationContract;
import pl.dawidgdanski.compass.util.Ranges;

public class MyLocation implements Parcelable {

    private static final String PARCELABLE_LATITUDE = "mylocation:lat";
    private static final String PARCELABLE_LONGITUDE = "mylocation:lng";

    public static final Creator<MyLocation> CREATOR = new Creator<MyLocation>() {
        @Override
        public MyLocation createFromParcel(Parcel source) {
            Bundle bundle = source.readBundle();

            return new MyLocation(bundle.getDouble(PARCELABLE_LATITUDE),
                    bundle.getDouble(PARCELABLE_LONGITUDE));
        }

        @Override
        public MyLocation[] newArray(int size) {
            return new MyLocation[size];
        }
    };

    private final double latitude;

    private final double longitude;

    private final String toString;

    private final int hashCode;

    public MyLocation(double latitude, double longitude) {
        Preconditions.checkArgument(Ranges.LATITUDE_RANGE.contains(latitude), "Invalid latitude");
        Preconditions.checkArgument(Ranges.LONGITUDE_RANGE.contains(longitude), "Invalid longitude");
        this.latitude = latitude;
        this.longitude = longitude;
        this.toString = String.format("lat: %s, lng: %s", String.valueOf(latitude), String.valueOf(longitude));
        this.hashCode = Objects.hashCode(latitude, longitude);
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }

        if(o == null || !(o instanceof MyLocation)) {
            return false;
        }

        MyLocation location = (MyLocation) o;

        return this.latitude == location.latitude &&
                this.longitude == location.longitude;
    }

    @Override
    public String toString() {
        return toString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        final Bundle bundle = new Bundle();
        bundle.putDouble(PARCELABLE_LATITUDE, latitude);
        bundle.putDouble(PARCELABLE_LONGITUDE, longitude);

        dest.writeBundle(bundle);
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyLocationContract.Table.COLUMN_LATITUDE, String.valueOf(latitude));
        contentValues.put(MyLocationContract.Table.COLUMN_LONGITUDE, String.valueOf(longitude));

        return contentValues;
    }
}
