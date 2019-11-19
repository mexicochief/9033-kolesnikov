package ru.cft.focusstart.kolesnikov;

public class DoubleFormat {

    public String getGetRoundedValue(double value) {
        double tempValue = roundUp(value);
        String result = String.valueOf(tempValue);
        if (tempValue % 1 == 0) {
            result = String.format("%d", (long) value);
        }
        return result;
    }

    private double roundUp(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
