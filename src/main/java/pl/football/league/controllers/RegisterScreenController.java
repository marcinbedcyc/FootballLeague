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

/**
 * Kontroler do pliku registerScreen.fxml
 * @author Marcin Cyc
 * @see pl.football.league.services.ItemAddService
 */
public class RegisterScreenController extends ItemAddService {
    /**
     * Flaga czy okna ma się wyświetlać w osobnym oknie
     */
    private boolean separatelyWindow = false;

    /**
     * Inicjalizacja okna. Ustawienie skalowalności, minimalnej szerokości i tytułu okna gdy flaga separatelyWindow ma
     * wartość false. Ustawienie akcji po zamknięciu okna.
     */
    @FXML
    void initialize() {
        if(!separatelyWindow) {
            mainScreenController.getStage().setResizable(true);
            mainScreenController.getStage().setMinWidth(400);
            mainScreenController.getStage().setTitle("Rejestracja");
        }
        else{
            stage.setOnCloseRequest(event -> back());
        }
    }

    /**
     * TextField z imieniem kibica
     * @see javafx.scene.control.TextField
     */
    @FXML
    private TextField nameTextField;

    /**
     * TextField z nazwiska kibica
     * @see javafx.scene.control.TextField
     */
    @FXML
    private TextField surnameTextField;

    /**
     * TextField z pseudonimem kibica
     * @see javafx.scene.control.TextField
     */
    @FXML
    private TextField nicknameTextField;

    /**
     * TextField z hasłem kibica
     * @see javafx.scene.control.TextField
     */
    @FXML
    private PasswordField passwordTextField;

    /**
     * TextField z powtórzonym hasłem kibica
     * @see javafx.scene.control.TextField
     */
    @FXML
    private PasswordField repeatPasswordTextField;

    /**
     * TextField z wiekiem kibica
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
     * Zaakceptowanie podanych danych, próba dodania kibica do bazy danych  oraz w przypadku sukcesu zamknięcie okna.
     * Zostaje utworzony nowy obiekt Fan i dane z poszczególnych pól kontrolnych przekazywane są do obiektu. Wyświetlany
     * jest pierwszy napotkany błąd w formie komunikatu (Alert). Weryfikowane są: niepustość obowiązkowych pól(imię,
     * naziwsko, pseudonim, hasło, powtórzone hasło), poprawność podanego wieku, równość dwóch podanych haseł. Następuje
     * wzerowanie zbiorów wspieranych drużyn i piłkarzy.
     * @see pl.football.league.fxmlUtils.Alerts
     */
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

    /**
     * Zakmnięcie okna jeśli flaga separatelyWindow ma wartość true, jeśli nie inicjalizacja głównego ekranu
     */
    @FXML
    void back() {
        if(separatelyWindow){
            if(isCanceled)
                buffer.put();
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
