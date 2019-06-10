package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import pl.football.league.entities.Footballer;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.Alerts;
import pl.football.league.fxmlUtils.TableControls;
import pl.football.league.services.MainService;

/**
 * Kontroler dp pliku optionScreen.fxml
 * @author Marcin Cyc
 * @see pl.football.league.services.MainService
 */
public class OptionScreenController extends MainService {
    /**
     * TextFiled z imieniem użytkownika
     * @see javafx.scene.control.TextField
     */
    @FXML
    private TextField nameTextField;

    /**
     * TextField z nazwiskiem użytkownika
     * @see javafx.scene.control.TextField
     */
    @FXML
    private TextField surnameTextField;

    /**
     * TextField z pseudonimem użytkownika
     * @see javafx.scene.control.TextField
     */
    @FXML
    private TextField nicknameTextField;

    /**
     * PasswordField ze starym hasłem użytkownika
     * @see javafx.scene.control.PasswordField
     */
    @FXML
    private PasswordField oldPasswordTextFIeld;

    /**
     * PasswordField z nowym hasłem użytkownika
     * @see javafx.scene.control.PasswordField
     */
    @FXML
    private PasswordField newPasswordTextField;

    /**
     * PasswordField z powtórzonym nowym hasłem użytkownika
     * @see javafx.scene.control.PasswordField
     */
    @FXML
    private PasswordField newPasswordRepeatTextField;

    /**
     * TextFiled z wiekiem użytkownika
     * @see javafx.scene.control.TextField
     */
    @FXML
    private TextField agetextField;

    /**
     * GridPane z piłkarzami, którym kibicuje zalogowany użytkownik
     * @see javafx.scene.layout.GridPane
     */
    @FXML
    private GridPane footballersGridPane;

    /**
     * GridPane z druzynami, którym kibicuje zalogowany użytkownik
     * @see javafx.scene.layout.GridPane
     */
    @FXML
    private GridPane teamsGridPane;

    /**
     * Inicjalizacja okna: ustawienie informacji o zalogowanym użytkowniku oraz uzupełnienie tabel wspieranych piłkarzy i
     * drużyn. W przypadku zalgowanego administratora wyłączenie opcji modyfikacji pseudnimu.
     */
    @FXML
    void initialize() {
        setInfo();
        fillTables();
        if(currentUser.getNickname().equals("admin")){
            nicknameTextField.setDisable(true);
        }
    }

    /**
     * Edytowanie informacji o zalogowanym kibicu. Po kolei weryfikowane są dane wprowadzone prez użytkownika: sprawdzenie
     * czy w polu agetextField została podana poprawna liczba, sprawdzenie czy jest poprawnie podane stare hasło, czy jesy
     * poprawnie podane nowe hasło oraz jego powtórzenie, sprawdzenie czy podany nowy login nie jest zajęty przez innego
     * użytkownika. W przypadku jakiegokolwiek błędu wyświetlany jest stosowny komuniakt w postaci Alert'u.
     * @see pl.football.league.fxmlUtils.Alerts
     */
    @FXML
    void edit() {
        boolean succes = true;
        entityManager.getTransaction().begin();
        currentUser.setName(nameTextField.getText());
        currentUser.setSurname(surnameTextField.getText());

        try {
            int age = Integer.parseUnsignedInt(agetextField.getText());
            currentUser.setAge(age);
        } catch (NumberFormatException e) {
            Alerts.wrongNumber().showAndWait();
            succes = false;
        }

        if (currentUser.getPassword().equals(oldPasswordTextFIeld.getText())) {
            if (newPasswordTextField.getText().isEmpty() || newPasswordRepeatTextField.getText().isEmpty()) {
                Alerts.emptyPasswordField().showAndWait();
                succes = false;
            } else {
                if (newPasswordTextField.getText().equals(newPasswordRepeatTextField.getText())) {
                    currentUser.setPassword(newPasswordTextField.getText());
                } else {
                    Alerts.diffrentPasswords().showAndWait();
                    succes = false;
                }
            }
        } else {
            if (!oldPasswordTextFIeld.getText().isEmpty()) {
                Alerts.wrongPassword().showAndWait();
                succes = false;
            }
        }

        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        try {
            currentUser.setNickname(nicknameTextField.getText());
            entityManager.getTransaction().commit();
        } catch (javax.persistence.RollbackException e) {
            Alert userAlreadyExistAlert = Alerts.wrongLoginAlert();
            userAlreadyExistAlert.showAndWait();
            entityManager.getTransaction().rollback();
            succes = false;
        }

        if (succes) {
            Alerts.success().showAndWait();
        }
    }

    /**
     * Uzupełnienie pól kontrolnych informacjami o zalogowanym użytkowniku: nameTextField uzupełniany jest imieniem zalogowanego
     * użytkownika, surnameTextField uzupełniany jest nazwiskiem użytkownika, nicknameTextField uzupełniany jest pseudonimem
     * użytkownika, agetextField uzupełniany jest wiekiem użytkownika, w przypadku braku podanego wieku wpisywane jest "-".
     */
    private void setInfo() {
        nameTextField.setText(currentUser.getName());
        surnameTextField.setText(currentUser.getSurname());
        nicknameTextField.setText(currentUser.getNickname());
        String result;
        if (currentUser.getAge() != null) {
            result = String.valueOf(currentUser.getAge());
        } else {
            result = "-";
        }
        agetextField.setText(result);
    }

    /**
     * Uzupełnienie odpowednich gridPane'ów z informacjami o wspieranych piłkarzach i drużynach. Dodanie akacji usuwania
     * kibicowania po kliknięciu w Label "X" oraz dodanie akcji wyświetlania informacji o piłkarzu, drużynie po kliknięciu
     * w odpowiedni Label z imieniem i nazwiskiem piłkarza lub nazwą drużyny.
     */
    private void fillTables() {
        int i = 0;
        footballersGridPane.getChildren().remove(0, footballersGridPane.getChildren().size());
        teamsGridPane.getChildren().remove(0, teamsGridPane.getChildren().size());

        if (currentUser.getSupportedFootballers() != null) {
            for (Footballer f : currentUser.getSupportedFootballers()) {
                Label footballerLabel = TableControls.LabelVGrow(100, f.getSurname());
                Label x = TableControls.LabelX(30);
                footballerLabel.setOnMouseClicked(event -> {
                    FootballerScreenController footballerScreenController = new FootballerScreenController();
                    footballerScreenController.setDependencies(currentUser, entityManager, mainScreenController, f);
                    loadCenterScene("/fxml/footballerScreen.fxml", footballerScreenController);
                });
                x.setOnMouseClicked(event -> {
                    entityManager.getTransaction().begin();
                    f.getFans().remove(currentUser);
                    currentUser.getSupportedFootballers().remove(f);
                    entityManager.getTransaction().commit();
                    fillTables();
                });
                footballersGridPane.addRow(i, footballerLabel, x);
                i++;
            }
        }
        if (currentUser.getSupportedFootballers() != null) {
            i = 0;
            for (Team t : currentUser.getSupportedTeams()) {
                Label teamLabel = TableControls.LabelVGrow(200, t.getName());
                Label x = TableControls.LabelX(30);
                teamLabel.setOnMouseClicked(event -> {
                    TeamScreenController teamScreenController = new TeamScreenController();
                    teamScreenController.setDependencies(currentUser, entityManager, mainScreenController, t);
                    loadCenterScene("/fxml/teamScreen.fxml", teamScreenController);
                });
                x.setOnMouseClicked(event -> {
                    entityManager.getTransaction().begin();
                    t.getTeamFans().remove(currentUser);
                    currentUser.getSupportedTeams().remove(t);
                    entityManager.getTransaction().commit();
                    fillTables();
                });
                teamsGridPane.addRow(i, teamLabel, x);
                i++;
            }
        }
    }
}