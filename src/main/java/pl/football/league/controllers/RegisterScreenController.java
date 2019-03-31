package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pl.football.league.entities.Fan;

import javax.persistence.EntityManager;

public class RegisterScreenController {
    private EntityManager entityManager;
    private MainScreenController mainController;

    @FXML
    void initialize() {
        mainController.getStage().setResizable(true);
        mainController.getStage().setTitle("Rejestracja");
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
            Alert emptyField = new Alert(Alert.AlertType.WARNING);
            emptyField.setTitle("Puste pole");
            emptyField.setHeaderText("Jedno z obowiazkowych pol jest puste!");
            emptyField.setContentText("Prosze je uzupelnic.");
            emptyField.showAndWait();
            return;
        }

        try {
            age = Integer.parseUnsignedInt(ageTextField.getText());
            newUser.setAge(age);
        }
        catch(NumberFormatException e){
            if(!ageTextField.getText().equals("")) {
                Alert wrongNumberAlert = new Alert(Alert.AlertType.ERROR);
                wrongNumberAlert.setTitle("Zla liczba");
                wrongNumberAlert.setHeaderText("Podano lanuch znakow zamiast liczby!");
                wrongNumberAlert.setContentText("Prosze podac poprawne dane.");
                wrongNumberAlert.showAndWait();
                return;
            }
        }

        if(!password.equals(repeatPassword)) {
            Alert diffrentPasswordsAlert = new Alert(Alert.AlertType.ERROR);
            diffrentPasswordsAlert.setTitle("Rozne hasla");
            diffrentPasswordsAlert.setHeaderText("Podano 2 rozne hasla!");
            diffrentPasswordsAlert.setContentText("Prosze podac poprawne dane.");
            diffrentPasswordsAlert.showAndWait();
            return;
        }
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setNickname(nickname);
        newUser.setPassword(password);

        entityManager.getTransaction().begin();
        try {
            entityManager.persist(newUser);
            entityManager.getTransaction().commit();
            back();
        }
        catch(javax.persistence.RollbackException e){
            Alert userAlreadyExistAlert = new Alert(Alert.AlertType.ERROR);
            userAlreadyExistAlert.setTitle("Zajety login");
            userAlreadyExistAlert.setHeaderText("Uyztkownik o podanym loginie juz istnieje!");
            userAlreadyExistAlert.setContentText("Prosze wybrac inny login.");
            userAlreadyExistAlert.showAndWait();
            entityManager.getTransaction().rollback();
        }
    }

    @FXML
    void back() {
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
}
