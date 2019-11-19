package ru.cft.focusstart.kolesnikov;

import ru.cft.focusstart.kolesnikov.figure.FigureParameterSetter;
import ru.cft.focusstart.kolesnikov.figure.figuretype.Figure;
import ru.cft.focusstart.kolesnikov.reading.FigureParametersReader;
import ru.cft.focusstart.kolesnikov.writing.FigureParametersWriter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class FigureMain {
    public static void main(String[] args) throws IOException {
        PrintWriter printWriter;
        FigureParametersReader figureParametersReader;
        if (args.length < 1) {
            throw new IllegalArgumentException("Нужен хотя бы один аргумент командной строки(имя входного файла)");
        }
        figureParametersReader = new FigureParametersReader(args[0]);
        if (args.length < 2) {
            printWriter = new PrintWriter(System.out);
        } else {
            printWriter = new PrintWriter(".//task2\\src\\main\\resources\\" + args[1]);
        }
        Figure figure = FigureParameterSetter.getFigureWithParameters(figureParametersReader);
        FigureParametersWriter figureWriter = new FigureParametersWriter(figure);
        figureWriter.writeFigureParameters(printWriter);
    }
}
