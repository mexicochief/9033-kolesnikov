package ru.cft.focusstart.kolesnikov.reading;

import ru.cft.focusstart.kolesnikov.figure.figureparameters.FigureInputParameters;
import ru.cft.focusstart.kolesnikov.figure.figuretype.FigureType;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FigureParametersReader {
    private BufferedReader bufferedReader;
    private HashMap<Enum, Double> figureParameters;
    private FigureType figureType;

    public FigureParametersReader(String filename) throws IOException {
        figureParameters = new HashMap<>();
        File file = new File(".//task2\\src\\main\\resources\\" + filename);
        if (!file.exists()) {
            throw new FileNotFoundException("Указанный файл не найден в папке resources");
        }
        FileReader fileReader = new FileReader(file);
        bufferedReader = new BufferedReader(fileReader);
        setFigureType();
        checkQuantityLineInFile(file);
        setFigureParameters();
        fileReader.close();
    }

    private void setFigureType() {
        String str = null;
        try {
            str = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!getAllFigureType().contains(str)) {
            throw new IllegalArgumentException("Неверный параметр типа фигуры");
        }
        figureType = FigureType.valueOf(str);
    }

    private ArrayList<String> getAllFigureType() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (FigureType figure : FigureType.values()) {
            arrayList.add(figure.toString());
        }
        return arrayList;
    }

    private void checkQuantityLineInFile(File file) throws IOException {
        try (Scanner scanner = new Scanner(file)) {
            if (quantityLineInFile(scanner) != figureType.quantityParameters) {
                throw new IOException("Некоректное число строк в файле");
            }
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
    }

        private int quantityLineInFile(Scanner scanner){
            int count = 0;
            while (scanner.hasNext()) {
                scanner.nextLine();
                count++;
            }
            return count;
        }


        public FigureType getFigureType () {
            return figureType;
        }

        public HashMap<Enum, Double> getFigureParameters () {
            return figureParameters;
        }

        private void setFigureParameters () {
            fillFigureInputParameters();
        }

        private void fillFigureInputParameters () {
            switch (figureType) {
                case CIRCLE:
                    fillCircleInputParameters();
                    break;
                case SQUARE:
                    fillSquareInputParameters();
                    break;
                case RECTANGLE:
                    fillRectangleInputParameters();
                    break;
            }
        }

        private void fillCircleInputParameters () {
            figureParameters.put(FigureInputParameters.RADIUS, readFigureParameter());
        }

        private void fillSquareInputParameters () {
            figureParameters.put(FigureInputParameters.FIRST_SIDE, readFigureParameter());
        }

        private void fillRectangleInputParameters () {
            double firstParameter = readCorrectParameter();
            double secondParameter = readFigureParameter();
            if (firstParameter == secondParameter) {
                figureType = FigureType.SQUARE;
                figureParameters.put(FigureInputParameters.FIRST_SIDE, firstParameter);
            } else {
                figureParameters.put(FigureInputParameters.FIRST_SIDE, firstParameter);
                figureParameters.put(FigureInputParameters.SECOND_SIDE, secondParameter);
            }
        }


        private double readFigureParameter () {

            return readCorrectParameter();
        }

        private double readCorrectParameter () {
            double value = 0;
            try {
                value = Double.parseDouble(bufferedReader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Неверный символ в файле или пустая строка");
            }
            if (value < 0) {
                throw new IllegalArgumentException("отрицательное значение в файле");
            }
            return value;
        }

    }
