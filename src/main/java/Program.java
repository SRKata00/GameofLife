import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Program {
    static ThreadLocalRandom random;
    static int nutritions=10;
    static Object nutritionLock = new Object();

    public static void main (String[] args)
    {
        ConcurrentLinkedQueue<Cell> cells = new ConcurrentLinkedQueue<Cell>();
        for (int i = 0; i < 3; i++) {
            cells.add(new SexualCell());
            cells.add(new AsexualCell());
        }
    }
}
