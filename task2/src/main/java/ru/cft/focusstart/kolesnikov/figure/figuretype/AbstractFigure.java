package ru.cft.focusstart.kolesnikov.figure.figuretype;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractFigure implements Figure {
    protected Map<String, String> commonParameters;
    protected Map<String, String> specialParameters;

    AbstractFigure(){
        commonParameters = new HashMap<>();
        specialParameters= new HashMap<>();
    }

    @Override
    public Map<String, String> getCommonParameters() {
        if (commonParameters.isEmpty()) {
            fillCommonParameters();
        }
        return commonParameters;
    }

    protected abstract void fillCommonParameters();

    @Override
    public Map<String, String> getSpecialParameters() {
        if (specialParameters.isEmpty()) {
            fillSpecialParameters();
        }
        return specialParameters;
    }

    protected abstract void fillSpecialParameters();
}
