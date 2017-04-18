import java.util.concurrent.Callable;

/**
 * Created by nathanraming on 4/17/17.
 */
public class Philosopher implements PhilosopherInterface, Callable
{


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
    public Object call() throws Exception {
        return null;
    }
}
