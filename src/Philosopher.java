import java.util.concurrent.Callable;

/**
 * Created by nathanraming on 4/17/17.
 */
public class Philosopher implements PhilosopherInterface, Runnable
{

    protected String name;

    protected State state;

    /**
     * Method where thread will "Sleep"?
     */
    public void think()
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
