package ru.cft.focusstart.kolesnikov.service.validation;

public class Validator {
    public static <K> void checkNull(K param, String paramName) {
        if (param != null) {
            throw new IllegalArgumentException(String.format("Parameter %s need to be null", paramName));
        }
    }

    public static <K> void checkNotNull(K param, String paramName) {
        if (param == null) {
            throw new IllegalArgumentException(String.format("Parameter %s need to be not null", paramName));
        }
    }

    public static  void checkSize(String param, int min, int max) {
        if (param.length() < min || param.length() > max) {
            throw new IllegalArgumentException("Param out of range");
        }
    }
}
