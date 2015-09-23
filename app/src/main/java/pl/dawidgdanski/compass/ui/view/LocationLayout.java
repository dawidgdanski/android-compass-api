package pl.dawidgdanski.compass.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.location.Location;
import android.os.Build;
import android.util.AttributeSet;
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

    private void initialize(AttributeSet attrs) {
        setOrientation(VERTICAL);
        setWeightSum(LAYOUT_WEIGHT_SUM);

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
}
