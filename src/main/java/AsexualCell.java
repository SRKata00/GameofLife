public class AsexualCell extends Cell{

    public AsexualCell()
    {
        id="A"+nr.incrementAndGet();
    }

    @Override
    protected void Divide() {
        nrOfEat=0;
        state=cellState.Ok;
        AsexualCell babyCell = new AsexualCell();
        Program.cells.add(babyCell);
        Thread babyThread = new Thread(){public void run(){ babyCell.Live(); } };
        Program.threadList.add(babyThread);
        babyThread.start();
    }
}
