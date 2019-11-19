package ru.cft.focusstart.kolesnikov.figure;

import ru.cft.focusstart.kolesnikov.figure.figureparameters.FigureInputParameters;
import ru.cft.focusstart.kolesnikov.figure.figuretype.*;
import ru.cft.focusstart.kolesnikov.reading.FigureParametersReader;

public class FigureParameterSetter {

    private FigureParameterSetter(){}

    public static Figure getFigureWithParameters(FigureParametersReader parameters) {
        Figure figure = null;
        switch (parameters.getFigureType()) {
            case RECTANGLE:
                double firstParameters = parameters.getFigureParameters().get(FigureInputParameters.FIRST_SIDE);
                double secondParameters = parameters.getFigureParameters().get(FigureInputParameters.SECOND_SIDE);
                figure = new Rectangle(firstParameters, secondParameters);
                break;
            case SQUARE:
                double sideLength = parameters.getFigureParameters().get(FigureInputParameters.FIRST_SIDE);
                figure = new Square(sideLength);
                break;
            case CIRCLE:
                double radius = parameters.getFigureParameters().get(FigureInputParameters.RADIUS);
                figure = new Circle(radius);
                break;

        }
        return figure;
    }
}

