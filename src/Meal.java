
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Nathan W. Raming and Justin Mechanye.
 * CS 370 : Operating Systems
 * Project 4 : Dining Philosophers
 * Class Name: Meal.java
 * Description: This is the driver for the Dinning Philosophers problem.
 * This class allows the five Philosophers to have a meal.
 *
 * @version 1.0
 * @date 28.Apr.2017
 */

public class Meal
{
    /** This is a static array of Chopstick objects*/
    protected static Chopstick[] chopsticks;
    /** This is an array which holds the "philosopher" threads*/
    protected static Philosopher[] philosophers;
    /** A constant for the number of diners*/
    private static final int DINERS = 5;

    /**
     * This is the main method that runs the dinning philosophers problem.
     * @param args Command line arguments.
     * @throws InterruptedException
     */
    public static void main(String args[]) throws InterruptedException
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
            //This sets up all of the chopsticks and philosophers then executes
            //runnable.
            for (int i = 0; i < DINERS; i++) {
                Philosopher philosopher = new Philosopher(log, i);
                philosopher.leftC = chopsticks[i];
                philosopher.rightC = chopsticks[(i+1) % DINERS];
                philosophers[i] = philosopher;
                exec.execute(philosophers[i]);
            }

            int sleepTime = Integer.parseInt(args[0]);
            Thread.sleep(sleepTime * 1000);

            for(Philosopher philo: philosophers)
            {
                philo.setRunningFalse();
                System.out.println("Kill signal sent to Philosopher " + philo.id + ".");
            }



        }
        catch(InterruptedException ie)
        {
            //Do Nothing at end of code.
        }
        finally {
                Thread.sleep(10000);
            for(int i = 0;i < DINERS; i++)
            {
                philosophers[i].eatStatPrint();
            }

            exec.shutdown();
        }

    }
}
