package ru.cft.focusstart.kolesnikov.writing;

import ru.cft.focusstart.kolesnikov.figure.figuretype.Figure;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class FigureParametersWriter {

    public void writeFigureParameters(PrintWriter printWriter, Figure figure) throws IOException {
        Map<String, String> commonParameters = figure.getCommonParameters();
        Map<String, String> specialParameters = figure.getSpecialParameters();
        try (BufferedWriter bufferedWriter = new BufferedWriter(printWriter)) { //
            for (Map.Entry<String, String> parameters : commonParameters.entrySet()) {
                bufferedWriter.write(parameters.getKey() + " " + parameters.getValue());
                bufferedWriter.write(System.lineSeparator());
            }
            for (Map.Entry<String, String> parameters : specialParameters.entrySet()) {
                bufferedWriter.write(parameters.getKey() + " " + parameters.getValue());
                bufferedWriter.write(System.lineSeparator());
            }
        }
    }
}

