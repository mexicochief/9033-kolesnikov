package ru.cft.focusstart.kolesnikov.reading;

import ru.cft.focusstart.kolesnikov.figure.figuretype.FigureType;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FigureParametersReader {

    public static FigureParameters getFigureParameters(String filename) throws IOException {
        File file = new File(filename);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
             Scanner scanner = new Scanner(file)) {
            FigureType figureType = readFigureType(bufferedReader);
            checkQuantityLineInFile(scanner, figureType);
            return new FigureParameters(figureType,
                    readFigureParameters(bufferedReader, figureType));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Указанный файл не найден");
        }
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
        while (scanner.hasNext() && figureType.quantityParameters >= count) {
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
            value = Double.valueOf(bufferedReader.readLine());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Неверный символ в файле или пустая строка");
        }
        if (value < 0) {
            throw new IllegalArgumentException("отрицательное значение в файле");
        }
        return value;
    }
}
