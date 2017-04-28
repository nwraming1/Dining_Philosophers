/**
 * This interface gives the signatures so that a philosopher can
 * take and replace both chopsticks.  It also sets a constant for the
 * number of philosophers having a meal.
 *
 * @author Nathan Raming, Justin Mechanye
 * @date 4/28/2017.
 */
public interface PhilosopherInterface
{
    int DINERS = 5;

    void takeChopsticks();

    void replaceChopsticks();
}
