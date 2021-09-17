package com.psc.sample;

public class ThreadUtil {

    public static void Sleep(int time, boolean isDebug){
        try {
            if(isDebug){
                System.out.println("                                 " + time +"초 sleep " + Thread.currentThread().getName());
            }
            Thread.sleep(time * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getThreadInfo(){
        return Thread.currentThread().getName();
    }
}
