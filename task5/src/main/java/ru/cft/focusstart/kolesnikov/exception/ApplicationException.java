package ru.cft.focusstart.kolesnikov.exception;

public abstract class ApplicationException extends RuntimeException {

    ApplicationException(String msg) {
        super(msg);
    }

}
