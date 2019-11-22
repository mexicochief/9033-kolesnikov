package ru.cft.focusstrat.kolesnikov;

import org.apache.log4j.Logger;

public class Consumer implements Runnable {
    private Store Store;
    private Thread t;
    private static final Logger LOG = Logger.getLogger("Потребитель");
    private  int workTime;

    Consumer(Store store, int id,int workTime) {
        this.Store = store;
        this.workTime = workTime;
        t = new Thread(this);
        t.setName(String.valueOf(id));
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Product product = Store.remove(LOG);
                Thread.sleep(workTime);
                LOG.info("Продукт " + product.getId() + " потреблён");
            } catch (InterruptedException e) {
                LOG.error("Выполнение прерванно");
            }
        }
    }

    public void start() {
        t.start();
    }
}
