package pl.dawidgdanski.compass.ui.validation;

import com.google.common.collect.Range;

import pl.dawidgdanski.compass.R;
import pl.dawidgdanski.compass.exception.ValidationException;
import pl.dawidgdanski.compass.interfaces.Validator;

public class LongitudeTextValidator implements Validator<String> {

    private static final Range<Double> LONGITUDE_RANGE = Range.closed(-180.0, 180.0);

    @Override
    public void validate(String instance) throws ValidationException {
        try {

            if(! LONGITUDE_RANGE.contains(Double.valueOf(instance))) {
                throw new ValidationException(R.string.longitude_out_of_range);
            }
        } catch(IllegalArgumentException e) {
            throw new ValidationException(R.string.invalid_longitude_applied);
        }
    }
}
