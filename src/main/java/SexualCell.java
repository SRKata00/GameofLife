public class SexualCell extends Cell{

    public SexualCell()
    {
        id="S"+nr.incrementAndGet();
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
