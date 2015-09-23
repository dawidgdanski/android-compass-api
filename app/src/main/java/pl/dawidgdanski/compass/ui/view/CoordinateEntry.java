package pl.dawidgdanski.compass.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.dawidgdanski.compass.R;
import pl.dawidgdanski.compass.exception.ValidationException;
import pl.dawidgdanski.compass.interfaces.SelfValidable;
import pl.dawidgdanski.compass.interfaces.Validator;
import pl.dawidgdanski.compass.ui.validation.CoordinateFloatingPartTextValidator;

public class CoordinateEntry extends LinearLayout implements SelfValidable, TextWatcher {

    @Bind(R.id.decimal_part)
    CoordinateEditText decimalPartText;

    @Bind(R.id.floating_part)
    CoordinateEditText floatingPartText;

    public CoordinateEntry(Context context) {
        this(context, null);
    }

    public CoordinateEntry(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoordinateEntry(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CoordinateEntry(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();
    }

    private void initialize() {
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.coordinate_entry_content, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);
        decimalPartText.addTextChangedListener(this);
        floatingPartText.addTextChangedListener(this);

        floatingPartText.setTextValidators(Collections.<Validator<String>>singleton(new CoordinateFloatingPartTextValidator()));
    }

    public void setDecimalPartTextValidator(final Validator<String> textValidator) {
        decimalPartText.setTextValidators(Collections.singleton(textValidator));
    }

    @Override
    public boolean validateSelf() {
        try {
            decimalPartText.validate();
            floatingPartText.validate();
            return true;
        } catch (ValidationException e) {

            return false;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        validateSelf();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public double getValue() {
        return Double.valueOf(String.format("%s.%s", decimalPartText.getText(), floatingPartText.getText()));
    }
}
