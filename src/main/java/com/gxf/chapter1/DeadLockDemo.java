package com.gxf.chapter1;

/**
 * Created by GuanXF on 2018/1/11.
 * 死锁demo
 */
public class DeadLockDemo {
    private static  Object lock1 = new Object();
    private static Object lock2 = new Object();

    //Test main
    public static void main(String[] args) {
        startTask1();
        startTaak2();
    }

    private static void startTask1(){
        Thread t1 = new Thread(new Task1());
        t1.start();
    }

    private static void startTaak2(){
        Thread t2 = new Thread(new Task2());
        t2.start();
    }

    //Task1
    static class Task1 implements Runnable{
        //lock lock1 lock2
        public void run() {
            synchronized (lock1){
                try {
                    System.out.println("Task1 get lock1");
                    Thread.sleep(2000);
                    synchronized (lock2){
                        System.out.println("Task1");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //Task2
    static class Task2 implements Runnable{
        //lock lock2 lock1
        public void run() {
            synchronized (lock2){
                try {
                    System.out.println("Task2 get lock2");
                    Thread.sleep(2000);
                    synchronized (lock1){
                        System.out.println("Task2");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
