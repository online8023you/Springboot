package com.demo.thread;

/**
 * @author Dawn
 * @ClassName com.demo.thread.RunnableDemo
 * @Description
 * @date 2019/9/24 9:25
 */


public class RunnableDemo implements Runnable {

    private Thread t;
    private String threadName;

    public RunnableDemo(String name) {
        threadName = name;
        System.out.println("Creating " + threadName);
    }


    @Override
    public void run() {
        System.out.println("Running " + threadName);
        try {
            for (int i = 4; i > 0; i--) {
                System.out.println("Thread:" + threadName + "," + i);
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread:" + threadName + "interrupted.");
        }
        System.out.println("Thread" + threadName + "exiting.");
    }

    public void start() {
        System.out.println("Starting" + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

}

