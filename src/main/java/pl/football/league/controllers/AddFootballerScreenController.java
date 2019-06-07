package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import pl.football.league.entities.Footballer;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.Alerts;
import pl.football.league.services.ItemAddService;

import java.util.HashSet;
import java.util.List;

public class AddFootballerScreenController extends ItemAddService {
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
        currentData = new Footballer();

        name = nameTextField.getText();
        surname = surnameTextField.getText();

        if(name.equals("") || surname.equals("")){
            Alert emptyField = Alerts.emptyField();
            emptyField.showAndWait();
            return;
        }
        else{
            ((Footballer)currentData).setName(name);
            ((Footballer)currentData).setSurname(surname);
        }

        position = positionComboBox.getValue();
        if(positionComboBox.getValue() == null){
            Alert noPosition = Alerts.noPosition();
            noPosition.showAndWait();
            return;
        }
        else {
            ((Footballer)currentData).setPosition(position);
        }

        if(teamComboBox.getValue() != null)
            ((Footballer)currentData).setTeam(teamComboBox.getValue());
        else
            ((Footballer)currentData).setTeam(null);

        if(numberComboBox.getValue() != null)
            ((Footballer)currentData).setNumber(numberComboBox.getValue());
        else
            ((Footballer)currentData).setNumber(null);

        ((Footballer)currentData).setFans(new HashSet<>());

        addItem(currentData);
        back();
    }

    @FXML
    void back() {
        if(isCanceled)
            buffer.put();
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

        List<Team> teams;teams = entityManager.createQuery("select T from Team T").getResultList();
        for(Team t : teams){
            teamComboBox.getItems().add(t);
        }
    }
}
