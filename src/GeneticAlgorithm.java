import javafx.scene.control.Label;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class GeneticAlgorithm
{
    private final int length, width, numThreads, stations;
    private ExecutorService threadPool;
    public volatile Factory solution;
    private volatile int fitness;
    private ReentrantLock fitnessLock = new ReentrantLock();
    private ReentrantLock solutionLock = new ReentrantLock();
    private ArrayList<Factory> solutionList = new ArrayList<>();
    private volatile CountDownLatch latch;
    private JFrame mainFrame;

    private static final int AMOUNT_OF_ITERATIONS = 50;

    GeneticAlgorithm(int length, int width, int numThreads, int stations, JFrame mainFrame)
    {
        this.length = length;
        this. width = width;
        this.numThreads = numThreads;
        this.stations = stations;
        solution = new Factory(length, width, stations);
        fitness = solution.fitness();
        this.latch = new CountDownLatch(numThreads);
        this.mainFrame = mainFrame;
    }


    public void run()
    {
        //Create the thread pool
        Runnable[] tasks = new Runnable[numThreads];
        threadPool = Executors.newFixedThreadPool(numThreads);
        //Fill the array with tasks
        for(int i = 0; i < numThreads; i++)
        {
            tasks[i] = geneticTask(i);
        }

        for(int j = 0; j < 100; j++)
        {
            latch = new CountDownLatch(numThreads);
            for(int k = 0; k < numThreads; k++)
            {
                threadPool.execute(tasks[k]);
            }
            try{
                latch.await();
                for(Factory temp : solutionList)
                {
                    if(temp.fitness() > fitness)
                    {
                        solution = temp;
                        fitness = temp.fitness();
                    }
                }
                System.out.println("Fitness is " + fitness);
                SolutionGrid sg = new SolutionGrid(length, width, 600 / length, solution);
                mainFrame.setLayout(new BorderLayout());
                mainFrame.getContentPane().add(new JLabel("Fitness = " + fitness), BorderLayout.SOUTH);
                mainFrame.getContentPane().add(sg);
                mainFrame.pack();
                mainFrame.setVisible(true);
            }catch(InterruptedException e)
            {
                System.out.println("Latch Interrupted");
            }
        }
        SolutionGrid sg = new SolutionGrid(length, width, 600 / length, solution);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.getContentPane().add(new JLabel("Fitness = " + fitness), BorderLayout.SOUTH);
        mainFrame.getContentPane().add(sg);
        mainFrame.pack();
        mainFrame.setVisible(true);
        System.out.println("end");
    }

    private Runnable geneticTask(int id)
    {
        Runnable runnable = () -> {
            //Setup for the runnable
            System.out.println("Thread " + id + " started");
            Factory temp = new Factory(length, width, stations);

            //Now iterate for a set amount to find the best solution.
            for(int i = 0; i < AMOUNT_OF_ITERATIONS; i++)
            {
                Factory prev = temp;
                //Try a mutation
                temp.mutation();
                if(comparePrev(prev, temp))
                {
                    temp = prev;
                }
            }
            addSolution(temp);
            latch.countDown();
            System.out.println("Thread " + id + " finsihed");
        };
        return runnable;
    }


    private boolean comparePrev(Factory previous, Factory mutated)
    {
        if(previous.fitness() > mutated.fitness())
        {
            return true;
        }
        return false;
    }

    private void addSolution(Factory temp)
    {
        solutionLock.lock();
        try{
            solutionList.add(temp);
        }
        finally{
            solutionLock.unlock();
        }
    }
}
