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

/**
 * Kontroler do pliku addFootballerScreen.fxml
 * @author Marcin Cyc
 * @see pl.football.league.services.ItemAddService
 */
public class AddFootballerScreenController extends ItemAddService {
    /**
     * TextField z imieniem piłkarza
     * @see javafx.scene.control.TextField
     */
    @FXML
    private TextField nameTextField;

    /**
     * TextField z nazwiskiem piłkarza
     * @see javafx.scene.control.TextField
     */
    @FXML
    private TextField surnameTextField;

    /**
     * ComboBox z możliwymi pozycjami dla zawodnika
     * @see javafx.scene.control.ComboBox
     */
    @FXML
    private ComboBox<String> positionComboBox;

    /**
     * ComboBox z numerami na koszulce dla zawodnika
     * @see javafx.scene.control.ComboBox
     */
    @FXML
    private ComboBox<Integer> numberComboBox;

    /**
     * ComboBox z drużynami dla zawodnika
     * @see javafx.scene.control.ComboBox
     */
    @FXML
    private ComboBox<Team> teamComboBox;

    /**
     * Przycisk akceptujący podane dane
     * @see javafx.scene.control.Button
     */
    @FXML
    private Button acceptButton;

    /**
     * Przycisk anulujący dodawanie i zamykjący okno
     * @see javafx.scene.control.Button
     */
    @FXML
    private Button cancelButton;

    /**
     * Zaakceptowanie podanych danych, próba dodania piłkarza do bazy danych  oraz w przypadku sukcesu zamknięcie okna.
     * Zostaje utworzony nowy obiekt Footballer i dane z poszczególnych pól kontrolnych przekazywane są do obiektu. Wyświetlany
     * jest pierwszy napotkany błąd w formie komunikatu (Alert). Weryfikowane są: niepustość imieniam nazwiska oraz pozycji.
     * @see pl.football.league.fxmlUtils.Alerts
     */
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

    /**
     * Zakmnięcie okna
     */
    @FXML
    void back() {
        if(isCanceled)
            buffer.put();
        stage.close();
    }

    /**
     * Inicjalizacja okna: ustawienie comboBox'ów. Ustawienie akcji po zamknięciu okna.
     */
    @FXML
    void initialize() {
        stage.setOnCloseRequest(event -> back());
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
