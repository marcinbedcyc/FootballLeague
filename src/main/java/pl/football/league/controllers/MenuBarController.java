package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
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
    void tableOpen() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/tableScreen.fxml"));
        TableScreenController tableScreenController = new TableScreenController();
        tableScreenController.setCurrentUser(currentUser);
        tableScreenController.setEntityManager(entityManager);
        tableScreenController.setMainController(mainController);

        loader.setController(tableScreenController);
        try {
            Parent root = loader.load();
            mainController.getBorderPane().setCenter(root);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    void footballersTableOpen() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/footballersTableScreen.fxml"));
        FootballersTableScreenController footballersTableScreenController = new FootballersTableScreenController();
        footballersTableScreenController.setCurrentUser(currentUser);
        footballersTableScreenController.setEntityManager(entityManager);
        footballersTableScreenController.setMainController(mainController);

        loader.setController(footballersTableScreenController);
        try {
            Parent root = loader.load();
            mainController.getBorderPane().setCenter(root);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    void coachesTableOpen(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/coachesTableScreen.fxml"));
        CoachesTableScreenController coachesTableScreenController = new CoachesTableScreenController();
        coachesTableScreenController.setCurrentUser(currentUser);
        coachesTableScreenController.setEntityManager(entityManager);
        coachesTableScreenController.setMainController(mainController);


        loader.setController(coachesTableScreenController);
        try {
            Parent root = loader.load();
            mainController.getBorderPane().setCenter(root);
        }
        catch (IOException ex){
            ex.printStackTrace();
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

    @FXML
    void resultTableOpen() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/resultTableScreen.fxml"));
        ResultTableScreenController resultTableScreenController = new ResultTableScreenController();
        resultTableScreenController.setCurrentUser(currentUser);
        resultTableScreenController.setEntityManager(entityManager);
        resultTableScreenController.setMainController(mainController);

        loader.setController(resultTableScreenController);
        tryLoader(loader);
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

    private void tryLoader(FXMLLoader loader){
        try {
            Parent root = loader.load();
            mainController.getBorderPane().setCenter(root);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
