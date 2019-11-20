package ru.cft.focusstart.kolesnikov.figure.figuretype;

import ru.cft.focusstart.kolesnikov.DoubleFormat;

public class Circle extends AbstractFigure {
    private double radius;
    private DoubleFormat doubleFormat = new DoubleFormat();

    public Circle(double radius) {
        super();
        this.radius = radius;
    }

    @Override
    public String getName() {
        return String.valueOf(FigureType.CIRCLE.nameRus);
    }

    @Override
    final protected void fillCommonParameters() {
        commonParameters.put("Тип фиуры:", getName());
        commonParameters.put("Площадь:", doubleFormat.getGetRoundedValue(getSquare()));
        commonParameters.put("Периметр:", doubleFormat.getGetRoundedValue(getPerimeter()));
    }

    private double getSquare() {
        return radius * radius;
    }

    private double getPerimeter() {
        return (getDiameter() * Math.PI);
    }


    @Override
    final protected void fillSpecialParameters() {
        specialParameters.put("Радиус:", doubleFormat.getGetRoundedValue(getRadius()));
        specialParameters.put("Диаметр:", doubleFormat.getGetRoundedValue(getDiameter()));
    }

    private double getRadius() {
        return radius;
    }

    private double getDiameter() {
        return radius * 2;
    }
}
