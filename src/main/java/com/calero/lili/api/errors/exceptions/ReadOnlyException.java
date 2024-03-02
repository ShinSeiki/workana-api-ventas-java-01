package com.calero.lili.api.errors.exceptions;

public class ReadOnlyException extends RuntimeException {

    public ReadOnlyException(String message) {
        super(message);
    }

}
