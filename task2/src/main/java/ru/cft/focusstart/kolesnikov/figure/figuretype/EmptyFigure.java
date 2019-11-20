package ru.cft.focusstart.kolesnikov.figure.figuretype;

public class EmptyFigure extends AbstractFigure {
    @Override
    protected void fillCommonParameters() {
        commonParameters.put("Тип:","Пустая Фигура");
    }

    @Override
    protected void fillSpecialParameters() {
        specialParameters.put("Сообщение:","Задайте правильный тип фигуры");

    }
}
