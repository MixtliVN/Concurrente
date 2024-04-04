package kas.concurrente;

import java.util.concurrent.atomic.AtomicReference;

public class MCSLock implements Lock {

    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myNode;

    public MCSLock() {
        tail = new AtomicReference<>(null);
        myNode = ThreadLocal.withInitial(QNode::new);
    }

    @Override
    public void lock() {
        QNode qnode = myNode.get();
        QNode pred = tail.getAndSet(qnode);
        if (pred != null) {
            qnode.locked = true;
            pred.next = qnode;
            // Spin until lock is acquired
            while (qnode.locked) {
                Thread.yield(); // Allow other threads to execute
            }
        }
    }

    @Override
    public void unlock() {
        QNode qnode = myNode.get();
        if (qnode.next == null) {
            if (tail.compareAndSet(qnode, null)) {
                return;
            }
            // Spin until qnode.next is set
            while (qnode.next == null) {
                Thread.yield(); // Allow other threads to execute
            }
        }
        qnode.next.locked = false;
        qnode.next = null;
    }
}

class QNode {
    boolean locked = false;
    QNode next = null;
}
