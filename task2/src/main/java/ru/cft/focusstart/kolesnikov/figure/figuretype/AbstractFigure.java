package ru.cft.focusstart.kolesnikov.figure.figuretype;

import java.util.HashMap;

public abstract class AbstractFigure implements Figure {
    protected HashMap<String, String> commonParameters;
    protected HashMap<String, String> specialParameters;

    AbstractFigure(){
        commonParameters = new HashMap<>();
        specialParameters= new HashMap<>();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public HashMap<String, String> getCommonParameters() {
        if (commonParameters.isEmpty()) {
            fillCommonParameters();
        }
        return commonParameters;
    }

    protected abstract void fillCommonParameters();

    @Override
    public HashMap<String, String> getSpecialParameters() {
        if (specialParameters.isEmpty()) {
            fillSpecialParameters();
        }
        return specialParameters;
    }

    protected abstract void fillSpecialParameters();
}
