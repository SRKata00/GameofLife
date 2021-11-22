import java.util.concurrent.ConcurrentLinkedQueue;

public class Program {
    public static void main (String[] args)
    {
        int nutritions;
        ConcurrentLinkedQueue<Cell> cells = new ConcurrentLinkedQueue<Cell>();
        for (int i = 0; i < 3; i++) {
            cells.add(new SexualCell());
            cells.add(new AsexualCell());
        }
    }
}
