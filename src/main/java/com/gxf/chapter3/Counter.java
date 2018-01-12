package com.gxf.chapter3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 58 on 2018/1/12.
 */
public class Counter {
    private int i = 0;
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    //main
    public static void main(String[] args) throws InterruptedException {
        final Counter cas = new Counter();
        List<Thread> ts = new ArrayList<Thread>();
        long startTime = System.currentTimeMillis();
        for(int j = 0; j < 100; j ++){
            //初始化100个线程
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i = 0; i < 10000; i++){
                        cas.count();
                        cas.safeCount();
                    } //for
                }// run
            });
            ts.add(t);
        } //for thread init

        //start thread
        for(Thread t: ts){
            t.start();
        }
        //wait all thread finish
        for(Thread t : ts){
            t.join();
        }
        //print result
        System.out.println("cas.i: " + cas.i);
        System.out.println("cas.atomicInteger.get(): " + cas.atomicInteger.get());
        System.out.println("System.currentTimeMillis() - startTime: " + (System.currentTimeMillis() - startTime));
    } //main

    //使用CAS实现安全计数
    private void safeCount(){
        for( ; ; ){
            int i = atomicInteger.get();
            boolean suc = atomicInteger.compareAndSet(i, ++i);
            if(suc){
                break;
            } //if
        }  //for
    }

    //非线程安全计数器
    private void count(){
        i ++;
    }
}
