package ru.cft.focusstart.kolesnikov.figure.figuretype;

import ru.cft.focusstart.kolesnikov.DoubleFormat;

import java.util.LinkedHashMap;

public class Rectangle implements Figure {
    private LinkedHashMap<String, String> specialParameters;
    private LinkedHashMap<String, String> commonParameters;
    private DoubleFormat doubleFormat = new DoubleFormat();
    protected double firstSide;
    private double secondSide;

    public Rectangle(double firstSide, double secondSide) {
        this.firstSide = firstSide;
        this.secondSide = secondSide;
        specialParameters = new LinkedHashMap<>();
        commonParameters = new LinkedHashMap<>();
    }

    public LinkedHashMap<String, String> getCommonParameters() {
        if (commonParameters.isEmpty()) {
            fillCommonParameters();
        }
        return commonParameters;
    }

    private void fillCommonParameters() {
        commonParameters.put("Тип фигуры:", getName());
        commonParameters.put("Площадь:", doubleFormat.getGetRoundedValue(getSquare()));
        commonParameters.put("Периметр:", doubleFormat.getGetRoundedValue(getPerimeter()));

    }

    @Override
    public String getName() {
        return FigureType.RECTANGLE.nameRus;
    }

    private double getSquare() {
        return firstSide * secondSide;
    }

    private double getPerimeter() {
        return (firstSide + secondSide) * 2;
    }


    public LinkedHashMap<String, String> getSpecialParameters() {
        if (specialParameters.isEmpty()) {
            fillSpecialParameters();
        }
        return specialParameters;
    }

    private void fillSpecialParameters() {
        specialParameters.put("Ширина:", doubleFormat.getGetRoundedValue(getWidth()));
        specialParameters.put("Длина:", doubleFormat.getGetRoundedValue(getLength()));
        specialParameters.put("Длина диагонали", doubleFormat.getGetRoundedValue(getDiagonalLength()));
    }

    private double getLength() {
        return Math.max(firstSide,secondSide);
    }

    private double getWidth() {
        return Math.min(firstSide,secondSide);
    }

    private double getDiagonalLength() {
        return Math.sqrt(Math.pow(firstSide, 2) + Math.pow(secondSide, 2));
    }


}
