package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.football.league.services.MainService;

/**
 * Kontroler do pliku  mainScreen.fxml
 * @author Marcin Cyc
 * @see pl.football.league.services.MainService
 */
public class MainScreenController extends MainService {
    /**
     * Główne okno programu
     * @see javafx.stage.Stage
     */
    private Stage stage;

    /**
     * Główna scena głównego okna
     * @see javafx.scene.layout.BorderPane
     */
    @FXML
    private BorderPane borderPane;

    /**
     * Inicjalizacja okna: ustawienie okna na wymiar 1000 x 600, ustawienie ikonki okna oraz załadowanie scemy logowania
     */
    @FXML
    void initialize() {
        stage.setHeight(600);
        stage.setWidth(1000);
        stage.getIcons().add(new Image(MainScreenController.class.getResourceAsStream("/images/soccer.png")));
        stage.setOnCloseRequest(event -> closeService());

        LoginScreenController loginScreenController = new LoginScreenController();
        loginScreenController.setDependencies(null, entityManager, this);
        mainScreenController = this;
        loadCenterScene("/fxml/loginScreen.fxml", loginScreenController);
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }
}
