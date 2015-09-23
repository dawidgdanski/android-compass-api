package pl.dawidgdanski.compass.ui.validation;

import pl.dawidgdanski.compass.R;
import pl.dawidgdanski.compass.exception.ValidationException;
import pl.dawidgdanski.compass.interfaces.Validator;
import pl.dawidgdanski.compass.util.Ranges;

public class LatitudeTextValidator implements Validator<String> {

    @Override
    public void validate(String instance) throws ValidationException {
        try {

            if(! Ranges.LATITUDE_RANGE.contains(Double.valueOf(instance))) {
                throw new ValidationException(R.string.latitude_out_of_range);
            }
        } catch(IllegalArgumentException e) {
            throw new ValidationException(R.string.invalid_latitute_applied);
        }
    }


}
