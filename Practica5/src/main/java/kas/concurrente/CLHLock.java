package kas.concurrente;

import java.util.concurrent.atomic.AtomicReference;

public class CLHLock implements Lock {
    private final AtomicReference<QNode> tail;
    private final ThreadLocal<QNode> myNode;
    private final ThreadLocal<QNode> myPred;

    public CLHLock() {
        tail = new AtomicReference<>(new QNode());
        myNode = ThreadLocal.withInitial(QNode::new);
        myPred = ThreadLocal.withInitial(() -> null);
    }

    @Override
    public void lock() {
        QNode qnode = myNode.get();
        qnode.locked = true;
        QNode pred = tail.getAndSet(qnode);
        myPred.set(pred);
        while (pred.locked) {
            Thread.yield(); 
        }
    }

    @Override
    public void unlock() {
        QNode qnode = myNode.get();
        qnode.locked = false;
        myNode.set(myPred.get());
    }

    private static class QNode {
        volatile boolean locked = false; 
    }
}