package ru.cft.focusstrat.kolesnikov;

import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Store {
    private int storeSize;
    private ConcurrentLinkedQueue<String> queue;

    Store(int storeSize) {
        queue = new ConcurrentLinkedQueue<>();
        this.storeSize = storeSize;
    }

    synchronized public void put(String product, Logger log) throws InterruptedException {
        if (queue.size() == storeSize) {
            wait();
        } else {
            queue.add(product);
            log.info("Продукт " + product + " " + "Прибыл на склад");
            notifyAll();
        }
    }

    synchronized public String remove(Logger log) throws InterruptedException {
        while (queue.size() == 0) {
            wait();
        }
        notifyAll();
        log.info("Продукт " + queue.peek() + " убыл со склада");
        return queue.remove();
    }
}
