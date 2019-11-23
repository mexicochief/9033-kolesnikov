package ru.cft.focusstrat.kolesnikov;

public class ConcurrencyMain {
    final static int CONSUMER_QUANTITY = 5;
    final static int PRODUCER_QUANTITY = 10;
    final static int STORE_SIZE = 10;
    final static int CONSUMER_WORK_TIME = 1000;
    final static int PRODUCER_WORK_TIME = 1000;


    public static void main(String[] args) {
        Store store = new Store(STORE_SIZE);
        for (int i = 1; i < CONSUMER_QUANTITY; i++) {
            new Consumer(store, i, CONSUMER_WORK_TIME).start();
        }
        for (int i = 1; i < PRODUCER_QUANTITY; i++) {
            new Producer(store, i, PRODUCER_WORK_TIME).start();
        }
    }
}
