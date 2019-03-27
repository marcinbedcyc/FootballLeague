package pl.football.league.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import pl.football.league.entities.Fan;

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
        Fan kibic;
        try {
            kibic = (Fan) entityManager.createQuery(hql)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .getSingleResult();
            System.out.println(kibic.getName());
        } catch (NoResultException e) {
            System.out.println("Bledne dane");
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
