import java.util.concurrent.ThreadLocalRandom;

public class SexualCell extends Cell{

    public Object divideLock = new Object();
    private SexualCell pair=null;

    public SexualCell()
    {
        id="S"+nr.incrementAndGet();
        state=cellState.Hungry;
    }

    public SexualCell(String parent)
    {
        //initial infometata.
        id=parent+"-"+nr.incrementAndGet();
        state=cellState.Hungry;
    }

    @Override
    public synchronized void divide() {
        //Dupa ce a mincat de minim 10 ori, o celula se va inmulti inainte sa i se faca din nou foame.
            if (canDivide()) { //another cell is ready to divide and waits for pair
                String babyid=id;
                while(pair!=null)
                {
                    synchronized (pair.divideLock) {
                        pair.divideLock.notify();
                    }
                    babyid+="+"+pair.id;
                    pair = null;
                }

                divided();
                SexualCell babyCell = new SexualCell(babyid);
                Program.cells.add(babyCell);
                Thread babyThread = new Thread() {
                    public void run() {
                        babyCell.live();
                    }
                };
                Program.threadList.add(babyThread);
                babyThread.start();
            } else { // check if there is another sexual cell
                boolean b = false;
                for (Cell c : Program.cells) //can be optimized
                {
                    if ((c != this) && (c instanceof SexualCell) && (c.state != cellState.Dead)) {
                        b = true;
                    }
                }
                if (b) {
                    try {
                        synchronized (divideLock) {
                            divideLock.wait();
                        }
                        divided();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else //no more sexual cells
                {
                    state = cellState.Ok;
                }
            }
    }

    private void divided()
    {
        nrOfEat=0;
        state=cellState.Ok;
    }

    @Override
    protected boolean canDivide() {
        synchronized (Program.divideLock) {
            if ((nrOfEat >= T_divide) && !Program.sCellToDivide.isEmpty())
            {
                pair=Program.sCellToDivide.remove();
            }
            else
            {
                pair=null;
                Program.sCellToDivide.add(this);
            }
        }
        return (pair!=null);
    }

    @Override
    protected void die() {
        state = cellState.Dead;
        int food = ThreadLocalRandom.current().nextInt(1, 6);
        synchronized (Program.nutritionLock) {
            Program.nutritions += food;
        }
        Program.cells.remove(this);
        synchronized (Program.deadCells) {
            Program.deadCells += "  " + id;
        }
        synchronized (Program.divideLock) { //to avoid deadlock, notify the waiting cell at the dead of last cell
            if (!Program.sCellToDivide.isEmpty())
            {
                int i=0;
                for (Cell c: Program.cells) {
                    if(c instanceof SexualCell)
                        i++;
                }
                if(i==0)
                {
                    SexualCell c = Program.sCellToDivide.remove();
                    synchronized (c.divideLock)
                    {
                        c.divideLock.notify();
                    }
                }

            }
        }
    }
}
