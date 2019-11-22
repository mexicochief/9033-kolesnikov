package ru.cft.focusstrat.kolesnikov;

import org.apache.log4j.Logger;

public class Producer implements Runnable {
    private Store store;
    private Thread t;
    private static final Logger LOG = Logger.getLogger("Производитель");
    private int workTime;

    Producer(Store store, int id, int workTime) {
        this.store = store;
        this.workTime = workTime;
        t = new Thread(this);
        t.setName(String.valueOf(id));
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Product product = new Product();
                Thread.sleep(workTime);
                LOG.info("Продукт " + product.getId() + " произведен");
                store.put(product, LOG);
            } catch (InterruptedException e) {
                LOG.error("Выполнение прерванно");
            }
        }
    }

    public void start() {
        t.start();
    }
}
