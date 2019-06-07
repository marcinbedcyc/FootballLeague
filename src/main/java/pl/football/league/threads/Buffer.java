package pl.football.league.threads;

public class Buffer {
    boolean isBufferFree = true;

    synchronized  boolean get(){
        if(isBufferFree){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return isBufferFree;
    }

    public synchronized void put(){
        if(!isBufferFree){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isBufferFree = false;
        notify();
    }
}
