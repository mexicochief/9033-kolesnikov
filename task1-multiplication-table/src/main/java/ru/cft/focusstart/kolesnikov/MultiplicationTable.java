package ru.cft.focusstart.kolesnikov;


import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;


public class MultiplicationTable {

    public static void main(String[] args) throws IOException {
        int tableSize = getTableSize(new BufferedReader(new InputStreamReader(System.in)), 32);

        String table = buildTableAsString(tableSize);
        writeToConsole(System.out, table);
    }

    public static int getTableSize(BufferedReader bufferedReader, int maxValue) throws IOException {
        System.out.println("Print integer number with range [0," + maxValue + "]");
        int tableSize = readIntFromConsole(bufferedReader);
        if (0 > tableSize || tableSize > maxValue) {
            throw new IllegalArgumentException("Wrong value, must be integer number with range [0," + maxValue + "]");
        }
        return tableSize;
    }

    private static int readIntFromConsole(BufferedReader bufferedReader) throws IOException {
        try {
            return Integer.parseInt(bufferedReader.readLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Wrong value, must be integer number");
        }
    }

    public static String buildTableAsString(int tableSize) {
        StringBuilder resultTable = new StringBuilder();
        int cellSize = getCellSize(tableSize);
        String separateLine = createSeparatorLine(tableSize, cellSize);

        for (int i = 1; i <= tableSize; i++) {
            for (int j = 1; j <= tableSize; j++) {
                int tableElement = j * i;
                String resultString = convertToCell(tableElement, cellSize);
                resultTable.append(resultString);
                if (j != tableSize) {
                    resultTable.append("|");
                }
            }
            if (i != tableSize) {
                resultTable.append(System.lineSeparator());
                resultTable.append(separateLine);
                resultTable.append(System.lineSeparator());
            }
        }
        return resultTable.toString();
    }

    private static int getCellSize(int tableSize) {
        String temp = String.valueOf(tableSize * tableSize);
        return temp.length();
    }

    private static String createSeparatorLine(int size, int len) {
        StringBuilder stringBuilder = new StringBuilder();
        String lineElement = String.format("%" + len + "s", "-");
        lineElement = lineElement.replace(" ", "-");

        for (int i = 0; i < size - 1; i++) {
            stringBuilder.append(lineElement);
            stringBuilder.append("+");
        }
        stringBuilder.append(lineElement);
        return String.valueOf(stringBuilder);
    }

    private static String convertToCell(int number, int maxNumberLen) {
        String res = String.valueOf(number);
        return String.format("%" + maxNumberLen + "s", res);
    }

    public static void writeToConsole(PrintStream printStream, String str) {
        printStream.println(str);

    }
}

