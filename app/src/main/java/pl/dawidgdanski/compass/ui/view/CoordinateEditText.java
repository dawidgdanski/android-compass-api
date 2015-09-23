package pl.dawidgdanski.compass.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Collections;

import pl.dawidgdanski.compass.exception.ValidationException;
import pl.dawidgdanski.compass.interfaces.Validator;

public class CoordinateEditText extends EditText {
    public CoordinateEditText(Context context) {
        this(context, null);
    }

    private final Collection<Validator<String>> textValidators = Lists.newArrayList();

    public CoordinateEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoordinateEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CoordinateEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initialize() {

    }

    public void setTextValidators(Collection<Validator<String>> validators) {
        textValidators.clear();
        textValidators.addAll(validators);
    }

    public Collection<Validator<String>> getTextValidators() {
        return Collections.unmodifiableCollection(textValidators);
    }

    public void validate() throws ValidationException {
        final String text = getText().toString();
        for(Validator<String> validator : textValidators) {
            validator.validate(text);
        }
    }
}
