package ua.oleksiienko;

public class Producer implements Runnable {
    private final int itemsNeeded;
    private final String name;
    private final Storage storage;

    public Producer(int itemsNeeded, String name, Storage storage) {
        this.itemsNeeded = itemsNeeded;
        this.name = name;
        this.storage = storage;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= itemsNeeded; i++) {
                storage.putItem(new Item(name, i));
            }
        } catch (InterruptedException e) {
            System.out.println("Producer was interrupted while waiting");
            Thread.currentThread().interrupt();
        }
    }
}
