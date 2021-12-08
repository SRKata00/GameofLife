import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Program {
    //static ThreadLocalRandom random=new ThreadLocalRandom();
    static int nutritions=29;
    static Object nutritionLock = new Object();
    static long startTime = System.currentTimeMillis();
    static ConcurrentLinkedQueue<Cell> cells;
    static ConcurrentLinkedQueue<Cell> sCellToDivide;
    static ArrayList<Thread> threadList;

    public static void main (String[] args)
    {
        sCellToDivide = new ConcurrentLinkedQueue<Cell>();
        cells = new ConcurrentLinkedQueue<Cell>();
        for (int i = 0; i < 3; i++) {
            cells.add(new SexualCell());
            //cells.add(new AsexualCell());
        }
        threadList = new ArrayList<>();
        for (Cell c : cells)
        {
            threadList.add(new Thread(){public void run(){ c.live(); } });
        }
        Thread consWriterThread = new Thread(){public void run()
        {
            boolean needed=true;
            while(needed)
            {
                writeState();
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                needed=false;
                for (Thread t:threadList)
                {
                    if(t.isAlive())
                        needed=true;
                }
            }
        } };
        for (Thread t:threadList)
        {
            t.start();
        }
        consWriterThread.start();
        //stopWatch.stop();
    }

    private static void writeState()
    {long time = System.currentTimeMillis()-startTime;
        write("\nTime: "+time/1000);
        write("Number of nutritions: "+nutritions);
        write("Cell to divide:" + sCellToDivide.stream().count());
        write("Cells:");
        for (Cell c: cells){write(c.toString());}
    }
    public static synchronized void write(String text)
    {
        System.out.println(text);
    }
}
