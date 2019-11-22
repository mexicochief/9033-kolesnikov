package ru.cft.focusstrat.kolesnikov;

import org.apache.log4j.Logger;

public class Consumer implements Runnable {
    private ru.cft.focusstrat.kolesnikov.Store Store;
    private Thread t;
    private static final Logger LOG = Logger.getLogger("Потребитель");

    Consumer(ru.cft.focusstrat.kolesnikov.Store store) {
        this.Store = store;
        t = new Thread(this);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                String id = Store.remove(LOG);
                LOG.info("Продукт " + id + " потреблён");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOG.error("Выполнение прерванно");
            }
        }
    }

    public void start() {
        t.start();
    }
}
