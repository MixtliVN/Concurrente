package kas.concurrente;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class BackoffLock implements Lock {

    private AtomicBoolean state = new AtomicBoolean(false);
    private static final int MIN_DELAY = 1;
    private static final int MAX_DELAY = 1000;

    public void lock() {
        Backoff backoff = new Backoff(MIN_DELAY, MAX_DELAY);
        while (true) {
            while (state.get()) {
            }
            if (!state.getAndSet(true)) {
                return;
            } else {
                try {
                    backoff.backoff();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void unlock() {
        state.set(false);
    }


}

class Backoff {

    final int minDelay, maxDelay;
    int limit;
    final Random random;

    public Backoff(int min, int max) {
        minDelay = min;
        maxDelay = max;
        limit = minDelay;
        random = new Random();
    }

    public void backoff() throws InterruptedException {
        int delay = random.nextInt(limit);
        limit = Math.min(maxDelay, 2 * limit);
        Thread.sleep(delay);
    }

    
}
