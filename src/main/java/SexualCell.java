public class SexualCell extends Cell{

    public SexualCell()
    {
        id="S"+nr.incrementAndGet();
        state=cellState.Hungry;
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
