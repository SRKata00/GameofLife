import java.util.concurrent.atomic.AtomicInteger;

public abstract class Cell {
    static AtomicInteger nr= new AtomicInteger(0);
    String id;
    cellState state = cellState.Ok;
    final int T_full=5;
    final int T_starve=2;//Program.random.nextInt(1,6);
    int timeToHungry=T_full;
    int timeToDie=timeToHungry+T_starve;
    int nrOfEat=0;

    public abstract void Live();
    protected abstract void Starve();
    public abstract void Divide();
    protected abstract boolean CanDivide();
    protected void Die()
    {
        Eat();
        if(timeToDie==0)
        {
            nrOfEat++;
            //TODO set times
            timeToHungry=5;
            timeToDie=5;
        }
        state=cellState.Dead;
    }
    protected void Eat()
    {
        if(canEat())
        {
            nrOfEat++;
            timeToHungry=T_full;
            timeToDie=timeToHungry+T_starve;
            state=cellState.Ok;
        }
    }
    private boolean canEat()
    {
        synchronized (Program.nutritionLock)
        {
            if (Program.nutritions>0)
            {
                Program.nutritions--;
                return true;
            }
            return false;
        }
    }

    @Override
    public String toString() {
        return "Cell{" +
                "#" + id + " is " + state +
                ", already eat " + nrOfEat + "times}";
    }
}
