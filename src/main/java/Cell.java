import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

@SuppressWarnings("BusyWait")
public abstract class Cell {
    protected static AtomicInteger nr= new AtomicInteger(0);
    protected String id;
    protected cellState state = cellState.Ok;
    protected final int T_full=5;
    protected final int T_starve=2;
    protected final int T_divide=3;//10
    protected int nrOfEat=0;

    protected abstract void divide();
    protected boolean canDivide(){
        return nrOfEat >= T_divide;
    }
    public void live()
    {
        while (state!=cellState.Dead)
        {
            switch (state)
            {
                case Ok -> {
                    try {
                        sleep(T_full*1000);
                        state=cellState.Hungry;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case Hungry -> {
                    int t=T_starve;
                    while ((state==cellState.Hungry)&&(t>0))
                    {
                        starve();
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        t--;
                    }
                    if(t==0)
                    {
                        die();
                        nrOfEat=-1;
                    }
                    break;
                }
                case WantDivide -> {
                    divide();
                    break;
                }
                default -> throw new IllegalStateException("Unexpected value: " + state);
            }
        }
    }
    protected void die()
    {
        state=cellState.Dead;
        int food = ThreadLocalRandom.current().nextInt(1,6);
        synchronized (Program.nutritionLock)
        {
            Program.nutritions+=food;
        }
    }
    protected void starve()
    {
        if(canEat())
        {
            nrOfEat++;
            state=cellState.Ok;
        }
        if (nrOfEat==T_divide)
        {
            state=cellState.WantDivide;
        }
    }
    protected boolean canEat()
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
        return "#" + id + " is " + state +
                ", already eat " + nrOfEat + " times.";
    }
}
