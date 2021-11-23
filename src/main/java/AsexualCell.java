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
    protected void Divide() {
        nrOfEat=0;
        state=cellState.Ok;
        BabyCell();
        BabyCell();
    }

    private void BabyCell() {
        AsexualCell babyCell = new AsexualCell(this.id);
        Program.cells.add(babyCell);
        Thread babyThread = new Thread(){public void run(){ babyCell.Live(); } };
        Program.threadList.add(babyThread);
        babyThread.start();
    }
}
