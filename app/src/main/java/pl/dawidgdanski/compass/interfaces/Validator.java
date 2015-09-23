package pl.dawidgdanski.compass.interfaces;

import pl.dawidgdanski.compass.exception.ValidationException;

public interface Validator<T> {

    void validate(T instance) throws ValidationException;

}
