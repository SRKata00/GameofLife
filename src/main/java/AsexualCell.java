public class AsexualCell extends Cell{

    public AsexualCell()
    {
        id="A"+nr.incrementAndGet();
    }
    public AsexualCell(String parent)
    {
        id=parent+"-"+nr.incrementAndGet();
    }

    @Override
    protected void divide() {
        nrOfEat=0;
        state=cellState.Ok;
        babyCell();
        babyCell();
    }

    private void babyCell() {
        AsexualCell babyCell = new AsexualCell(this.id);
        Program.cells.add(babyCell);
        Thread babyThread = new Thread(){public void run(){ babyCell.live(); } };
        Program.threadList.add(babyThread);
        babyThread.start();
    }
}
