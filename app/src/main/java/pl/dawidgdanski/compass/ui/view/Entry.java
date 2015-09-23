package pl.dawidgdanski.compass.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.dawidgdanski.compass.R;

public class Entry extends LinearLayout {

    @Bind(R.id.title)
    TextView titleText;

    @Bind(R.id.value)
    TextView valueText;

    private String title;

    public Entry(Context context) {
        this(context, null);
    }

    public Entry(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public Entry(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Entry(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);
        titleText.setText(title);
    }

    public void setValue(final String valueText) {
        this.valueText.setText(valueText);
    }

    public void setTitle(final String title) {
        titleText.setText(title);
    }

    public String getValue() {
        return valueText.getText().toString();
    }

    private void initialize(AttributeSet attrs) {
        setSaveEnabled(true);
        final Context context = getContext();
        inflate(context, R.layout.entry, this);

        if(attrs == null) {
            return;
        }

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Entry);
        title = array.getString(R.styleable.Entry_entry_title);

        array.recycle();
    }
}
