package ru.cft.focusstart.kolesnikov;

import ru.cft.focusstart.kolesnikov.figure.FigureParameterSetter;
import ru.cft.focusstart.kolesnikov.figure.figuretype.Figure;
import ru.cft.focusstart.kolesnikov.reading.FigureParametersReader;
import ru.cft.focusstart.kolesnikov.writing.FigureParametersWriter;


import java.io.IOException;
import java.io.PrintWriter;

public class FigureMain {
    public static void main(String[] args) throws IOException {
        PrintWriter printWriter;

        if (args.length < 1) {
            throw new IllegalArgumentException("Нужен хотя бы один аргумент командной строки(имя входного файла)");
        }
        if (args.length < 2) {
            printWriter = new PrintWriter(System.out);
        } else {
            printWriter = new PrintWriter(args[1]);
        }
        Figure figure = FigureParameterSetter.
                getFigureWithParameters(FigureParametersReader.readFigureParameters(args[0]));
        FigureParametersWriter figureWriter = new FigureParametersWriter();
        figureWriter.writeFigureParameters(printWriter, figure);
    }
}
