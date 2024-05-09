package ua.oleksiienko;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class Storage {
    private final Semaphore isNotOccupied;
    private final Semaphore isNotEmpty;
    private final Semaphore isNotFull;
    private final ConcurrentLinkedQueue<Item> queue = new ConcurrentLinkedQueue<>();

    public Storage(int capacity, int occupancy, boolean fair) {
        this.isNotFull = new Semaphore(capacity, fair);
        this.isNotEmpty = new Semaphore(0, fair);
        this.isNotOccupied = new Semaphore(occupancy, fair);
    }

    public void putItem(Item item) throws InterruptedException {
        isNotFull.acquire();
        isNotOccupied.acquire();
        queue.add(item);
        System.out.printf("Producer %s has provided an item %s\n", item.producedBy(), item.serialNumber());
        isNotEmpty.release();
        isNotOccupied.release();
    }
    public Item retrieveItem(String asker) throws InterruptedException {
        Item item;
        isNotEmpty.acquire();
        isNotOccupied.acquire();
        item = queue.poll();
        System.out.printf("Consumer %s has received an item %s\n", asker, item.serialNumber());
        isNotFull.release();
        isNotOccupied.release();
        return item;
    }
}
