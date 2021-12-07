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

    /*public String getId()
    {
        return id;
    }*/

    @Override
    public synchronized void divide() {
        if (canDivide())
        {
            notify();
            divided();
            SexualCell babyCell = new SexualCell(this.id);
            Program.cells.add(babyCell);
            Thread babyThread = new Thread(){public void run(){ babyCell.live(); } };
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
                    divided();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else //no more sexual cells
            {
                state=cellState.Ok;
            }

        }
    }

    private void divided()
    {
        Program.sCellToDivide.remove(this);
        nrOfEat=0;
        state=cellState.Ok;
    }

    @Override
    public boolean canDivide() {
        Program.sCellToDivide.remove(this); //try if contains
        return ((nrOfEat>=10)&& !Program.sCellToDivide.isEmpty());
    }
}
