package ru.cft.focusstart.kolesnikov.figure.figuretype;

public enum FigureType {
    CIRCLE("CIRCLE", "Круг", 2),
    RECTANGLE("RECTANGLE", "Прямоугольник", 3),
    SQUARE("SQUARE", "Квадрат", 2);

    String name;
    String nameRus;
    public int quantityParameters;

    FigureType(String name, String nameRus, int quantityParameters) {
        this.name = name;
        this.nameRus = nameRus;
        this.quantityParameters = quantityParameters;
    }

}
