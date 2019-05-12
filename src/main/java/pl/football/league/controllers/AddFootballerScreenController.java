package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.football.league.entities.Footballer;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.Alerts;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;

public class AddFootballerScreenController {
    private EntityManager entityManager;
    private Stage stage;
    private List<Team> teams;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private ComboBox<String> positionComboBox;

    @FXML
    private ComboBox<Integer> numberComboBox;

    @FXML
    private ComboBox<Team> teamComboBox;

    @FXML
    private Button acceptButton;

    @FXML
    private Button cancelButton;

    @FXML
    void addFootballerAndBack() {
        String name, surname, position;
        Footballer footballer = new Footballer();

        name = nameTextField.getText();
        surname = surnameTextField.getText();

        if(name.equals("") || surname.equals("")){
            Alert emptyField = Alerts.emptyField();
            emptyField.showAndWait();
            return;
        }
        else{
            footballer.setName(name);
            footballer.setSurname(surname);
        }

        position = positionComboBox.getValue();
        if(positionComboBox.getValue() == null){
            Alert noPosition = Alerts.noPosition();
            noPosition.showAndWait();
            return;
        }
        else {
            footballer.setPosition(position);
        }

        if(teamComboBox.getValue() != null)
            footballer.setTeam(teamComboBox.getValue());
        else
            footballer.setTeam(null);

        if(numberComboBox.getValue() != null)
            footballer.setNumber(numberComboBox.getValue());
        else
            footballer.setNumber(null);

        footballer.setFans(new HashSet<>());

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(footballer);
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
        for(int i = 0; i <= 99 ; i++){
            numberComboBox.getItems().add(i);
        }

        positionComboBox.getItems().add("BR");
        positionComboBox.getItems().add("OB");
        positionComboBox.getItems().add("PO");
        positionComboBox.getItems().add("NA");

        teams = entityManager.createQuery("select T from Team T").getResultList();
        for(Team t : teams){
            teamComboBox.getItems().add(t);
        }
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
}
