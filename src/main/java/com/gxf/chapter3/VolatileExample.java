package com.gxf.chapter3;

/**
 * Created by GuanXF on 2018/1/13.
 */
public class VolatileExample {
    public static void main(String[] args) {
        VolatileExample volatileExample = new VolatileExample();
        WriteTask writeTask = new WriteTask(volatileExample);
        ReadTask readTask = new ReadTask(volatileExample);
        Thread w = new Thread(writeTask);
        Thread r = new Thread(readTask);
        w.start();
        r.start();
    }

    int a = 0;
    volatile boolean flag = false;

    public void write(){
        a = 1;
        flag = true;
    }

    public void read(){
        if(flag){
            int i = a;
            System.out.println("i = " + i);
        }
    }
}
//WriteTask
class WriteTask implements Runnable{
    private VolatileExample volatileExample;

    public WriteTask(VolatileExample volatileExample) {
        this.volatileExample = volatileExample;
    }

    @Override
    public void run() {
        volatileExample.write();
    }
}

//Read Task
class ReadTask implements Runnable{
    private VolatileExample volatileExample;

    public ReadTask(VolatileExample volatileExample) {
        this.volatileExample = volatileExample;
    }

    @Override
    public void run() {
        volatileExample.read();
    }
}
