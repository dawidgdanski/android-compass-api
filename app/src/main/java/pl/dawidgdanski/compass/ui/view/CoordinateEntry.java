package pl.dawidgdanski.compass.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.SparseArray;
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
        setSaveEnabled(true);
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

        decimalPartText.setText(savedState.decimalPart);
        floatingPartText.setText(savedState.floatingPart);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);

        savedState.decimalPart = decimalPartText.getText().toString();
        savedState.floatingPart= floatingPartText.getText().toString();

        return savedState;
    }

    public double getValue() {
        validateSelf();
        return Double.valueOf(String.format("%s.%s", decimalPartText.getText(), floatingPartText.getText()));
    }

   private static class SavedState extends BaseSavedState {

        private static final String SAVED_STATE_DECIMAL = "ce:decimal";
        private static final String SAVED_STATE_FLOATING = "ce:floating";

        private String decimalPart;

        private String floatingPart;

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
            decimalPart = bundle.getString(SAVED_STATE_DECIMAL);
            floatingPart = bundle.getString(SAVED_STATE_FLOATING);
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            Bundle bundle = new Bundle();
            bundle.putString(SAVED_STATE_DECIMAL, decimalPart);
            bundle.putString(SAVED_STATE_FLOATING, floatingPart);
            out.writeBundle(bundle);
        }
    }
}
