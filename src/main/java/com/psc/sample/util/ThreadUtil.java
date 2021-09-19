package com.psc.sample.util;

public class ThreadUtil {

    public static void sleep(int time, boolean isDebug){
        try {
            if(isDebug){
                System.out.println("                                 " + time +"초 sleep " + Thread.currentThread().getName());
            }
            Thread.sleep(time * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getThreadName(){
        return Thread.currentThread().getName();
    }
}
