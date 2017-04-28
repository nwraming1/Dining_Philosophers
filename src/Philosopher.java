import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
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
        if(log){
            try {
                PrintStream out = new PrintStream(new File("log.txt"));
                System.setOut(out);
            }catch(FileNotFoundException fnfe){
                System.out.println("Can't find file");
            }
        }
    }

    /**
     * Method where thread will "Sleep"?
     */
    public void think(int time) throws InterruptedException
    {
        try{
            state = State.THINKING;
            printStatus();
            Thread.sleep(time);
            state = State.HUNGRY;
            printStatus();
        }
        catch(InterruptedException ie)
        {
            throw new InterruptedException(ie.getMessage());
        }
    }

    /**
     * Method where the philosopher will eat?
     */
    public void eat(int time)
    {
        try {
            takeChopsticks();
            state = State.EATING;
            printStatus();
            Thread.sleep(time);
            replaceChopsticks();
        }
        catch(InterruptedException ie)
        {
            System.out.println("Thread Died! Exiting...");
            System.exit(1);
        }
    }

    /**
     * This method will attempt to take the chopsticks if they are avaliable?
     */
    @Override
    public void takeChopsticks()
    {
        leftC.getChopstick();
        rightC.getChopstick();
    }

    /**
     * This method will put chopsticks back in place if any are held by philosopher
     */
    @Override
    public void replaceChopsticks() {
        leftC.replaceChopstick();
        rightC.replaceChopstick();
    }

    @Override
    public void run(){
        random = ThreadLocalRandom.current();
        while(running)
        {
            try {
                think(random.nextInt(waitTime));
                eat(random.nextInt(waitTime));
            }
            catch(InterruptedException ie)
            {
                System.out.println("Thread " + id + " was interrupted! Exiting...");
                System.exit(1);
            }
        }
    }

    public void eatStatPrint()
    {
        System.out.println("Philosopher " + id + " ate " + eatingTimes + " times.");
    }

    private void printStatus()
    {
        System.out.println("Philosopher " + id + " is " + state.getState());
    }

    protected void setRunningFalse()
    {
        running = false;
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
