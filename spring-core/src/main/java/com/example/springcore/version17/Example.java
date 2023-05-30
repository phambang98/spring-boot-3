package com.example.springcore.version17;


public class Example {
    public static class Counter {
        long count = 0;

        public void add(long value, String name) {

            synchronized (name) {
                this.count += value;
                System.out.println(count);
            }


        }
    }

    public static class CounterThread extends Thread {

        protected Counter counter = null;

        public CounterThread(Counter counter) {
            this.counter = counter;
        }

        public void run() {
            for (int i = 0; i < 10; i++) {
                counter.add(i, "i");
            }
        }
    }

    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread threadA = new CounterThread(counter);
        Thread threadB = new CounterThread(counter);

        threadA.start();
        threadB.start();
    }
}