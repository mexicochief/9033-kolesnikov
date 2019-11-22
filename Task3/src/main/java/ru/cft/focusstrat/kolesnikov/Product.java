package ru.cft.focusstrat.kolesnikov;

import java.util.concurrent.atomic.AtomicInteger;

public class Product {
    private static AtomicInteger currentProductId = new AtomicInteger(1);
    private int id;

    Product(){
        id = currentProductId.getAndIncrement();
    }

    synchronized int getId() { // тут не уверен насчёт модификатора
        return id;
    }
}
