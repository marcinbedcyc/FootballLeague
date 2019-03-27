package pl.football.league.controllers;

import javafx.fxml.FXML;
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

        try {
            age = Integer.parseUnsignedInt(ageTextField.getText());
            newUser.setAge(age);
        }
        catch(NumberFormatException e){
            System.out.println("Podanno zla liczbe");
            return;
        }

        if(!password.equals(repeatPassword)) {
            System.out.println("Podane hasla nie sa takie same!");
            System.out.println(password + " " + repeatPasswordTextField);
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
            System.out.println("Uzytkownik juz istnieje");
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
