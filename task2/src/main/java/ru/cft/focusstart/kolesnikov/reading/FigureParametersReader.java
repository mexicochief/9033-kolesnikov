package ru.cft.focusstart.kolesnikov.reading;

import ru.cft.focusstart.kolesnikov.figure.figuretype.FigureType;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FigureParametersReader {

    public static FigureParameters getFigureParameters(String filename) throws IOException {
        File file = new File(filename);
        try (Scanner scanner = new Scanner(file)) {
            FigureType figureType = readFigureType(scanner);
            return new FigureParameters(figureType,
                    readFigureParameters(scanner, figureType));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Указанный файл не найден");
        }
    }

    private static FigureType readFigureType(Scanner scanner) {
        String str = scanner.nextLine();
        return FigureType.valueOf(str);
    }

    private static ArrayList<Double> readFigureParameters(Scanner scanner,
                                                          FigureType figureType) throws IOException {
        ArrayList<Double> parameters = new ArrayList<>();
        for (int i = 1; i < figureType.quantityParameters; i++) {
            parameters.add(readCorrectParameter(scanner));
        }
        if (scanner.hasNext()) {
            throw new IOException("Слишком много арументов");
        }
        return parameters;
    }

    private static double readCorrectParameter(Scanner scanner) {
        double value;
        try {
            value = scanner.nextDouble();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Неверный символ в файле или пустая строка");
        }
        if (value < 0) {
            throw new IllegalArgumentException("отрицательное значение в файле");
        }
        return value;
    }
}
