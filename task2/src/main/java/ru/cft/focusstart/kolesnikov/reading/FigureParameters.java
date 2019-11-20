package ru.cft.focusstart.kolesnikov.reading;

import ru.cft.focusstart.kolesnikov.figure.figureparameters.FigureInputParameters;
import ru.cft.focusstart.kolesnikov.figure.figuretype.FigureType;

import java.util.ArrayList;
import java.util.HashMap;

public class FigureParameters {
    private FigureType figureType;
    private HashMap<FigureInputParameters, Double> figureParameters;


    FigureParameters(FigureType figureType) {
        this.figureType = figureType;
        figureParameters = new HashMap<>();
    }


    public HashMap<FigureInputParameters, Double> getFigureParameters() {
        return figureParameters;
    }

    void setFigureParameters(ArrayList<Double> parameters) {
        switch (figureType) {
            case CIRCLE:
                figureParameters.put(FigureInputParameters.RADIUS, parameters.get(0));
                break;
            case SQUARE:
                this.figureParameters.put(FigureInputParameters.SIDE, parameters.get(0));
                break;
            case RECTANGLE:
                figureParameters.put(FigureInputParameters.FIRST_SIDE, parameters.get(0));
                figureParameters.put(FigureInputParameters.SECOND_SIDE, parameters.get(1));
                break;

        }
    }

    public FigureType getFigureType() {
        return figureType;
    }
}
