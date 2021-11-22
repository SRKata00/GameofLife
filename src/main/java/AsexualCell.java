public class AsexualCell extends Cell{

    public AsexualCell()
    {
        id="A"+nr.incrementAndGet();
    }

    @Override
    public void Starve() {

    }

    @Override
    public void Divide() {

    }

    @Override
    public boolean CanDivide() {
        return false;
    }
}
