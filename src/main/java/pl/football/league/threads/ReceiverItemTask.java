package pl.football.league.threads;

import javafx.concurrent.Task;

public class ReceiverItemTask extends Task {
    Buffer b1;

    public ReceiverItemTask(Buffer b){
        b1 = b;
    }

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
