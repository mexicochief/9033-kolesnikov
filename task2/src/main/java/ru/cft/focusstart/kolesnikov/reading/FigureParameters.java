package ru.cft.focusstart.kolesnikov.reading;

import ru.cft.focusstart.kolesnikov.figure.figuretype.FigureType;

import java.util.ArrayList;


public class FigureParameters {
    private FigureType figureType;
    private ArrayList<Double> parameters;


    FigureParameters(FigureType figureType, ArrayList<Double> parameters) {
        this.figureType = figureType;
        this.parameters = parameters;
    }

    public ArrayList<Double> getFigureParameters() {
        return parameters;
    }

    public FigureType getFigureType() {
        return figureType;
    }
}
