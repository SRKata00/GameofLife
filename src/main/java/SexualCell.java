import java.awt.*;
import java.util.ArrayList;

public class SexualCell extends Cell{

    public SexualCell()
    {
        id="S"+nr.incrementAndGet();
        state=cellState.Hungry;
    }

    public SexualCell(String parent)
    {
        id=parent+"-"+nr.incrementAndGet();
        state=cellState.Hungry;
    }

    public String getId()
    {
        return id;
    }

    @Override
    public synchronized void Divide() {
        if (CanDivide())
        {
            notify();
            Divided();
            SexualCell babyCell = new SexualCell();
            Program.cells.add(babyCell);
            Thread babyThread = new Thread(){public void run(){ babyCell.Live(); } };
            Program.threadList.add(babyThread);
            babyThread.start();
        }
        else
        {
            boolean b=false;
            for(Cell c:Program.cells) //TODO: can be optimized
            {
                if ((c!=this)&&(c instanceof SexualCell) && (c.state!=cellState.Dead))
                {
                    b=true;
                }
            }
            if(b)
            {
                Program.sCellToDivide.add(this);
                try {
                    wait();
                    Divided();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                state=cellState.Ok;
            }

        }
    }

    private void Divided()
    {
        Program.sCellToDivide.remove(this);
        nrOfEat=0;
        state=cellState.Ok;
    }

    @Override
    public boolean CanDivide() {
        Program.sCellToDivide.remove(this); //try if contains
        return ((nrOfEat>=10)&& !Program.sCellToDivide.isEmpty());
    }
}
