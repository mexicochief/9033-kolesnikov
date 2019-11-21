package ru.cft.focusstrat.kolesnikov;

import java.util.concurrent.atomic.AtomicInteger;

public class Product {
    private AtomicInteger currentProductId = new AtomicInteger(0);

    synchronized String getAndAppend() { // тут не уверен насчёт модификатора
        return String.valueOf(currentProductId.getAndIncrement());
    }
}
