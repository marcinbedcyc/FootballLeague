package pl.football.league.services;

import javafx.stage.Stage;
import pl.football.league.controllers.MainScreenController;
import pl.football.league.entities.Fan;
import pl.football.league.threads.AddItemTask;
import pl.football.league.threads.Buffer;

import javax.persistence.EntityManager;

/**
 * Serwis dodający element do bazy danych
 *
 * @author Marcin Cyc
 * @see pl.football.league.services.ItemShowService
 */
public class ItemAddService extends ItemShowService {
    /**
     * Bufor komunikacyjny
     *
     * @see pl.football.league.threads.Buffer
     */
    protected Buffer buffer;
    /**
     * Nowe okno
     *
     * @see javafx.stage.Stage
     */
    protected Stage stage;
    /**
     * Task dodający element do bazy danych
     *
     * @see pl.football.league.threads.AddItemTask
     */
    private AddItemTask addItemTask;
    /**
     * Flaga mówiąca czy czynność dodawania użytkownika do bazy została anulowana
     */
    protected boolean isCanceled;

    /**
     * Ustawienie zależności. Korzysta z ustawień zależności klasy ItemShowService.
     * @param currentUser obecnie zalogowany użytkoniwk
     * @param entityManager menadżer encji
     * @param mainScreenController kontroler głównego okna
     * @param currentData obecne dane
     * @param stage okno
     * @param buffer bufor komunikacyjny
     */
    public void setDependencies(Fan currentUser, EntityManager entityManager, MainScreenController mainScreenController, Object currentData, Stage stage,  Buffer buffer){
        super.setDependencies(currentUser, entityManager, mainScreenController, currentData);
        setBuffer(buffer);
        setStage(stage);
        isCanceled = true;
    }

    /**
     * Dodanie elementu do bazy danych. Uruchomienie współbieżnego task'a dodającego element i ustawienie falgi na false
     * @param item dodawany element
     */
    protected void addItem(Object item){
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
