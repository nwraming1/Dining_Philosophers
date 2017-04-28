import javax.management.monitor.Monitor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;

/**
 * Created by nathanraming on 4/17/17.
 */
public class Meal
{
    protected static Chopstick[] chopsticks;

    protected static Philosopher[] philosophers;

    private static final int DINERS = 5;

    public static void main(String args[])
    {
        chopsticks = new Chopstick[DINERS];
        philosophers = new Philosopher[DINERS];
        for(int i = 0; i < chopsticks.length; i++)
        {
            Chopstick chopstick = new Chopstick();
            chopsticks[i] = chopstick;
        }

        ExecutorService exec = Executors.newFixedThreadPool(DINERS);
        try {

            for (int i = 0; i < DINERS; i++) {
                boolean log = Boolean.parseBoolean(args[1]);
                Philosopher philosopher = new Philosopher(log, i);
                philosopher.leftC = chopsticks[i];
                philosopher.rightC = chopsticks[i+1 % DINERS];
                philosophers[i] = philosopher;
                exec.execute(philosophers[i]);
            }

            int sleepTime = Integer.parseInt(args[0]);
            Thread.sleep(sleepTime);

            for(int i = 0;i < DINERS; i++)
            {
                philosophers[i].eatStatPrint();
            }
            exec.shutdown();

        }
        catch(InterruptedException ie)
        {

        }


    }
}
