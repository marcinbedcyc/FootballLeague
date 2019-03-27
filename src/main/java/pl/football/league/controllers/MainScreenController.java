package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import java.io.IOException;

public class MainScreenController {

    private EntityManager entityManager;
    private Stage stage;

    @FXML
    private BorderPane borderPane;

    @FXML
    void initialize() {
        Pane pane;
        LoginScreenController loginScreenController;

        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/loginScreen.fxml"));

            loginScreenController = new LoginScreenController();
            loginScreenController.setEntityManager(entityManager);
            loginScreenController.setMainController(this);

            loader.setController(loginScreenController);
            pane = loader.load();
            borderPane.setCenter(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
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
