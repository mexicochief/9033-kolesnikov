package ru.cft.focusstart.kolesnikov.figure.figuretype;

import ru.cft.focusstart.kolesnikov.DoubleFormat;

import java.util.LinkedHashMap;

public class Circle implements Figure {
    private double radius;
    private LinkedHashMap<String, String> commonParameters;
    private LinkedHashMap<String, String> specialParameters;
    private DoubleFormat doubleFormat = new DoubleFormat();

    public Circle(double radius) {
        this.radius = radius;
        commonParameters = new LinkedHashMap<>();
        specialParameters = new LinkedHashMap<>();
    }

    @Override
    public LinkedHashMap<String, String> getCommonParameters() {
        if (commonParameters.isEmpty()) {
            fillCommonParameters();
        }
        return commonParameters;
    }

    private void fillCommonParameters() {
        commonParameters.put("Тип фиуры:", getName());
        commonParameters.put("Площадь:", doubleFormat.getGetRoundedValue(getSquare()));
        commonParameters.put("Периметр:", doubleFormat.getGetRoundedValue(getPerimeter()));
    }

    public String getName() {
        return String.valueOf(FigureType.CIRCLE.nameRus);
    }

    private double getSquare() {
        return radius * radius;
    }

    private double getPerimeter() {
        return (getDiameter() * Math.PI);
    }


    @Override
    public LinkedHashMap<String, String> getSpecialParameters() {
        if (specialParameters.isEmpty()) {
            fillSpecialParameters();
        }
        return specialParameters;
    }

    private void fillSpecialParameters() {
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
