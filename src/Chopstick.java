import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Nathan W. Raming and Justin Mechanye.
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
    /** The lock variable for protecting the critical section.*/
    protected static ReentrantLock lock = new ReentrantLock();
    /** This condition variable is used to signal that a chopstick is available
     * */
    protected static final Condition AVALIABLE = lock.newCondition();
    /** This condition variable is used to signal awaits while chopsticks
     * are in use.*/
    protected static final Condition INUSE = lock.newCondition();
    /** This boolean is true if a chopstick is in use.*/
    protected AtomicBoolean inUse;

    /**
     * This constructs a chopstick in which a chopstick is not currently
     * being used.
     */
    public Chopstick()
    {
        inUse = new AtomicBoolean();
        inUse.getAndSet(false);
    }

    /**
     * This method locks as the critical section and checks to see if
     * the chopstick is in use, If its not then it is picked up.
     */
    protected void getChopstick()
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
                System.out.println("Thread Died! Exiting...");
                System.exit(1);
            }
        }
        lock.unlock();
    }

    /**
     * This locks the critical section to set down the chopstick then
     * opens and signals the other processes that there is a free chop stick.
     */
    protected void replaceChopstick()
    {
        lock.lock();

        inUse.getAndSet(false);
        INUSE.signalAll();

        lock.unlock();
    }
}
