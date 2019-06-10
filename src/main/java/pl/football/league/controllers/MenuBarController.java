package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.football.league.services.MainService;

/**
 * Kontroler do pliku menuBar.fxml
 * @author Marcin Cyc
 * @see pl.football.league.services.MainService
 */
public class MenuBarController extends MainService {
    /**
     * Przycisk ekranu domowego z bocznego menu
     * @see javafx.scene.control.ToggleButton
     */
    @FXML
    private ToggleButton homeButton;

    @FXML
    private ToggleGroup menu;

    /**
     * Przycisk tabeli ligi z bocznego menu
     * @see javafx.scene.control.ToggleButton
     */
    @FXML
    private ToggleButton leagueTableButton;

    /**
     * Przycisk tabeli wyników z bocznego menu
     * @see javafx.scene.control.ToggleButton
     */
    @FXML
    private ToggleButton resultsButton;

    /**
     * Przycisk tabeli drużyn z bocznego menu
     * @see javafx.scene.control.ToggleButton
     */
    @FXML
    private ToggleButton teamsButton;

    /**
     * Przycisk tabeli piłkarzy z bocznego menu
     * @see javafx.scene.control.ToggleButton
     */
    @FXML
    private ToggleButton footballersButton;

    /**
     * Przycisk tabeli trenerów z bocznego menu
     * @see javafx.scene.control.ToggleButton
     */
    @FXML
    private ToggleButton coachesButton;

    /**
     * Przycisk tabeli fanów z bocznego menu
     * @see javafx.scene.control.ToggleButton
     */
    @FXML
    private ToggleButton fansButton;

    /**
     * Przycisk opcji z bocznego menu
     * @see javafx.scene.control.ToggleButton
     */
    @FXML
    private ToggleButton optionButton;

    /**
     * Przycisk opcji adminstracyjnych z bocznego menu
     * @see javafx.scene.control.ToggleButton
     */
    @FXML
    private ToggleButton adminOptionButton;

    /**
     * Przycisk wylogowania z bocznego menu
     * @see javafx.scene.control.ToggleButton
     */
    @FXML
    private Button logoutButton;

    /**
     * Label opisujący kto jest aktualnie zalogowany w aplikacji
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label whoLogIn;

    /**
     * Inicjalizacja okna: ustawienie Label'a z informacją o zalogowanym użytkowniku, pokazanie dodatkowej opcji w menu
     * (Zarządzaj)w przypadku gdy zalogowany jest administrator.
     */
    @FXML
    void initialize() {
        whoLogIn.setText("Zalogowany jako " + currentUser.getNickname());
        if(!currentUser.getNickname().equals("admin")){
            adminOptionButton.setVisible(false);
        }
    }

    /**
     * Otworzenie tabeli ligi (załadowanie sceny z pliku tableScreen.fxml)
     */
    @FXML
    void tableOpen() {
        TableScreenController tableScreenController = new TableScreenController();
        tableScreenController.setDependencies(currentUser,entityManager, mainScreenController);
        loadCenterScene("/fxml/tableScreen.fxml", tableScreenController);
    }

    /**
     * Otworzenie okna startowego aplikacji
     */
    @FXML
    void start(){
        tableOpen();
        homeButton.setSelected(true);
    }

    /**
     * Otworzenie tabeli piłkarzy (załadowanie sceny z pliku footballersTableScreen.fxml)
     */
    @FXML
    void footballersTableOpen() {
        FootballersTableScreenController footballersTableScreenController = new FootballersTableScreenController();
        footballersTableScreenController.setDependencies(currentUser, entityManager, mainScreenController);
        loadCenterScene("/fxml/footballersTableScreen.fxml", footballersTableScreenController);
    }

    /**
     * Otworzenie tabeli trenerów (załadowanie sceny z pliku coachesTableScreen.fxml)
     */
    @FXML
    void coachesTableOpen(){
        CoachesTableScreenController coachesTableScreenController = new CoachesTableScreenController();
        coachesTableScreenController.setDependencies(currentUser,entityManager,mainScreenController);
        loadCenterScene("/fxml/coachesTableScreen.fxml", coachesTableScreenController);
    }

    /**
     * Otworzenie tabeli drużyn (załadowanie sceny z pliku teamsTableScreen.fxml)
     */
    @FXML
    void teamsTableOpen() {
        TeamsTableScreenController teamsTableScreenController = new TeamsTableScreenController();
        teamsTableScreenController.setDependencies(currentUser, entityManager, mainScreenController);
        loadCenterScene("/fxml/teamsTable.fxml", teamsTableScreenController);
    }


    /**
     * Wylogwanie z aplikacji i załadowanie ekranu logowania
     */
    @FXML
    void logout() {
        LoginScreenController loginScreenController = new LoginScreenController();
        loginScreenController.setDependencies(null, entityManager, mainScreenController);
        loadCenterScene("/fxml/loginScreen.fxml", loginScreenController);
        mainScreenController.getBorderPane().setLeft(null);
        mainScreenController.getStage().setWidth(1000);
        mainScreenController.getStage().setHeight(600);
    }

    /**
     * Otworzenie tabeli kibiców (załadowanie sceny z pliku fansTableScreen.fxml)
     */
    @FXML
    void fansTableOpen() {
        FansScreenTableController fansScreenTableController = new FansScreenTableController();
        fansScreenTableController.setDependencies(currentUser, entityManager, mainScreenController);
        loadCenterScene("/fxml/fansTableScreen.fxml", fansScreenTableController);
    }

    /**
     * Otworzenie tabeli wyników meczów (załadowanie sceny z pliku resultTableScreen.fxml)
     */
    @FXML
    void resultTableOpen() {
        ResultTableScreenController resultTableScreenController = new ResultTableScreenController();
        resultTableScreenController.setDependencies(currentUser, entityManager, mainScreenController);
        loadCenterScene("/fxml/resultTableScreen.fxml", resultTableScreenController);
    }

    /**
     * Otworzenie opcji zalogowanego użytkownika(załadowanie sceny z pliku optionScreen.fxml)
     */
    @FXML
    void openOption(){
        OptionScreenController optionScreenController = new OptionScreenController();
        optionScreenController.setDependencies(currentUser, entityManager,  mainScreenController);
        loadCenterScene("/fxml/optionScreen.fxml", optionScreenController);
    }

    /**
     * Otworzenie opcji administracyjnych (załadowanie sceny z pliku managmentScreen.fxml)
     */
    @FXML
    void openManagment(){
        ManagementScreenController managementScreenController = new ManagementScreenController();
        managementScreenController.setEntityManager(entityManager);
        loadCenterScene("/fxml/managmentScreen.fxml", managementScreenController);
    }
}
