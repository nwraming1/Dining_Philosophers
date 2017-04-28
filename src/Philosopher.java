import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by nathanraming on 4/17/17.
 */
public class Philosopher implements PhilosopherInterface, Runnable
{

    protected int id;

    protected State state;

    private ThreadLocalRandom random;

    protected boolean log;

    protected int waitTime;

    private int eatingTimes = 0;

    protected boolean running = true;

    protected static Condition[] chopsticks;

    protected static ReentrantLock lock = new ReentrantLock();

    protected static final Condition AVALIABLE = lock.newCondition();

    protected static final Condition INUSE = lock.newCondition();

    protected static final int SLEEPMAX = 5000;

    protected int leftC;

    protected int rightC;


    public Philosopher(boolean log, int id)
    {
        this.id = id;
        leftC = id;
        rightC = (id + 1) % DINERS;
        this.log = log;
        eatingTimes = 0;
        state = State.THINKING;

        if(chopsticks == null)
        {
            chopsticks = new Condition[DINERS];
            for(int i = 0; i < chopsticks.length; i++)
            {
                chopsticks[i] = AVALIABLE;
            }
        }
    }

    /**
     * Method where thread will "Sleep"?
     */
    public void think(int time)
    {

    }

    /**
     * Method where the philosopher will eat?
     */
    public void eat()
    {

    }

    /**
     * This method will attempt to take the chopsticks if they are avaliable?
     */
    @Override
    public void takeChopsticks() {

    }

    /**
     * This method will put chopsticks back in place if any are held by philosopher
     */
    @Override
    public void replaceChopsticks() {

    }

    @Override
    public void run(){
        random = ThreadLocalRandom.current();
        while(running)
        {
            think(random.nextInt(waitTime));

        }
    }
}

enum State
{
    THINKING("Thinking", -1),
    EATING("Eating", 1),
    HUNGRY("Hungry", 0);

    private String name;

    private int code;

    private State(String name, int val)
    {
        this.name = name;
        this.code = val;
    }

    public String getName()
    {
        return name;
    }

    public int getCode()
    {
        return code;
    }
}
