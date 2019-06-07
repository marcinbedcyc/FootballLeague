package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.football.league.services.MainService;
public class MenuBarController extends MainService {

    @FXML
    private ToggleButton homeButton;

    @FXML
    private ToggleGroup menu;

    @FXML
    private ToggleButton leagueTableButton;

    @FXML
    private ToggleButton resultsButton;

    @FXML
    private ToggleButton teamsButton;

    @FXML
    private ToggleButton footballersButton;

    @FXML
    private ToggleButton coachesButton;

    @FXML
    private ToggleButton fansButton;

    @FXML
    private ToggleButton optionButton;

    @FXML
    private ToggleButton adminOptionButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label whoLogIn;

    @FXML
    void initialize() {
        whoLogIn.setText("Zalogowany jako " + currentUser.getNickname());
        if(!currentUser.getNickname().equals("admin")){
            adminOptionButton.setVisible(false);
        }
    }

    @FXML
    void tableOpen() {
        TableScreenController tableScreenController = new TableScreenController();
        tableScreenController.setDependencies(currentUser,entityManager, mainScreenController);
        loadCenterScene("/fxml/tableScreen.fxml", tableScreenController);
    }

    @FXML
    void start(){
        tableOpen();
        homeButton.setSelected(true);
    }

    @FXML
    void footballersTableOpen() {
        FootballersTableScreenController footballersTableScreenController = new FootballersTableScreenController();
        footballersTableScreenController.setDependencies(currentUser, entityManager, mainScreenController);
        loadCenterScene("/fxml/footballersTableScreen.fxml", footballersTableScreenController);
    }

    @FXML
    void coachesTableOpen(){
        CoachesTableScreenController coachesTableScreenController = new CoachesTableScreenController();
        coachesTableScreenController.setDependencies(currentUser,entityManager,mainScreenController);
        loadCenterScene("/fxml/coachesTableScreen.fxml", coachesTableScreenController);
    }

    @FXML
    void teamsTableOpen() {
        TeamsTableScreenController teamsTableScreenController = new TeamsTableScreenController();
        teamsTableScreenController.setDependencies(currentUser, entityManager, mainScreenController);
        loadCenterScene("/fxml/teamsTable.fxml", teamsTableScreenController);
    }


    @FXML
    void logout() {
        LoginScreenController loginScreenController = new LoginScreenController();
        loginScreenController.setDependencies(null, entityManager, mainScreenController);
        loadCenterScene("/fxml/loginScreen.fxml", loginScreenController);
        mainScreenController.getBorderPane().setLeft(null);
        mainScreenController.getStage().setWidth(1000);
        mainScreenController.getStage().setHeight(600);

    }

    @FXML
    void fansTableOpen() {
        FansScreenTableController fansScreenTableController = new FansScreenTableController();
        fansScreenTableController.setDependencies(currentUser, entityManager, mainScreenController);
        loadCenterScene("/fxml/fansTableScreen.fxml", fansScreenTableController);
    }

    @FXML
    void resultTableOpen() {
        ResultTableScreenController resultTableScreenController = new ResultTableScreenController();
        resultTableScreenController.setDependencies(currentUser, entityManager, mainScreenController);
        loadCenterScene("/fxml/resultTableScreen.fxml", resultTableScreenController);
    }

    @FXML
    void openOption(){
        OptionScreenController optionScreenController = new OptionScreenController();
        optionScreenController.setDependencies(currentUser, entityManager,  mainScreenController);
        loadCenterScene("/fxml/optionScreen.fxml", optionScreenController);
    }

    @FXML
    void openManagment(){
        ManagementScreenController managementScreenController = new ManagementScreenController();
        managementScreenController.setEntityManager(entityManager);
        loadCenterScene("/fxml/managmentScreen.fxml", managementScreenController);
    }
}
