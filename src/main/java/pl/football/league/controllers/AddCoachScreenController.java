package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.football.league.entities.Coach;
import pl.football.league.fxmlUtils.Alerts;

import javax.persistence.EntityManager;

public class AddCoachScreenController {
    private EntityManager entityManager;
    private Stage stage;
    private Coach coach;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField ageTextField;

    @FXML
    private Button acceptButton;

    @FXML
    private Button cancelButton;


    @FXML
    void addCoachAndBack() {
        String name, surname;
        int age;
        coach = new Coach();

        name = nameTextField.getText();
        surname = surnameTextField.getText();

        if(name.equals("") || surname.equals("")){
            Alert emptyField = Alerts.emptyField();
            emptyField.showAndWait();
            return;
        }

        try {
            age = Integer.parseUnsignedInt(ageTextField.getText());
            coach.setAge(age);
        }
        catch(NumberFormatException e){
            if(!ageTextField.getText().equals("")) {
                Alert wrongNumberAlert = Alerts.wrongNumber();
                wrongNumberAlert.showAndWait();
                return;
            }
        }

        coach.setName(name);
        coach.setSurname(surname);

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(coach);
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
        stage.close();
    }

    @FXML
    void initialize() {

    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public  void setDependecies(EntityManager entityManager, Stage stage){
        setEntityManager(entityManager);
        setStage(stage);
    }

    public Coach getCoach() {
        return coach;
    }
}


