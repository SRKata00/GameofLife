import org.hsqldb.lib.StopWatch;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Program {
    static ThreadLocalRandom random;
    static int nutritions=10;
    static Object nutritionLock = new Object();
    static StopWatch stopWatch = new StopWatch();
    static ConcurrentLinkedQueue<Cell> cells;
    static ConcurrentLinkedQueue<Cell> sCellToDivide;
    static ArrayList<Thread> threadList;

    public static void main (String[] args)
    {
        sCellToDivide = new ConcurrentLinkedQueue<Cell>();
        cells = new ConcurrentLinkedQueue<Cell>();
        for (int i = 0; i < 3; i++) {
            cells.add(new SexualCell());
            cells.add(new AsexualCell());
        }
        threadList = new ArrayList<>();
        for (Cell c : cells)
        {
            threadList.add(new Thread(){public void run(){ c.Live(); } });
        }
        Thread consWriterThread = new Thread(){public void run()
        {
            boolean needed=true;
            while(needed)
            {
                stopWatch.start();
                write("Time: "+stopWatch.elapsedTime(),true);
                write("\nCells",false);
                for (Cell c: cells){write(c.toString()+"                         ",false);}
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
        stopWatch.stop();
    }

    public static synchronized void write(String text, boolean b)
    {
        if(b) {
            char escCode=0x18;
            int row=0;
            int column =0;
            System.out.print(String.format("%c[%d;%df]", escCode, row,column));
        }
        System.out.println(text);
    }
}
