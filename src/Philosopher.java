import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Nathan W. Raming and Justin Mechanye.
 * CS 370 : Operating Systems
 * Project 4 : Dining Philosophers
 * Class Name: Philosopher.java
 * Description: The Philosopher class makes a runnable object that acts
 * as a Philosopher for the dinning Philosophers problem.
 *
 * @version 1.0
 * @date 28.Apr.2017
 */

public class Philosopher implements PhilosopherInterface, Runnable
{
    /** This represent the name of a philosopher*/
    protected int id;
    /** This is the current state of the philosopher {THINKING EATING HUNGRY}*/
    protected State state;
    /** Used to generate a random sleep time for thinking and eating*/
    private ThreadLocalRandom random;
    /** Used to set up sending output to a log file.*/
    protected boolean log;
    /** The number of times this thread has eaten.*/
    private int eatingTimes = 0;
    /** This controlls the while loop in the run() method.*/
    protected boolean running = true;
    /** This is the Maximum sleep time for a philosopher.*/
    protected static final int SLEEPMAX = 5000;
    /** This represents the left side chopstick for this Philosopher.*/
    protected Chopstick leftC;
    /** This represents the right side chopstick for this Philosopher.*/
    protected Chopstick rightC;

    /**
     * This constructs a Philosopher that will be executed as a thread.
     * @param log true if the user wanted to log the results in a file false
     *            if they want the information printed to the console.
     * @param id  The name of this philosopher.
     */
    public Philosopher(boolean log, int id)
    {
        random = ThreadLocalRandom.current();
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
     * This is the thinking time for a philosopher once the philosopher
     * has finished thinking (Maximum of 5sec) the philosopher will be
     * in a hungry state.
     * @param time The random amount of time this philosopher will think.
     * @throws InterruptedException
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
     * This method calls the takeChopsticks() method then the philosopher
     * begins eating.  After the sleep time it will then replace the
     * chop sticks.
     * @param time The random amount of time (Maximum 5sec) that
     *             the philosopher will eat.
     */
    public void eat(int time)
    {
        try {
            takeChopsticks();
            state = State.EATING;
            eatingTimes++;
            printStatus();
            Thread.sleep(time);
            replaceChopsticks();
            System.out.println("Philosopher " + id + " is full.");
            System.out.flush();
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

    /**
     * This method runs the thread philosopher.
     */
    @Override
    public void run(){
        while(running)
        {
            try {
                think(random.nextInt(SLEEPMAX));
                eat(random.nextInt(SLEEPMAX));
            }
            catch(InterruptedException ie)
            {
                System.out.println("Thread " + id + " was interrupted! Exiting...");
                System.exit(1);
            }
        }
    }

    /**
     * This prints out the number of times each Philosopher ate during a meal.
     */
    public void eatStatPrint()
    {
        System.out.println("Philosopher " + id + " ate " + eatingTimes + " times.");
        System.out.flush();
    }

    /**
     * This prints the status of the current philosopher {THINKING HUNGRY EATING}
     */
    private void printStatus()
    {
        System.out.println("Philosopher " + id + " is " + state.getState());
        System.out.flush();
    }

    /**
     * This method sets the running field to false stopping the while loop
     * in the run method.
     */
    protected void setRunningFalse()
    {
        running = false;
    }
}

enum State
{
    THINKING("Thinking"),
    EATING("Eating"),
    HUNGRY("Hungry");

    /** The name of the state.*/
    private String name;

    /**
     * This constructs a state.
     * @param state name of the state.
     */
    State(String state)
    {
        this.name = state;
    }

    /** Gets the String value representing the String */
    public String getState()
    {
        return name;
    }

}
