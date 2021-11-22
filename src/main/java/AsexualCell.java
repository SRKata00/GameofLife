public class AsexualCell extends Cell{

    public AsexualCell()
    {
        id="A"+nr.incrementAndGet();
    }

    @Override
    public void Live() {

    }

    @Override
    public void Starve() {

    }

    @Override
    public void Divide() {

    }

    @Override
    public boolean CanDivide() {
        if (nrOfEat>=10)
        {
            return true;
        }
        return false;
    }
}
