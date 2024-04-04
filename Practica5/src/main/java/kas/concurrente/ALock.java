package kas.concurrente;

import java.util.concurrent.atomic.AtomicInteger;

public class ALock implements Lock {
    ThreadLocal<Integer> mySlotIndex = ThreadLocal.withInitial(() -> 0);
    AtomicInteger tail;
    volatile boolean[] flag;

    int size;

    public ALock(int hilos) {
        size = hilos;
        tail = new AtomicInteger(0);
        flag = new boolean[hilos];
        flag[0] = true;
    }

    @Override
    public void lock() {
        int slot = tail.getAndIncrement() % size;

        mySlotIndex.set(slot);

        while (!flag[slot]) {
        }
        Thread.yield();

    }

    @Override
    public void unlock() {
        int slot = mySlotIndex.get();
        flag[slot] = false;
        flag[(slot + 1) % size] = true;
    }

}
