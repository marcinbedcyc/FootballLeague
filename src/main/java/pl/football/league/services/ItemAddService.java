package pl.football.league.services;

import javafx.stage.Stage;
import pl.football.league.controllers.MainScreenController;
import pl.football.league.entities.Fan;
import pl.football.league.threads.AddItemTask;
import pl.football.league.threads.Buffer;

import javax.persistence.EntityManager;

public class ItemAddService extends ItemShowService {
    protected Buffer buffer;
    protected Stage stage;
    protected AddItemTask addItemTask;
    protected boolean isCanceled;

    public void setDependencies(Fan currentUser, EntityManager entityManager, MainScreenController mainScreenController, Object currentData, Stage stage,  Buffer buffer){
        super.setDependencies(currentUser, entityManager, mainScreenController, currentData);
        setBuffer(buffer);
        setStage(stage);
        isCanceled = true;
    }

    public void addItem(Object item){
        isCanceled = false;
        addItemTask = new AddItemTask(entityManager, item, buffer);
        new Thread(addItemTask).start();
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
