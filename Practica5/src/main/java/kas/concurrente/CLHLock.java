package kas.concurrente;

import java.util.concurrent.atomic.AtomicReference;


public class CLHLock implements Lock {

    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myNode;
    ThreadLocal<QNode> myPred;

    public CLHLock() {
        tail = new AtomicReference<QNode>(new QNode());
        myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
        myPred = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return null;
            }
        };
    }

    @Override
    public void lock() {
        QNode qnode = myNode.get();
        qnode.locked = true;
        QNode pred = tail.getAndSet(qnode);
        myPred.set(pred);
        while (pred.locked) {
        }
    }

    @Override
    public void unlock() {
        QNode qnode = myNode.get();
        qnode.locked = false;
        myNode.set(myPred.get());
    }

    class QNode {
        volatile boolean locked = false;
    }
}
