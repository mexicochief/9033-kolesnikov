package ru.cft.focusstrat.kolesnikov;

import org.apache.log4j.Logger;

public class Producer implements Runnable {
    private Store queue;
    private Thread t;
    private Product product;
    private static final Logger LOG = Logger.getLogger("Производитель");

    Producer(Store queue, Product product) {
        this.product = product;
        this.queue = queue;
        t = new Thread(this);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                String id = product.getAndAppend();
                LOG.info("Продукт " + id + " произведен");
                queue.put(id, LOG);
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
