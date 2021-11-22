public class SexualCell extends Cell{

    public SexualCell()
    {
        id="S"+nr.incrementAndGet();
        timeToHungry=0;
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
    public boolean CanDivide() {//TODO find pair
        if (nrOfEat>=10)
        {
            return true;
        }
        return false;
    }
}
