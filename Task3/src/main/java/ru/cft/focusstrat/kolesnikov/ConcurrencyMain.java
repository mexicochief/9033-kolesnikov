package ru.cft.focusstrat.kolesnikov;

public class ConcurrencyMain {
    final static int CONSUMER_QUANTITY = 5;
    final static int PRODUCER_QUANTITY = 10;
    final static int STORE_SIZE = 10;


    public static void main(String[] args) {
        Store store = new Store(STORE_SIZE);
        Product product = new Product();
        for (int i = 0; i < CONSUMER_QUANTITY; i++) {
            new Consumer(store).start();
        }
        for (int i = 0; i < PRODUCER_QUANTITY; i++) {
            new Producer(store, product).start();
        }
    }
}
