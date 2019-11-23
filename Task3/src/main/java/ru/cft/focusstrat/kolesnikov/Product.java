package ru.cft.focusstrat.kolesnikov;

public class Product {
    private static int instanceCount = 0;
    private int id;

    Product(){
        id = instanceCount++;
    }

    int getId() {
        return id;
    }
}
