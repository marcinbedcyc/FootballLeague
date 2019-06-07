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
import pl.football.league.services.ItemAddService;

import java.util.*;

public class RegisterScreenController extends ItemAddService {
    private boolean separatelyWindow = false;

    @FXML
    void initialize() {
        if(!separatelyWindow) {
            mainScreenController.getStage().setResizable(true);
            mainScreenController.getStage().setMinWidth(400);
            mainScreenController.getStage().setTitle("Rejestracja");
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
        currentData = new Fan();

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
            ((Fan)currentData).setAge(age);
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
            diffrentPasswordsAlert.showAndWait();
            return;
        }
        ((Fan)currentData).setName(name);
        ((Fan)currentData).setSurname(surname);
        ((Fan)currentData).setNickname(nickname);
        ((Fan)currentData).setPassword(password);
        Set<Footballer> emptyFootballerSet = new HashSet<>();
        Set<Team> emptyTeamSet = new HashSet<>();
        ((Fan)currentData).setSupportedFootballers(emptyFootballerSet);
        ((Fan)currentData).setSupportedTeams(emptyTeamSet);

        addItem(currentData);
        back();
    }

    @FXML
    void back() {
        if(separatelyWindow){
            Stage stage = (Stage)cancelButton.getScene().getWindow();
            stage.close();
        }
        else
            mainScreenController.initialize();
    }

    public void setSeparatelyWindow(){
        separatelyWindow = true;
    }
}
