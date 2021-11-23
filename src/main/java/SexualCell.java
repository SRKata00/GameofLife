import java.util.ArrayList;

public class SexualCell extends Cell{

    public SexualCell()
    {
        id="S"+nr.incrementAndGet();
        state=cellState.Hungry;
    }

    @Override
    public void Divide() {
        if (CanDivide())
        {
            //TODO make dividing - send sign for a sexual cell who wants to divide
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
                //TODO wait for sign
            }
            else
            {
                state=cellState.Ok;
            }
            Program.sCellToDivide.add(this);
        }
    }

    @Override
    public boolean CanDivide() {
        boolean b=false;
        if (Program.sCellToDivide.isEmpty())
        {
            //TODO choose a cell and check if this is in the ArrayList
        }
        else
        {
            b=true;
        }
        return ((nrOfEat>=10)&& b);
    }
}
