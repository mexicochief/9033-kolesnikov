package ru.cft.focusstrat.kolesnikov;

import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Store {
    private int storeSize;
    private ConcurrentLinkedQueue<String> store;

    Store(int storeSize) {
        store = new ConcurrentLinkedQueue<>();
        this.storeSize = storeSize;
    }

    synchronized public void put(String product, Logger log) throws InterruptedException {
        while (store.size() == storeSize) {
            wait();
        }
        store.add(product);
        log.info("Продукт " + product + " " + "Прибыл на склад");
        notifyAll();
    }

    synchronized public String remove(Logger log) throws InterruptedException {
        while (store.size() == 0) {
            wait();
        }
        notifyAll();
        log.info("Продукт " + store.peek() + " убыл со склада");
        return store.remove();
    }
}
