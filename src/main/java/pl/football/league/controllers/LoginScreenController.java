package pl.football.league.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pl.football.league.entities.Fan;
import pl.football.league.fxmlUtils.Alerts;
import pl.football.league.services.MainService;
import pl.football.league.threads.Buffer;

import javax.persistence.NoResultException;

public class LoginScreenController extends MainService {
    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button createAccountButton;


    @FXML
    void logIn(ActionEvent event) {
        String login = loginTextField.getText();
        String password = passwordTextField.getText();
        String hql = "from Fan f where f.nickname = :login and f.password = :password";

        try {
            currentUser = (Fan) entityManager.createQuery(hql)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .getSingleResult();

            MenuBarController menuBarController  = new MenuBarController();
            TableScreenController tableScreenController = new TableScreenController();

            menuBarController.setDependencies(currentUser, entityManager, mainScreenController);
            tableScreenController.setDependencies(currentUser, entityManager, mainScreenController);

            loadLeftScene("/fxml/menuBar.fxml", menuBarController);
            loadCenterScene("/fxml/tableScreen.fxml", tableScreenController);

            mainScreenController.getStage().setResizable(true);
            mainScreenController.getStage().setMinWidth(1200);
            mainScreenController.getStage().setMinHeight(700);
            mainScreenController.getStage().setTitle("FootballLeague");
        } catch (NoResultException e) {
            Alert loginFailedAlert = Alerts.loginFail();
            loginFailedAlert.showAndWait();
        }
    }

    @FXML
    void closeApplication() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void openRegisterWindow() {
        Buffer bufferAddFan = new Buffer();
        RegisterScreenController registerScreenController = new RegisterScreenController();
        registerScreenController.setDependencies(currentUser, entityManager, mainScreenController, null, null, bufferAddFan);
        mainScreenController.getBorderPane().setMinWidth(320);
        loadCenterScene("/fxml/registerScreen.fxml", registerScreenController);
    }

    @FXML
    void initialize() {
        mainScreenController.getStage().setResizable(false);
        mainScreenController.getStage().setTitle("Logowanie");
    }
}
