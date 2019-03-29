package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import pl.football.league.entities.Fan;

import javax.persistence.EntityManager;
import java.io.IOException;

public class MenuBarController {

    private MainScreenController mainController;
    private EntityManager entityManager;
    private Fan currentUser;

    @FXML
    private ToggleButton homeButton;

    @FXML
    private ToggleGroup menu;

    @FXML
    private ToggleButton leagueTableButton;

    @FXML
    private ToggleButton resultsButton;

    @FXML
    private ToggleButton teamsButton;

    @FXML
    private ToggleButton footballersButton;

    @FXML
    private ToggleButton coachesButton;

    @FXML
    private ToggleButton fansButton;

    @FXML
    private ToggleButton optionButton;

    @FXML
    private ToggleButton adminOptionButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label whoLogIn;

    @FXML
    void initialize() {
        whoLogIn.setText("Zalogowany jako " + currentUser.getNickname());
        if(!currentUser.getNickname().equals("admin")){
            adminOptionButton.setVisible(false);
        }
    }

    @FXML
    void logout() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/loginScreen.fxml"));

        LoginScreenController loginScreenController = new LoginScreenController();
        loginScreenController.setEntityManager(entityManager);
        loginScreenController.setMainController(mainController);

        loader.setController(loginScreenController);
        Pane pane;
        try {
            pane = loader.load();
            mainController.getBorderPane().setCenter(pane);
            mainController.getBorderPane().setLeft(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setCurrentUser(Fan currentUser) {
        this.currentUser = currentUser;
    }
}
