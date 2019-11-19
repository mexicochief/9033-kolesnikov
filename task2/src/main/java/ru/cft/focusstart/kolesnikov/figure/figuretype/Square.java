package ru.cft.focusstart.kolesnikov.figure.figuretype;

import ru.cft.focusstart.kolesnikov.DoubleFormat;

import java.util.LinkedHashMap;

public class Square extends Rectangle {
    private LinkedHashMap<String, String> specialParameters;
    private DoubleFormat doubleFormat = new DoubleFormat();

    public Square(double sideLength) {
        super(sideLength, sideLength);
        specialParameters = new LinkedHashMap<>();
    }


    @Override
    public String getName() {
        return FigureType.SQUARE.nameRus;
    }

    @Override
    public LinkedHashMap<String, String> getSpecialParameters() {
        if (specialParameters.isEmpty()) {
            fillSpecialParameters();
        }
        return specialParameters;
    }

    private void fillSpecialParameters() {
        specialParameters.put("Длина стороны:", doubleFormat.getGetRoundedValue(getSideLength()));
        specialParameters.put("Длина диагонали", doubleFormat.getGetRoundedValue(getDiagonalLength()));
    }

    private double getSideLength() {
        return firstSide;
    }

    private double getDiagonalLength() {
        return Math.sqrt(Math.sqrt(2) * firstSide);
    }

}
