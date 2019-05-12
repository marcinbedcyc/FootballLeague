package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Footballer;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.Alerts;

import javax.persistence.EntityManager;
import java.util.*;

public class RegisterScreenController {
    private EntityManager entityManager;
    private MainScreenController mainController;
    private boolean separatelyWindow = false;

    @FXML
    void initialize() {
        if(!separatelyWindow) {
            mainController.getStage().setResizable(true);
            mainController.getStage().setMinWidth(400);
            mainController.getStage().setTitle("Rejestracja");
        }
    }

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField nicknameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private PasswordField repeatPasswordTextField;

    @FXML
    private TextField ageTextField;

    @FXML
    private Button acceptButton;

    @FXML
    private Button cancelButton;

    @FXML
    void addUserAndBack() {
        String name, surname, nickname, password, repeatPassword;
        int age;
        Fan newUser = new Fan();

        name = nameTextField.getText();
        surname = surnameTextField.getText();
        nickname = nicknameTextField.getText();
        password = passwordTextField.getText();
        repeatPassword = repeatPasswordTextField.getText();

        if(name.equals("") || surname.equals("") || nickname.equals("") || password.equals("") || repeatPassword.equals("")){
            Alert emptyField = Alerts.emptyField();
            emptyField.showAndWait();
            return;
        }

        try {
            age = Integer.parseUnsignedInt(ageTextField.getText());
            newUser.setAge(age);
        }
        catch(NumberFormatException e){
            if(!ageTextField.getText().equals("")) {
                Alert wrongNumberAlert = Alerts.wrongNumber();
                wrongNumberAlert.showAndWait();
                return;
            }
        }

        if(!password.equals(repeatPassword)) {
            Alert diffrentPasswordsAlert = Alerts.diffrentPasswords();
            return;
        }
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setNickname(nickname);
        newUser.setPassword(password);
        Set<Footballer> emptyFootballerSet = new HashSet<>();
        Set<Team> emptyTeamSet = new HashSet<>();
        newUser.setSupportedFootballers(emptyFootballerSet);
        newUser.setSupportedTeams(emptyTeamSet);

        entityManager.getTransaction().begin();
        try {
            entityManager.persist(newUser);
            entityManager.getTransaction().commit();
            back();
        }
        catch(Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            Alert transactionFail = Alerts.transactionFail();
            transactionFail.showAndWait();
        }
    }

    @FXML
    void back() {
        if(separatelyWindow){
            Stage stage = (Stage)cancelButton.getScene().getWindow();
            stage.close();
        }
        else
            mainController.initialize();
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

    public void setSeparatelyWindow(){
        separatelyWindow = true;
    }
}
