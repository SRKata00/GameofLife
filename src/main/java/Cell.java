import java.util.concurrent.atomic.AtomicInteger;

public abstract class Cell {
    static AtomicInteger nr= new AtomicInteger(0);
    String id;
    int T_full;
    int T_starve;
    int timeToHungry;
    int timeToDie;

    public abstract void Starve();
    public abstract void Divide();
    public abstract boolean CanDivide();
    public void Eat()
    {
        //TODO cell eat
    }

}
