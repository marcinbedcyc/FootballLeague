package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.football.league.entities.Coach;
import pl.football.league.fxmlUtils.Alerts;
import pl.football.league.services.ItemAddService;

/**
 * Kontroler do pliku addCoachScreen.fxml
 * @author Marcin Cyc
 * @see pl.football.league.services.ItemAddService
 */
public class AddCoachScreenController extends ItemAddService {
    /**
     * Label z tytułem okna
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label titleLabel;

    /**
     * TextField z imieniem trenera
     * @see javafx.scene.control.TextField
     */
    @FXML
    private TextField nameTextField;

    /**
     * TextField z nazwiskiem trenera
     * @see javafx.scene.control.TextField
     */
    @FXML
    private TextField surnameTextField;

    /**
     * TextField z wiekiem trenera
     * @see javafx.scene.control.TextField
     */
    @FXML
    private TextField ageTextField;

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
     * Zaakceptowanie podanych danych, próba dodania trenera do bazy danych  oraz w przypadku sukcesu zamknięcie okna.
     * Zostaje utworzony nowy obiekt Coach i dane z poszczególnych pól kontrolnych przekazywane są do obiektu. Wyświetlany
     * jest pierwszy napotkany błąd w formie komunikatu (Alert). Weryfikowane są: niepustość pól imię, naziwsko oraz
     * porawność wprowadzeonej liczby.
     * @see pl.football.league.fxmlUtils.Alerts
     */
    @FXML
    void addCoachAndBack() {
        String name, surname;
        int age;
        currentData = new Coach();

        name = nameTextField.getText();
        surname = surnameTextField.getText();

        if(name.equals("") || surname.equals("")){
            Alert emptyField = Alerts.emptyField();
            emptyField.showAndWait();
            return;
        }

        try {
            age = Integer.parseUnsignedInt(ageTextField.getText());
            ((Coach)currentData).setAge(age);
        }
        catch(NumberFormatException e){
            if(!ageTextField.getText().equals("")) {
                Alert wrongNumberAlert = Alerts.wrongNumber();
                wrongNumberAlert.showAndWait();
                return;
            }
        }

        ((Coach)currentData).setName(name);
        ((Coach)currentData).setSurname(surname);

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
     * Inicjalizacja okna. Ustawienie akcji po zamknięciu okna.
     */
    @FXML
    void initialize() {
        stage.setOnCloseRequest(event -> back());
    }
}