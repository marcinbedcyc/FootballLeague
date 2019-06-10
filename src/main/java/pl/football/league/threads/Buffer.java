package pl.football.league.threads;

/**
 * Bufor komunikujący pomiędzy sobą producenta i konsumenta.
 *
 * @author Marcin Cyc
 */
public class Buffer {
    /**
     * Flaga zapełnienia bufora. Flaga mówiąca czy bufor jest wolny.
     */
    private boolean isBufferFree = true;

    /**
     * Jeśli bufor jest wolny to czeka(wątek blokuje się), aż zostanie zapełniony przez producenta korzystającego z
     * tego bufora.
     *
     * @return stan zapełnienia bufora
     */
    public synchronized  boolean get(){
        if(isBufferFree){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return isBufferFree;
    }

    /**
     * Jeśli bufor jest zapełniony to czeka aż zostanie zwolniony i zapełnia go. Oraz odblokowuje czekający wątek.
     * Ustawia flagę zapełenienia bufora na false.
     */
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
