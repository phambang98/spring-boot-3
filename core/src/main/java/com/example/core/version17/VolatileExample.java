package com.example.core.version17;

public class VolatileExample {
    private static volatile int count = 0;

    public static void main(String[] args) {
        Thread thread1 = new Thread(new PrintRunnable());
        Thread thread2 = new Thread(new PrintRunnable());
        Thread thread3 = new Thread(new PrintRunnable());
        Thread thread4 = new Thread(new PrintRunnable());
        Thread thread5 = new Thread(new PrintRunnable());
        Thread thread6 = new Thread(new PrintRunnable());

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
    }

    static class IncrementRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                count++;
                System.out.println("Incremented count to: " + count);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class PrintRunnable implements Runnable {
        @Override
        public void run() {
            int localCount = count;
            while (localCount < 5) {
                if (localCount != count) {
                    System.out.println("New count value: " + count);
                    localCount = count;
                }
            }
        }
    }
}