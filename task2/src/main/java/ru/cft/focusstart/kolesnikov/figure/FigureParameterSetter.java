package ru.cft.focusstart.kolesnikov.figure;

import ru.cft.focusstart.kolesnikov.figure.figuretype.*;
import ru.cft.focusstart.kolesnikov.reading.FigureParameters;

public class FigureParameterSetter {

    private FigureParameterSetter() {
    }

    public static Figure getFigureWithParameters(FigureParameters parameters) {
        Figure figure = new EmptyFigure();
        switch (parameters.getFigureType()) {
            case RECTANGLE:
                double firstParameters = parameters.getFigureParameters().get(0);
                double secondParameters = parameters.getFigureParameters().get(1);
                if (firstParameters == secondParameters) {
                    figure = new Square(firstParameters);
                } else {
                    figure = new Rectangle(firstParameters, secondParameters);
                }
                break;
            case SQUARE:
                double sideLength = parameters.getFigureParameters().get(0);
                figure = new Square(sideLength);
                break;
            case CIRCLE:
                double radius = parameters.getFigureParameters().get(0);
                figure = new Circle(radius);
                break;
        }
        return figure;
    }
}

