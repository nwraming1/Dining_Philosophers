import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Nathan W. Raming
 * CS ___ : _________
 * Project _ : _______
 * Class Name: ________
 * Description:
 *
 * @version 1.0
 * @date 28.Apr.2017
 */
public class Chopstick
{
    protected static ReentrantLock lock = new ReentrantLock();

    protected static final Condition AVALIABLE = lock.newCondition();

    protected static final Condition INUSE = lock.newCondition();

    protected AtomicBoolean used;

    public Chopstick()
    {
        used = new AtomicBoolean();
        used.getAndSet(false);
    }
}
