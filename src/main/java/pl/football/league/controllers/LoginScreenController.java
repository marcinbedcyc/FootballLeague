package pl.football.league.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import pl.football.league.entities.Fan;
import pl.football.league.fxmlUtils.Alerts;
import pl.football.league.fxmlUtils.TableControls;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.IOException;

public class LoginScreenController {
    private EntityManager entityManager;
    private MainScreenController mainController;

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
        Fan curentUser;

        try {
            curentUser = (Fan) entityManager.createQuery(hql)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .getSingleResult();

            MenuBarController menuBarController;
            TableScreenController tableScreenController;
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/menuBar.fxml"));
            FXMLLoader loaderCenter = new FXMLLoader(this.getClass().getResource("/fxml/tableScreen.fxml"));

            menuBarController = new MenuBarController();
            menuBarController.setDependencies(mainController, entityManager, curentUser);

            tableScreenController = new TableScreenController();
            tableScreenController.setDependencies(mainController, entityManager, curentUser);

            loader.setController(menuBarController);
            loaderCenter.setController(tableScreenController);
            Pane vBox = loader.load();
            Pane center = loaderCenter.load();
            mainController.getBorderPane().setLeft(vBox);
            mainController.getBorderPane().setCenter(center);
            mainController.getStage().setResizable(true);
            mainController.getStage().setMinWidth(1200);
            mainController.getStage().setMinHeight(700);
            mainController.getStage().setTitle("FootballLeague");

        } catch (NoResultException e) {
            Alert loginFailedAlert = Alerts.loginFail();
            loginFailedAlert.showAndWait();
        }catch (IOException e){
            Alert loginFailedAlert = Alerts.loginFail();
            loginFailedAlert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    void closeApplication(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void openRegisterWindow(ActionEvent event) {
        Pane pane;
        RegisterScreenController registerScreenController;

        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/registerScreen.fxml"));

            registerScreenController = new RegisterScreenController();
            registerScreenController.setEntityManager(entityManager);
            registerScreenController.setMainController(this.mainController);

            loader.setController(registerScreenController);
            pane = loader.load();
            mainController.getBorderPane().setCenter(pane);
            mainController.getBorderPane().setMinWidth(320);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        mainController.getStage().setResizable(false);
        mainController.getStage().setTitle("Logowanie");
    }

    public MainScreenController getMainController() {
        return mainController;
    }

    public void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
