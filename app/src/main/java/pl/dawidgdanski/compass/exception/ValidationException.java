package pl.dawidgdanski.compass.exception;

import android.content.res.Resources;
import android.support.annotation.StringRes;

import javax.inject.Inject;

import pl.dawidgdanski.compass.inject.DependencyInjector;

public class ValidationException extends Exception {

    @Inject
    Resources resources;

    private final String message;

    public ValidationException(@StringRes int messageId) {
        DependencyInjector.getGraph().inject(this);
        message = resources.getString(messageId);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
