import javax.management.monitor.Monitor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
        if(args.length<2){
            System.out.println("Usage: <time> <'T'/'F'>");
        }
        boolean log;
        chopsticks = new Chopstick[DINERS];
        philosophers = new Philosopher[DINERS];
        for(int i = 0; i < chopsticks.length; i++)
        {
            Chopstick chopstick = new Chopstick();
            chopsticks[i] = chopstick;
        }

        ExecutorService exec = Executors.newFixedThreadPool(DINERS);
        try {
            if(args[1].toLowerCase().equals("t")) {
                log=true;
            }
            else{
                log=false;
            }

            for (int i = 0; i < DINERS; i++) {
                Philosopher philosopher = new Philosopher(log, i);
                philosopher.leftC = chopsticks[i];
                philosopher.rightC = chopsticks[i+1 % DINERS];
                philosophers[i] = philosopher;
                exec.execute(philosophers[i]);
            }

            int sleepTime = Integer.parseInt(args[0]);
            Thread.sleep(sleepTime);

            for(Philosopher philo: philosophers)
            {
                philo.setRunningFalse();
                System.out.println("Kill signal sent to Philosopher " + philo.id + ".");
            }
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
