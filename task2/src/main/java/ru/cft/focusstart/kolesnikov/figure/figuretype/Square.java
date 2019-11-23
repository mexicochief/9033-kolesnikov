package ru.cft.focusstart.kolesnikov.figure.figuretype;

import ru.cft.focusstart.kolesnikov.DoubleFormat;


public class Square extends Rectangle {
    private DoubleFormat doubleFormat = new DoubleFormat();

    public Square(double sideLength) {
        super(sideLength, sideLength);
    }

    @Override
    public String getName() {
        return FigureType.SQUARE.nameRus;
    }

    final protected void fillSpecialParameters() {
        specialParameters.put("Длина стороны:", doubleFormat.getGetRoundedValue(getSideLength()));
    }

    private double getSideLength() {
        return firstSide;
    }
}
