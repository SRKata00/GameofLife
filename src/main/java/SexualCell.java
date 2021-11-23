import java.util.ArrayList;

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
    public boolean CanDivide() {
        boolean b=false;
        if (Program.sCellToDivide.isEmpty())
        {
            for(Cell c:Program.cells) //TODO: can be optimized
            {
                b=((c instanceof SexualCell) && (c.state!=cellState.Dead));
            }
        }
        else
        {
            b=true;
        }
        return ((nrOfEat>=10)&& b);
    }
}
