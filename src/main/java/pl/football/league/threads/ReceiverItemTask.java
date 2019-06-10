package pl.football.league.threads;

import javafx.concurrent.Task;

/**
 * Współbieżny wątek/task czekający na zapełnienie bufora. Konsument przy komunikacj z aplikacją.
 *
 * @author Marcin Cyc
 * @see javafx.concurrent.Task
 */
public class ReceiverItemTask extends Task {
    /**
     * Bufor komunikacyjny z producentem.
     *
     * @see pl.football.league.threads.Buffer
     */
    private Buffer b1;

    /**
     * Tworzy nowy task zapełnienia bufora.
     *
     * @param b bufor komunikacyjny
     */
    public ReceiverItemTask(Buffer b){
        b1 = b;
    }

    /**
     * Uruchamia task działający w pętli do momentu zapełnienia się bufora. Wypisuje informację w konsoli o rozpoczęciu
     * i zakończeniu działania.
     * @return zawsze null
     * @throws Exception Nieprzechwycony wątek, który może wystąpić podczas operacji w tle
     */
    @Override
    protected Object call() throws Exception {
        System.out.println("Start consumer's thread");
        while(true) {
            if (!b1.get()){
                System.out.println("End consumer's thread");
                break;
            }
        }
        return null;
    }
}
