package pl.dawidgdanski.compass.ui.validation;

import pl.dawidgdanski.compass.R;
import pl.dawidgdanski.compass.exception.ValidationException;
import pl.dawidgdanski.compass.interfaces.Validator;

public class CoordinateFloatingPartTextValidator implements Validator<String> {

    @Override
    public void validate(String instance) throws ValidationException {
        try {
            final int number = Integer.parseInt(instance);
            final int textLength = instance.length();
            if(!(number >= 0 && textLength > 0 && textLength <= 6)) {
                throw new ValidationException(R.string.floating_part_precision_places_at_most);
            }
        } catch(IllegalArgumentException e) {
            throw new ValidationException(R.string.incorect_floating_point_value);
        }
    }
}
