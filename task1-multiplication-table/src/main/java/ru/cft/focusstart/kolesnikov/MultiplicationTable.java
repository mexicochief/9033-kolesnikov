package ru.cft.focusstart.kolesnikov;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MultiplicationTable {
    public static void main(String[] args) {
        int tableSize = getTableSize();
        int cellSize = getCellSize(tableSize);

        buildTable(tableSize, cellSize);
    }


    private static int getTableSize() {

        try {
            return readFromConsole();
        } catch (InputMismatchException e) {
            System.out.println("Должно быть целое положительное число");
            return getTableSize();
        }

    }

    private static int readFromConsole() {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        if (size < 0) throw new InputMismatchException();
        return size;
    }


    private static int getCellSize(int size) {
        String temp = String.valueOf(size * size);
        return temp.length();
    }


    private static void buildTable(int tablesize, int sqaresize) {
        for (int i = 1; i <= tablesize; i++) {
            for (int j = 1; j < tablesize; j++) {
                int tableElement = j * i;
                String resultString = convertToCell(tableElement, sqaresize);
                System.out.printf("%s%s",resultString, "|");
            }
            int lastElement = tablesize * i;
            System.out.println(convertToCell(lastElement, sqaresize)); // добавить последнюю клетку(без |)
            if (i != tablesize) System.out.println(createLine(tablesize, sqaresize)); // вывести разделитель

        }

    }


    private static String convertToCell(int number, int maxNumberLen) {
        String res = String.valueOf(number);
        int temp = maxNumberLen;
        String str;
        str = String.format("%" + temp + "s", res);

        return str;
    }


    private static String createLine(int size, int len) {
        StringBuilder stringBuilder = new StringBuilder();
        String lineElement = String.format("%" + len + "s", "-");
        lineElement = lineElement.replace(" ", "-");

        for (int i = 0; i < size - 1; i++) {
            stringBuilder.append(lineElement);
            stringBuilder.append("+");
        }
        stringBuilder.append(lineElement); // добавить последний элемент строки(без + )

        return String.valueOf(stringBuilder);

    }

}

