package ru.cft.focusstart.kolesnikov.figure.figuretype;

import java.util.HashMap;

public interface Figure {

    String getName();

    HashMap<String, String> getCommonParameters();

    HashMap<String, String> getSpecialParameters();

}
