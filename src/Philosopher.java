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



    protected static final int SLEEPMAX = 5000;

    protected Chopstick leftC;

    protected Chopstick rightC;


    public Philosopher(boolean log, int id)
    {
        this.id = id;
        this.log = log;
        eatingTimes = 0;
        state = State.THINKING;
    }

    public Philosopher(int id)
    {
        this.id = id;
        this.log = log;
        eatingTimes = 0;
        state = State.THINKING;
    }

    /**
     * Method where thread will "Sleep"?
     */
    public void think(int time) throws InterruptedException
    {
        try{
            Thread.sleep(time);
        }
        catch(InterruptedException ie)
        {
            throw new InterruptedException(ie.getMessage());
        }
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
            try {
                think(random.nextInt(waitTime));
            }
            catch(InterruptedException ie)
            {
                System.out.println("Thread " + id + " was interrupted! Exiting...");
                System.exit(1);
            }
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

    State(String state, int val)
    {
        this.name = state;
        this.code = val;
    }

    public String getState()
    {
        return name;
    }

    public int getCode()
    {
        return code;
    }
}
