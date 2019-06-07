package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.football.league.services.MainService;


public class MainScreenController extends MainService {
    private Stage stage;

    @FXML
    private BorderPane borderPane;

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
