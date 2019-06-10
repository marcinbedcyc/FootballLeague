package pl.football.league.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pl.football.league.entities.Fan;
import pl.football.league.fxmlUtils.Alerts;
import pl.football.league.services.MainService;
import pl.football.league.threads.Buffer;

import javax.persistence.NoResultException;

/**
 * Kontroler do pliku loginScreen.fxml
 * @author Marcin Cyc
 * @see pl.football.league.services.MainService
 */
public class LoginScreenController extends MainService {
    /**
     * TextField z pseudonimem użytkownika
     * @see javafx.scene.control.TextField
     */
    @FXML
    private TextField loginTextField;

    /**
     * PasswordField z hasłem użytkownika
     * @see javafx.scene.control.PasswordField
     */
    @FXML
    private PasswordField passwordTextField;

    /**
     * Przycisk logujący do aplikacji
     * @see javafx.scene.control.Button
     */
    @FXML
    private Button loginButton;

    /**
     * Przycisk anulujący logowanie
     * @see javafx.scene.control.Button
     */
    @FXML
    private Button cancelButton;

    /**
     * Przycisk otwierania okna tworznia aplikacji
     * @see javafx.scene.control.Button
     */
    @FXML
    private Button createAccountButton;


    /**
     * Zalogowanie do aplikacji: sprawdzenie czy istnieje użytkownik o podanym loginie i haśle w bazie danych. W przypadku
     * sukcesu otworzenie głównego okna aplikacji i menu bocznego, w przeciwnym wypadku wyświetlenie informacji o błędzie w
     * stosownym komunikacie(Alert).
     * @see pl.football.league.fxmlUtils.Alerts
     */
    @FXML
    void logIn() {
        String login = loginTextField.getText();
        String password = passwordTextField.getText();
        String hql = "from Fan f where f.nickname = :login and f.password = :password";

        try {
            currentUser = (Fan) entityManager.createQuery(hql)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .getSingleResult();

            MenuBarController menuBarController  = new MenuBarController();
            TableScreenController tableScreenController = new TableScreenController();

            menuBarController.setDependencies(currentUser, entityManager, mainScreenController);
            tableScreenController.setDependencies(currentUser, entityManager, mainScreenController);

            loadLeftScene("/fxml/menuBar.fxml", menuBarController);
            loadCenterScene("/fxml/tableScreen.fxml", tableScreenController);

            mainScreenController.getStage().setResizable(true);
            mainScreenController.getStage().setMinWidth(1200);
            mainScreenController.getStage().setMinHeight(700);
            mainScreenController.getStage().setTitle("FootballLeague");
        } catch (NoResultException e) {
            Alerts.loginFail().showAndWait();
        }
    }

    /**
     * Zamknięcie aplikacji
     */
    @FXML
    void closeApplication() {
        Platform.exit();
        System.exit(0);
    }

    /**
     * Otworzenie okna rejestracji nowego użytklownika aplikacji
     */
    @FXML
    void openRegisterWindow() {
        Buffer bufferAddFan = new Buffer();
        RegisterScreenController registerScreenController = new RegisterScreenController();
        registerScreenController.setDependencies(currentUser, entityManager, mainScreenController, null, null, bufferAddFan);
        mainScreenController.getBorderPane().setMinWidth(320);
        loadCenterScene("/fxml/registerScreen.fxml", registerScreenController);
    }

    /**
     * Inicjalizacja okna: ustawienie braku roszerzenia okna oraz ustawienie tytułu
     */
    @FXML
    void initialize() {
        mainScreenController.getStage().setResizable(false);
        mainScreenController.getStage().setTitle("Logowanie");
    }
}
