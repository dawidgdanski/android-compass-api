package pl.dawidgdanski.compass.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.dawidgdanski.compass.R;

public class LocationLayout extends LinearLayout {

    private static final int LAYOUT_WEIGHT_SUM = 3;

    @Bind(R.id.header_title)
    TextView header;

    @Bind(R.id.address_entry)
    Entry address;

    @Bind(R.id.latitude_entry)
    Entry latitude;

    @Bind(R.id.longitude_entry)
    Entry longitude;

    private String title;

    public LocationLayout(Context context) {
        super(context);
        initialize(null);
    }

    public LocationLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LocationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LocationLayout(Context context,
                          AttributeSet attrs,
                          int defStyleAttr,
                          int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);
        header.setText(title);
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        super.dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        super.dispatchThawSelfOnly(container);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        final SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());

        address.setValue(savedState.address);
        longitude.setValue(savedState.longitude);
        latitude.setValue(savedState.latitude);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);

        savedState.address = address.getValue();
        savedState.longitude = longitude.getValue();
        savedState.latitude = latitude.getValue();

        return savedState;
    }

    private void initialize(AttributeSet attrs) {
        setOrientation(VERTICAL);
        setWeightSum(LAYOUT_WEIGHT_SUM);
        setSaveEnabled(true);

        final Context context = getContext();
        inflate(context, R.layout.merge_location_layout_content, this);

        if(attrs == null) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LocationLayout);

        title = typedArray.getString(R.styleable.LocationLayout_header_title);

        typedArray.recycle();
    }

    public void setAddress(final String addressString) {
        address.setValue(addressString);
    }

    public void setLocation(final Location location) {
        longitude.setValue(String.valueOf(location.getLatitude()));
        latitude.setValue(String.valueOf(location.getLongitude()));
    }

    private static class SavedState extends BaseSavedState {

        private static final String SAVED_STATE_ADDRESS = "ll:address";
        private static final String SAVED_STATE_LATITUDE = "ll:latitude";
        private static final String SAVED_STATE_LONGITUDE = "ll:longitude";

        private String address;

        private String latitude;

        private String longitude;

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        public SavedState(Parcel source) {
            super(source);
            Bundle bundle = source.readBundle();
            address = bundle.getString(SAVED_STATE_ADDRESS);
            latitude = bundle.getString(SAVED_STATE_LATITUDE);
            longitude = bundle.getString(SAVED_STATE_LONGITUDE);
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            Bundle bundle = new Bundle();
            bundle.putString(SAVED_STATE_ADDRESS, address);
            bundle.putString(SAVED_STATE_LATITUDE, latitude);
            bundle.putString(SAVED_STATE_LONGITUDE, longitude);
            out.writeBundle(bundle);
        }
    }
}
