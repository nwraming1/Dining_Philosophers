import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Nathan W. Raming
 * CS 370 : Operating Systems
 * Project 4 : Dining Philosophers
 * Class Name: Chopstick.java
 * Description: This is an Class that represents the Chopstick object. The
 * philosophers must used this "shared memory" to eat at correct times.
 *
 * @version 1.0
 * @date 28.Apr.2017
 */
public class Chopstick
{
    protected static ReentrantLock lock = new ReentrantLock();

    protected static final Condition AVALIABLE = lock.newCondition();

    protected static final Condition INUSE = lock.newCondition();

    protected AtomicBoolean inUse;

    public Chopstick()
    {
        inUse = new AtomicBoolean();
        inUse.getAndSet(false);
    }

    protected void getChopstick()throws InterruptedException
    {
        lock.lock();
        if(!inUse.get())
        {
            inUse.getAndSet(true);
            AVALIABLE.signalAll();
        }
        else
        {
            try {
                INUSE.await();
                inUse.getAndSet(true);
            }
            catch(InterruptedException ie)
            {
                throw new InterruptedException(ie.getMessage());
            }
        }
        lock.unlock();
    }

    protected void replaceChopstick()
    {
        lock.lock();

        inUse.getAndSet(false);
        INUSE.signalAll();

        lock.unlock();
    }
}
