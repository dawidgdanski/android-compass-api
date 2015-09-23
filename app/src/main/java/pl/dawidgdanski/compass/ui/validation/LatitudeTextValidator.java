package pl.dawidgdanski.compass.ui.validation;

import com.google.common.collect.Range;

import pl.dawidgdanski.compass.R;
import pl.dawidgdanski.compass.exception.ValidationException;
import pl.dawidgdanski.compass.interfaces.Validator;

public class LatitudeTextValidator implements Validator<String> {

    private static final Range<Double> LATITUDE_RANGE = Range.closed(-90.0, 90.0);

    @Override
    public void validate(String instance) throws ValidationException {
        try {

            if(! LATITUDE_RANGE.contains(Double.valueOf(instance))) {
                throw new ValidationException(R.string.latitude_out_of_range);
            }
        } catch(IllegalArgumentException e) {
            throw new ValidationException(R.string.invalid_latitute_applied);
        }
    }


}
