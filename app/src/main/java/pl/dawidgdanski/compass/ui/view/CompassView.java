package pl.dawidgdanski.compass.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.dawidgdanski.compass.R;

public class CompassView extends RelativeLayout {

    private static final float DEFAULT_COMPASS_SIZE_FRACTION = 0.75f;
    private static final float DEFAULT_COMPASS_ARROW_FRACTION = 0.8f * 0.75f;

    private float compassSizeFraction = DEFAULT_COMPASS_SIZE_FRACTION;
    private float arrowSizeFraction = DEFAULT_COMPASS_ARROW_FRACTION;

    @Bind(R.id.compass)
    ImageView compass;

    @Bind(R.id.arrow)
    ImageView arrow;

    private boolean isCompassSizeAdjusted;

    public CompassView(Context context) {
        this(context, null);
    }

    public CompassView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CompassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CompassView(Context context,
                       AttributeSet attrs,
                       int defStyleAttr,
                       int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(attrs, defStyleAttr);
    }

    private void initialize(AttributeSet attributeSet, int defStyleAttr) {
        final Context context = getContext();
        inflate(context, R.layout.merge_compass_view_content, this);

        if(attributeSet == null) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attributeSet,
                R.styleable.CompassView,
                defStyleAttr,
                0);

        compassSizeFraction = typedArray.getFraction(R.styleable.CompassView_compass_fraction, 1, 1, DEFAULT_COMPASS_SIZE_FRACTION);
        arrowSizeFraction = typedArray.getFraction(R.styleable.CompassView_arrow_fraction, 1, 1, DEFAULT_COMPASS_ARROW_FRACTION);

        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        adjustChildrenDimensions();
    }

    public View getArrowView() {
        return arrow;
    }

    private void adjustChildrenDimensions() {
        if(!isCompassSizeAdjusted) {
            final int width = getWidth() - getPaddingLeft() - getPaddingRight();
            final int height = getHeight() - getPaddingTop() - getPaddingBottom();

            final int ox = (int) (width * compassSizeFraction);
            final int oy = (int) (height * compassSizeFraction);

            final int compassSize = (ox != 0 && oy != 0) ? Math.min(ox, oy) : Math.max(ox, oy);

            final int arrowSize = (int) (compassSize * arrowSizeFraction);

            ViewGroup.LayoutParams compassLayoutParams = compass.getLayoutParams();
            compassLayoutParams.height = compassLayoutParams.width = compassSize;

            ViewGroup.LayoutParams arrowLayoutParams = arrow.getLayoutParams();
            arrowLayoutParams.height = arrowLayoutParams.width = arrowSize;

            requestLayout();
            isCompassSizeAdjusted = true;
        }
    }
}
