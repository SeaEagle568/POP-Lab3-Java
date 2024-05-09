package ua.oleksiienko;

public class Consumer implements Runnable {
    private final int itemsNeeded;
    private final Storage storage;
    private final String name;

    public Consumer(int itemsNeeded, String name, Storage storage) {
        this.storage = storage;
        this.name = name;
        this.itemsNeeded = itemsNeeded;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < itemsNeeded; i++) {
                storage.retrieveItem(name);
            }
        } catch (InterruptedException e) {
            System.out.println("Consumer was interrupted while waiting");
            Thread.currentThread().interrupt();
        }
    }
}
