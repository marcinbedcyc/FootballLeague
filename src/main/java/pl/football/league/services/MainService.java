package pl.football.league.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.football.league.controllers.MainScreenController;
import pl.football.league.entities.Fan;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;

/**
 * Główny serwis aplikacji komunikujący się z bazą i zarządzający wyświetlaniem okien.
 *
 * @author Marcin Cyc
 */
public class MainService {
    /**
     * Menadżer encji komunikujący się z bazą danych
     */
    protected EntityManager entityManager;
    /**
     * Kontroler głównego okna aplikacji
     */
    protected MainScreenController mainScreenController;
    /**
     * Obecnie zalogowany użytkownik
     */
    protected Fan currentUser;

    /**
     * Załadowanie nowego pliku .fxml jako środkowe okno głownego ekranu
     * @param path scieżka do pliku .fxml
     * @param controller kontroler opisujący plik podany w pierwszym argumencie
     */
    protected void loadCenterScene(String path, Object controller){
        Parent root = loadScene(path, controller);
        mainScreenController.getBorderPane().setCenter(root);
    }

    /**
     * Załadowanie nowego pliku .fxml jako lewe okno głownego ekranu
     * @param path scieżka do pliku .fxml
     * @param controller kontroler opisujący plik podany w pierwszym argumencie
     */
    protected void loadLeftScene(String path, Object controller){
        Parent root = loadScene(path, controller);
        mainScreenController.getBorderPane().setLeft(root);
    }

    /**
     * Otworznie nowego okna aplikacji 320 x 600 skalowalnego z minimalnymi rozmiarami 320 x 600
     * @param path scieżka do pliku .fxml
     * @param controller kontroler opisujący plik podany w pierwszym argumencie
     * @param stage nowe okno
     */
    protected void loadNewStage(String path, Object controller, Stage stage){
        Parent root = loadScene(path, controller);
        stage.setScene(new Scene(root, 320, 600));
        stage.setMinHeight(600);
        stage.setMinWidth(320);
        stage.showAndWait();
    }

    /**
     * Załadowanie nowego okna
     * @param path scieżka do pliku .fxml
     * @param controller kontroler opisujący plik podany w pierwszym argumencie
     * @return nową scenę
     *
     * @see javafx.fxml.FXMLLoader
     * @see javafx.scene.Parent
     */
    private Parent loadScene(String path, Object controller){
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(path));
        loader.setController(controller);
        try{
            root = loader.load();
        }catch(IOException e){
            e.printStackTrace();
        }
        return root;
    }

    /**
     * Zakmnięcie serwisu. Zamyka entityManager i entityManagerFactory
     */
    protected void closeService(){
        EntityManagerFactory entityManagerFactory = entityManager.getEntityManagerFactory();
        entityManager.close();
        entityManagerFactory.close();
    }

    /**
     * Ustawia zależności: obecnie zalogoawnego użytkowika, menadżera encji, kontroler głównego okna
     * @param currentUser obecnie zalogowany użytkownik
     * @param entityManager menadżer encji
     * @param mainScreenController kontroler głównego okna
     */
    public void setDependencies(Fan currentUser, EntityManager entityManager, MainScreenController mainScreenController){
        setCurrentUser(currentUser);
        setEntityManager(entityManager);
        setMainScreenController(mainScreenController);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public MainScreenController getMainScreenController() {
        return mainScreenController;
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    public Fan getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Fan currentUser) {
        this.currentUser = currentUser;
    }
}
