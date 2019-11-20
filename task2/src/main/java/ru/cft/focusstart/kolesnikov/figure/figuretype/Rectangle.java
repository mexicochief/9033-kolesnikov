package ru.cft.focusstart.kolesnikov.figure.figuretype;

import ru.cft.focusstart.kolesnikov.DoubleFormat;


public class Rectangle extends AbstractFigure {
    private DoubleFormat doubleFormat = new DoubleFormat();
    protected double firstSide;
    private double secondSide;

    public Rectangle(double firstSide, double secondSide) {
        super();
        this.firstSide = firstSide;
        this.secondSide = secondSide;
    }

    @Override
    public String getName() {
        return FigureType.RECTANGLE.nameRus;
    }

    protected void fillCommonParameters() {
        commonParameters.put("Тип фигуры:", getName());
        commonParameters.put("Площадь:", doubleFormat.getGetRoundedValue(getSquare()));
        commonParameters.put("Периметр:", doubleFormat.getGetRoundedValue(getPerimeter()));
        specialParameters.put("Длина диагонали", doubleFormat.getGetRoundedValue(getDiagonalLength()));

    }

    private double getSquare() {
        return firstSide * secondSide;
    }

    private double getPerimeter() {
        return (firstSide + secondSide) * 2;
    }

    private double getDiagonalLength() {
        return Math.sqrt(Math.pow(firstSide, 2) + Math.pow(secondSide, 2));
    }


    protected void fillSpecialParameters() {
        specialParameters.put("Ширина:", doubleFormat.getGetRoundedValue(getWidth()));
        specialParameters.put("Длина:", doubleFormat.getGetRoundedValue(getLength()));
    }

    private double getLength() {
        return Math.max(firstSide,secondSide);
    }

    private double getWidth() {
        return Math.min(firstSide,secondSide);
    }




}
