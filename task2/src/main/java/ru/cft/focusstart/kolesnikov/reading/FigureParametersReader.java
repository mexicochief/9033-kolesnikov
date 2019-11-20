package ru.cft.focusstart.kolesnikov.reading;

import ru.cft.focusstart.kolesnikov.figure.figuretype.FigureType;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FigureParametersReader {

    public static FigureParameters readFigureParameters(String filename) throws IOException {
        FigureParameters figureParameters;
        File file = new File(filename);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
             Scanner scanner = new Scanner(file)) {
            FigureType figureType = readFigureType(bufferedReader);
            figureParameters = new FigureParameters(figureType);
            checkQuantityLineInFile(scanner, figureType);
            figureParameters.setFigureParameters(readFigureParameters(bufferedReader, figureType));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Указанный файл не найден");
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        return figureParameters;
    }

    private static FigureType readFigureType(BufferedReader bufferedReader) throws IOException {
        String str = bufferedReader.readLine();
        return FigureType.valueOf(str);
    }

    private static void checkQuantityLineInFile(Scanner scanner, FigureType figureType) throws IOException {
        if (quantityLineInFile(scanner, figureType) != figureType.quantityParameters) {
            throw new IOException("Некоректное число строк в файле");
        }
    }

    private static int quantityLineInFile(Scanner scanner, FigureType figureType) {
        int count = 0;
        while (scanner.hasNext() || figureType.quantityParameters > count + 1) {
            scanner.nextLine();
            count++;
        }
        return count;
    }

    private static ArrayList<Double> readFigureParameters(BufferedReader bufferedReader, FigureType figureType) throws IOException {
        ArrayList<Double> parameters = new ArrayList<>();
        for (int i = 1; i < figureType.quantityParameters; i++) {
            parameters.add(readCorrectParameter(bufferedReader));
        }
        return parameters;
    }

    private static double readCorrectParameter(BufferedReader bufferedReader) throws IOException {
        double value;
        try {
            value = Double.parseDouble(bufferedReader.readLine());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Неверный символ в файле или пустая строка");
        }
        if (value < 0) {
            throw new IllegalArgumentException("отрицательное значение в файле");
        }
        return value;
    }
}
