package Exercises.four;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizeThreads {

    private static SumObj obj = new SumObj();

    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 1000; i ++){
            executor.execute(new AddToSum());
        }

        executor.shutdown();

        while (!executor.isTerminated()){

            System.out.println("Sum: " + obj.getSum());
        }

    }
    private static class AddToSum implements Runnable {

        public void run(){
            obj.addToSum(1);
        }
    }

    private static class SumObj {

        private static Lock lock = new ReentrantLock();
        private int sum = 0;

        public int getSum(){
            return sum;
        }
        public synchronized void addToSum(int amount){
            int newSum = sum + amount;

            sum = newSum;
        }
        public void addToSum2(int amount){
            lock.lock();

            try {
                int newSum = sum + amount;

                sum = newSum;
            }
            catch (InterruptedException ex){

            }
            finally{
                lock.unlock();
            }
        }
    }

}
