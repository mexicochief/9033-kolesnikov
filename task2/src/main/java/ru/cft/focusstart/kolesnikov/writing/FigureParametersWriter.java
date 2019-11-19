package ru.cft.focusstart.kolesnikov.writing;

import ru.cft.focusstart.kolesnikov.figure.figuretype.Figure;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class FigureParametersWriter {
    private Figure figureOb;

    public FigureParametersWriter(Figure figureOb) {
        this.figureOb = figureOb;
    }

    public void writeFigureParameters(PrintWriter printWriter) {
        HashMap<String, String> commonParameters = figureOb.getCommonParameters();
        HashMap<String, String> specialParameters = figureOb.getSpecialParameters();
        try (BufferedWriter bufferedWriter = new BufferedWriter(printWriter)) {
            for (Map.Entry<String, String> temp : commonParameters.entrySet()) {
                bufferedWriter.write(temp.getKey() + " " + temp.getValue());
                bufferedWriter.write(System.lineSeparator());
            }
            for (Map.Entry<String, String> temp : specialParameters.entrySet()) {
                bufferedWriter.write(temp.getKey() + " " + temp.getValue());
                bufferedWriter.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
