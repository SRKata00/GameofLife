import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

@SuppressWarnings("BusyWait")
public abstract class Cell {
    static AtomicInteger nr= new AtomicInteger(0);
    String id;
    cellState state = cellState.Ok;
    final int T_full=5;
    final int T_starve=2;
    //int timeToHungry=T_full;
    //int timeToDie=timeToHungry+T_starve;
    int nrOfEat=0;

    protected abstract void Divide();
    protected boolean CanDivide(){
        return nrOfEat >= 10;
    }
    public void Live()
    {
        while (state!=cellState.Dead)
        {
            switch (state)
            {
                case Ok -> {
                    try {
                        sleep(T_full*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case Hungry -> {
                    int t=T_starve;
                    while ((state==cellState.Hungry)&&(t>0))
                    {
                        Starve();
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        t--;
                    }
                    if(t==0)
                    {
                        Die();
                        nrOfEat=-1;
                    }
                    break;
                }
                case wantDivide -> {
                    Divide();
                    break;
                }
                default -> throw new IllegalStateException("Unexpected value: " + state);
            }
        }
    }
    protected void Die()
    {
        state=cellState.Dead;
        int food = Program.random.nextInt(1,6);
        synchronized (Program.nutritionLock)
        {
            Program.nutritions+=food;
        }
    }
    protected void Starve()
    {
        if(canEat())
        {
            nrOfEat++;
            //timeToHungry=T_full;
            //timeToDie=timeToHungry+T_starve;
            state=cellState.Ok;
        }
        if (nrOfEat==10)
            state=cellState.wantDivide;
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
