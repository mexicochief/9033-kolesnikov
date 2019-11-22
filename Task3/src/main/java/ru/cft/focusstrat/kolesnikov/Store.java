package ru.cft.focusstrat.kolesnikov;

import org.apache.log4j.Logger;

import java.util.LinkedList;

public class Store {
    private int storeSize;
    private LinkedList<Product> store;

    Store(int storeSize) {
        store = new LinkedList<>();
        this.storeSize = storeSize;
    }

    synchronized public void put(Product product, Logger log) throws InterruptedException {
        while (store.size() == storeSize) {
            wait();
        }
        store.add(product);
        log.info("Продукт " + product.getId() + " " + "прибыл на склад");
        notifyAll();
    }

    synchronized public Product remove(Logger log) throws InterruptedException {
        while (store.size() == 0) {
            wait();
        }
        notify();
        log.info("Продукт " + store.peek().getId() + " убыл со склада");
        return store.remove();
    }
}
