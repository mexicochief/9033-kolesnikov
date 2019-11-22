package ru.cft.focusstart.kolesnikov.figure.figuretype;

public class EmptyFigure extends AbstractFigure {

    @Override
    public String getName() {
        return "Пустая фигура";
    }

    @Override
    protected void fillCommonParameters() {
        commonParameters.put("Тип:", getName());
    }

    @Override
    protected void fillSpecialParameters() {
        specialParameters.put("Сообщение:", "Задайте правильный тип фигуры");
    }

}
