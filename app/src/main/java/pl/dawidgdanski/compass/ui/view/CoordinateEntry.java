package pl.dawidgdanski.compass.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.dawidgdanski.compass.R;
import pl.dawidgdanski.compass.exception.ValidationException;
import pl.dawidgdanski.compass.interfaces.SelfValidable;
import pl.dawidgdanski.compass.interfaces.Validator;
import pl.dawidgdanski.compass.ui.validation.CoordinateFloatingPartTextValidator;

public class CoordinateEntry extends LinearLayout implements SelfValidable, TextWatcher {

    @Bind(R.id.error_text)
    TextView errorText;

    @Bind(R.id.coord_decimal_part)
    CoordinateEditText decimalPartText;

    @Bind(R.id.coord_floating_part)
    CoordinateEditText floatingPartText;

    public CoordinateEntry(Context context) {
        super(context);
        initialize();
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
            errorText.setVisibility(INVISIBLE);
            return true;
        } catch (ValidationException e) {
            errorText.setText(e.getMessage());
            errorText.setVisibility(VISIBLE);
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
        validateSelf();
        return Double.valueOf(String.format("%s.%s", decimalPartText.getText(), floatingPartText.getText()));
    }
}
