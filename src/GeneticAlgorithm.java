import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class GeneticAlgorithm
{
    private final int length, width, numThreads, stations;
    private ExecutorService threadPool;
    private volatile Factory solution;
    private volatile int fitness;
    private ReentrantLock fitnessLock = new ReentrantLock();
    private Exchanger<Factory> factoryExchanger = new Exchanger<>();

    private static final int AMOUNT_OF_ITERATIONS = 50;

    GeneticAlgorithm(int length, int width, int numThreads, int stations)
    {
        this.length = length;
        this. width = width;
        this.numThreads = numThreads;
        this.stations = stations;
        solution = new Factory(length, width, stations);
        fitness = solution.fitness();
    }


    public void run()
    {
        //Create the thread pool
        threadPool = Executors.newFixedThreadPool(numThreads);

        //Execute the task for each thread.
        for(int i = 0; i < numThreads; i++)
        {
            threadPool.execute(geneticTask(i));
        }
    }

    private Runnable geneticTask(int id)
    {
        Runnable runnable = () -> {
            //Setup for the runnable
            System.out.println("Thread " + id + " started");
            final CountDownLatch latch = new CountDownLatch(numThreads);
            Factory temp = new Factory(length, width, stations);
            int counter = 0;

            //Now iterate for a set amount to find the best solution.
            for(int i = 0; i < AMOUNT_OF_ITERATIONS; i++)
            {
                //Try a mutation
                temp.mutation();
                compareSolutions(temp);

                //If we are in a multiple of 10 offer up the solution.
                if(i % 10 == 0)
                {
                    try{
                        System.out.println("Exchanging " + id + "'s solution.");
                        temp = factoryExchanger.exchange(temp);
                    }catch(InterruptedException e)
                    {
                        System.out.println("Interrupted");
                    }
                }

                latch.countDown();
            }
            System.out.println("Thread " + id + " finsihed");
        };
        return runnable;
    }


    private void compareSolutions(Factory toCompare)
    {
        fitnessLock.lock();
        try {
            if (toCompare.fitness() > fitness) {
                solution = toCompare;
                fitness = toCompare.fitness();
                System.out.println("New fitness is " + fitness);
                SolutionGrid sg = new SolutionGrid(length, width, 600 / length, solution);
                JFrame solution = new JFrame();
                solution.getContentPane().add(sg);
                solution.pack();
                solution.setVisible(true);
            }
        }finally
        {
            fitnessLock.unlock();
        }
    }
}
