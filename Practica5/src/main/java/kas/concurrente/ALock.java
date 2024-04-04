package kas.concurrente;

import java.util.concurrent.atomic.AtomicInteger;

public class ALock implements Lock {
    ThreadLocal<Integer> mySlotIndex = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

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
        int slot;
        while (true) {
            slot = tail.getAndIncrement() % size;
            if (flag[slot]) {
                mySlotIndex.set(slot);
                return;
            } else {
                // The lock is not available, so we need to spin
                while (!flag[slot]) {
                    // Busy wait
                }
            }
        }
    }

    @Override
    public void unlock() {
        int slot = mySlotIndex.get();
        flag[slot] = false;
        flag[(slot + 1) % size] = true;
        mySlotIndex.set(null);
    }

}
