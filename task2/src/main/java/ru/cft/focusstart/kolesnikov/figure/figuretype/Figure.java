package ru.cft.focusstart.kolesnikov.figure.figuretype;

import java.util.Map;

public interface Figure {

    String getName();

    Map<String, String> getCommonParameters();

    Map<String, String> getSpecialParameters();

}
