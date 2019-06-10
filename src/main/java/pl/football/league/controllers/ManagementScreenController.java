package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pl.football.league.entities.*;
import pl.football.league.fxmlUtils.Alerts;
import pl.football.league.fxmlUtils.TableControls;
import pl.football.league.services.MainService;
import pl.football.league.threads.Buffer;
import pl.football.league.threads.ReceiverItemTask;

import javax.persistence.NoResultException;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Kontroler do pliku managmentScreen.fxml. Okno umożliwiające dodatkowe opcje administratorowi takie jak edytowanie elementów
 * w bazie danych, dodawanie i usuwanie.
 * @author Marcin Cyc
 * @see pl.football.league.services.MainService
 */
public class ManagementScreenController extends MainService {
    /**
     * Lista trenerów z bazy danych
     * @see pl.football.league.entities.Coach
     */
    private List<Coach> coaches;

    /**
     * Lista trenerów z bazy danych, którzy nie prowadzą żadnej drużyny
     * @see pl.football.league.entities.Coach
     */
    private List<Coach> freeCoaches;

    /**
     * Lista kibiców z bazy danych
     * @see pl.football.league.entities.Fan
     */
    private List<Fan> fans;

    /**
     * Lista piłkarzy z bazy danych
     * @see pl.football.league.entities.Footballer
     */
    private List<Footballer> footballers;

    /**
     * Lista drużyn z bazy danych
     * @see pl.football.league.entities.Team
     */
    private List<Team> teams;

    /**
     * Lista meczów z bazy danych
     * @see pl.football.league.entities.Match
     */
    private List<Match> matches;

    /**
     * GridPane'a z informacjami o trenerach z bazy danych
     * @see javafx.scene.layout.GridPane
     */
    @FXML
    private GridPane coachGridPane;


    /**
     * GridPane'a z informacjami o kibicach z bazy danych
     * @see javafx.scene.layout.GridPane
     */
    @FXML
    private GridPane fanGridPane;

    /**
     * GridPane'a z informacjami o piłkarzach z bazy danych
     * @see javafx.scene.layout.GridPane
     */
    @FXML
    private  GridPane footballerGridPane;

    /**
     * GridPane'a z informacjami o meczach z bazy danych
     * @see javafx.scene.layout.GridPane
     */
    @FXML
    private  GridPane matchGridPane;

    /**
     * GridPane'a z informacjami o drużynach z bazy danych
     * @see javafx.scene.layout.GridPane
     */
    @FXML
    private  GridPane teamGridPane;

    /**
     * Główny layout okna, który przechowuje wszystkie gridPane'y z danymi z bazy danych
     * @see javafx.scene.control.TabPane
     */
    @FXML
    private TabPane tabPane;

    /**
     * Inicjalizacja okna: uzupełnienie zakładki z trenerami danymi, przypisanie stylu .css do okna oraz zainicjalizowanie
     * pustej listy dla trenerów bez drużyny.
     */
    @FXML
    void initialize() {
        fillCoachesTable();
        tabPane.getStylesheets().add(this.getClass().getResource("/css/tab.css").toExternalForm());
        freeCoaches = new LinkedList<>();
    }

    /**
     * Jeśli lista coaches jest pusta to pobierane są dane z bazy danych. Ponadto uzupełniana jest zakładka trenerów danymi
     */
    @FXML
    void coachFill() {
        if(coaches == null)
            coaches = entityManager.createQuery("select C from Coach  C", Coach.class).getResultList();
        fillCoachesTable();
    }

    /**
     * Jeśli lista fans jest pusta to pobierane są dane z bazy danych. Ponadto uzupełniana jest zakładka kibiców danymi
     */
    @FXML
    void fanFill() {
        if(fans == null)
            fans = entityManager.createQuery("select F from Fan F", Fan.class).getResultList();
        fillFansTable();
    }

    /**
     * Jeśli lista footballers jest pusta to pobierane są dane z bazy danych. Jeśli lista teams jest pusta to pobierane
     * są dane z bazy danych. Ponadto uzupełniana jest zakładka piłkarzy danymi.
     */
    @FXML
    void footballerFill() {
        if(teams == null)
            teams = entityManager.createQuery("select T from Team T", Team.class).getResultList();
        if(footballers == null)
            footballers = entityManager.createQuery("select F from Footballer  F", Footballer.class).getResultList();
        fillFootballersTable();
    }

    /**
     * Jeśli lista matches jest pusta to pobierane są dane z bazy danych. Ponadto uzupełniana jest zakładka meczów danymi
     */
    @FXML
    void matchFill() {
        if(matches == null)
            matches = entityManager.createQuery("select M from  Match M", Match.class).getResultList();
        fillMatchesTable();
    }

    /**
     * Jeśli lista footballers  jest pusta to pobierane są dane z bazy danych. Jeśli lista teams  jest pusta to pobierane
     * są dane z bazy danych. Jeśli lista matches  jest pusta to pobierane są dane z bazy danych. Ponadto uzupełniana
     * jest zakładka drużyn danymi
     */
    @FXML
    void teamFill() {
        if(footballers == null)
            footballers = entityManager.createQuery("select F from Footballer  F", Footballer.class).getResultList();
        if(teams == null)
            teams = entityManager.createQuery("select T from Team T", Team.class).getResultList();
        if(matches == null)
            matches = entityManager.createQuery("select M from  Match M", Match.class).getResultList();
        fillTeamsTable();
    }

    /**
     * Otwiera nowe okno do dodania trenera do bazy danych. Dodanie do bazy odbbywa się w osobny wątku i działa współbieżnie.
     */
    @FXML
    void addCoach(){
        Buffer bufferAddCoach = new Buffer();
        Stage addCoachStage = new Stage();
        AddCoachScreenController addCoachScreenController = new AddCoachScreenController();
        ReceiverItemTask receiverAddingCoach = new ReceiverItemTask(bufferAddCoach);

        Thread thread = new Thread(receiverAddingCoach);
        thread.start();

        addCoachStage.setTitle("Dodawanie Trenera");
        addCoachScreenController.setDependencies(currentUser, entityManager,mainScreenController, null, addCoachStage, bufferAddCoach);
        loadNewStage("/fxml/addWindows/addCoachScreen.fxml", addCoachScreenController, addCoachStage);

        receiverAddingCoach.setOnSucceeded(event ->{
            if(addCoachScreenController.getCurrentData() != null)
                coaches.add((Coach)addCoachScreenController.getCurrentData());
            fillCoachesTable();
        });
    }

    /**
     * Otwiera nowe okno do dodania kibica do bazy danych. Dodanie do bazy odbbywa się w osobny wątku i działa współbieżnie.
     */
    @FXML
    void addFan() {
        Buffer bufferAddFan = new Buffer();
        Stage addFanStage = new Stage();
        ReceiverItemTask receiverAddingFan = new ReceiverItemTask(bufferAddFan);
        RegisterScreenController registerScreenController = new RegisterScreenController();

        Thread thread = new Thread(receiverAddingFan);
        thread.start();

        addFanStage.setTitle("Dodawanie Kibica");
        registerScreenController.setDependencies(currentUser, entityManager, mainScreenController, null, addFanStage, bufferAddFan);
        registerScreenController.setSeparatelyWindow();
        loadNewStage("/fxml/registerScreen.fxml", registerScreenController, addFanStage);

        receiverAddingFan.setOnSucceeded(event -> {
            if (registerScreenController.getCurrentData() != null)
                fans.add((Fan) registerScreenController.getCurrentData());
            fillFansTable();
        });
    }

    /**
     * Otwiera nowe okno do dodania piłkarza do bazy danych. Dodanie do bazy odbbywa się w osobny wątku i działa współbieżnie.
     */
    @FXML
    void addFootballer(){
        Buffer bufferAddFootbalelr = new Buffer();
        Stage addFootballerStage = new Stage();
        ReceiverItemTask receiverAddingFootballer = new ReceiverItemTask(bufferAddFootbalelr);
        AddFootballerScreenController addFootballerScreenController = new AddFootballerScreenController();

        Thread thread = new Thread(receiverAddingFootballer);
        thread.start();

        addFootballerStage.setTitle("Dodawanie Piłkarza");
        addFootballerScreenController.setDependencies(currentUser, entityManager,mainScreenController, null, addFootballerStage, bufferAddFootbalelr );
        loadNewStage("/fxml/addWindows/addFootballerScreen.fxml", addFootballerScreenController, addFootballerStage);

        receiverAddingFootballer.setOnSucceeded(event -> {
            if(addFootballerScreenController.getCurrentData() != null)
                footballers.add((Footballer)addFootballerScreenController.getCurrentData());
            fillFootballersTable();
        });
    }

    /**
     * Otwiera nowe okno do dodania meczu do bazy danych. Dodanie do bazy odbbywa się w osobny wątku i działa współbieżnie.
     */
    @FXML
    void addMatch(){
        Buffer bufferAddMatch = new Buffer();
        Stage addMatchStage = new Stage();
        AddMatchScreenController addMatchScreenController = new AddMatchScreenController();
        ReceiverItemTask receiverAddingMatch = new ReceiverItemTask(bufferAddMatch);

        Thread thread = new Thread(receiverAddingMatch);
        thread.start();

        addMatchStage.setTitle("Dodawanie Meczu");
        addMatchScreenController.setDependencies(currentUser, entityManager, mainScreenController, null, addMatchStage, bufferAddMatch);
        loadNewStage("/fxml/addWindows/addMatchScreen.fxml", addMatchScreenController, addMatchStage);

        receiverAddingMatch.setOnSucceeded(event -> {
            if (addMatchScreenController.getCurrentData() != null) {
                matches.add((Match) addMatchScreenController.getCurrentData());
                entityManager.refresh(((Match) addMatchScreenController.getCurrentData()).getMatchID().getHome());
                entityManager.refresh(((Match) addMatchScreenController.getCurrentData()).getMatchID().getAway());
            }
            fillMatchesTable();
        });
    }

    /**
     * Otwiera nowe okno do dodania drużyny do bazy danych. Dodanie do bazy odbbywa się w osobny wątku i działa współbieżnie.
     */
    @FXML
    void addTeam(){
        Buffer bufferAddTeam = new Buffer();
        Stage addTeamStage = new Stage();
        AddTeamScreenController addTeamScreenController = new AddTeamScreenController();
        ReceiverItemTask receiverItemAddingTeam = new ReceiverItemTask(bufferAddTeam);

        Thread thread = new Thread(receiverItemAddingTeam);
        thread.start();

        addTeamStage.setTitle("Dodawanie Drużyny");
        addTeamScreenController.setDependencies(currentUser, entityManager, mainScreenController, null, addTeamStage, bufferAddTeam);
        loadNewStage("/fxml/addWindows/addTeamScreen.fxml", addTeamScreenController, addTeamStage);

        receiverItemAddingTeam.setOnSucceeded(event -> {
            if(addTeamScreenController.getCurrentData() != null) {
                teams.add((Team)addTeamScreenController.getCurrentData());
            }
            if(addTeamScreenController.getCoach() != null ){
                coaches.add(addTeamScreenController.getCoach());
            }
            fillCoachesTable();
            fillTeamsTable();
        });
    }

    /**
     * Uzupełnia coachGridPane danymi o trenerach. Wiersz wygląda następująco: TextField z imieniem, TextField  z nazwiskiem,
     * ComboBox z wiekiem, przycisk "Zapisz" do zatwierdzenia zmian oraz przycisk "Usuń" do usunięcia elementu z bazy danych.
     */
    private void fillCoachesTable(){
        int row = 0;
        coachGridPane.getChildren().remove(0, coachGridPane.getChildren().size());
        Tooltip nameTooltip = new Tooltip("Imię trenera");
        nameTooltip.setStyle("-fx-font-size: 15px");
        Tooltip surnameTooltip = new Tooltip("Nazwisko trenera");
        surnameTooltip.setStyle("-fx-font-size: 15px");
        for (Coach c : coaches) {
            TextField name = new TextField(c.getName());
            name.setTooltip(nameTooltip);
            TextField surname = new TextField(c.getSurname());
            surname.setTooltip(surnameTooltip);
            ComboBox<Integer> age = TableControls.comboBoxAge(c.getAge());
            Button edit = TableControls.greenButton("Zapisz");
            edit.setOnMouseClicked(event -> {
                try {
                    entityManager.getTransaction().begin();
                    c.setName(name.getText());
                    c.setSurname(surname.getText());
                    if (age.getValue() == 0)
                        c.setAge(null);
                    else
                        c.setAge(age.getValue());
                    entityManager.getTransaction().commit();
                    Alerts.success().showAndWait();
                }
                catch(Exception e){
                    entityManager.getTransaction().rollback();
                    e.printStackTrace();
                    Alert transactionFail = Alerts.transactionFail();
                    transactionFail.showAndWait();
                }
            });
            Button delete = TableControls.redButton("Usuń");
            delete.setOnMouseClicked(event -> {
                String  hql = "select T from Team T where T.coach = :coach";
                try {
                    entityManager.createQuery(hql).setParameter("coach", c).getSingleResult();
                    Alert busyCoach = Alerts.busyCoach();
                    busyCoach.showAndWait();
                }
                catch(NoResultException e) {
                    try {
                        coaches.remove(c);
                        entityManager.getTransaction().begin();
                        entityManager.remove(c);
                        entityManager.getTransaction().commit();
                        fillCoachesTable();
                        Alerts.success().showAndWait();
                    }
                    catch(Exception exception){
                        entityManager.getTransaction().rollback();
                        exception.printStackTrace();
                        Alert transactionFail = Alerts.transactionFail();
                        transactionFail.showAndWait();
                    }
                }
            });
            coachGridPane.addRow(row, name, surname, age, edit, delete);
            row++;
        }
    }

    /**
     * Uzupełnia fanGridPane danymi o kibicach. Wiersz wygląda następująco: TextField z imieniem, TextField  z nazwiskiem,
     * TextField z hasłem, TextField z pseudonimem, ComboBox z wiekiem, przycisk "Zapisz" do zatwierdzenia zmian oraz
     * przycisk "Usuń" do usunięcia elementu z bazy danych.
     */
    private void fillFansTable(){
        int row = 0;
        fanGridPane.getChildren().remove(0, fanGridPane.getChildren().size());
        Tooltip nameTooltip = new Tooltip("Imię kibica");
        nameTooltip.setStyle("-fx-font-size: 15px");
        Tooltip surnameTooltip = new Tooltip("Nazwisko kibica");
        surnameTooltip.setStyle("-fx-font-size: 15px");
        Tooltip passwordTooltip = new Tooltip("Hasło kibica");
        passwordTooltip.setStyle("-fx-font-size: 15px");
        Tooltip nicknameTooltip = new Tooltip("Pseudonim kibica");
        nicknameTooltip.setStyle("-fx-font-size: 15px");
        for (Fan f : fans) {
            TextField name = new TextField(f.getName());
            name.setTooltip(nameTooltip);
            TextField surname = new TextField(f.getSurname());
            surname.setTooltip(surnameTooltip);
            TextField password = new TextField(f.getPassword());
            password.setTooltip(passwordTooltip);
            TextField nickname = new TextField(f.getNickname());
            if(f.getNickname().equals("admin"))
                nickname.setDisable(true);
            nickname.setTooltip(nicknameTooltip);
            ComboBox<Integer> age = TableControls.comboBoxAge(f.getAge());
            Button edit = TableControls.greenButton("Zapisz");
            edit.setOnMouseClicked(event -> {
                try {
                    entityManager.getTransaction().begin();
                    f.setName(name.getText());
                    f.setSurname(surname.getText());
                    if (age.getValue() == 0)
                        f.setAge(null);
                    else
                        f.setAge(age.getValue());
                    f.setPassword(password.getText());
                    f.setNickname(nickname.getText());
                    entityManager.getTransaction().commit();
                    Alerts.success().showAndWait();
                }
                catch (Exception e){
                    entityManager.getTransaction().rollback();
                    e.printStackTrace();
                    Alert transactionFail = Alerts.transactionFail();
                    transactionFail.showAndWait();
                }
            });
            Button delete = TableControls.redButton("Usuń");
            delete.setOnMouseClicked(event -> {
                try {
                    fans.remove(f);
                    entityManager.getTransaction().begin();
                    entityManager.remove(f);
                    entityManager.getTransaction().commit();
                    fillFansTable();
                    Alerts.success().showAndWait();
                }catch(Exception e){
                    entityManager.getTransaction().rollback();
                    e.printStackTrace();
                    Alert transactionFail = Alerts.transactionFail();
                    transactionFail.showAndWait();
                }

            });
            if(f.getNickname().equals("admin"))
                delete.setDisable(true);
            fanGridPane.addRow(row, name, surname, password, nickname, age, edit, delete);
            row++;
        }
    }

    /**
     * Uzupełnia footballerGridPane danymi o piłkarzach. Wiersz wygląda następująco: TextField z imieniem, TextField  z nazwiskiem,
     * ComboBox z pozycją na boisku, ComboBox z numerem na koszulce, ComboBox z drużyną, w której gra piłkarz, przycisk
     * "Zapisz" do zatwierdzenia zmian oraz przycisk "Usuń" do usunięcia elementu z bazy danych.
     */
    private void fillFootballersTable(){
        int row = 0;
        footballerGridPane.getChildren().remove(0, footballerGridPane.getChildren().size());
        List<String> positions = new LinkedList<>();
        positions.add("BR");
        positions.add("OB");
        positions.add("PO");
        positions.add("NA");
        Tooltip nameTooltip = new Tooltip("Imię piłkarza");
        nameTooltip.setStyle("-fx-font-size: 15px");
        Tooltip surnameTooltip = new Tooltip("Nazwisko piłkarza");
        surnameTooltip.setStyle("-fx-font-size: 15px");

        for (Footballer f : footballers) {
            TextField name = new TextField(f.getName());
            name.setTooltip(nameTooltip);
            TextField surname = new TextField(f.getSurname());
            surname.setTooltip(surnameTooltip);
            ComboBox<String> position = new ComboBox<>();
            position.getItems().addAll(positions);
            position.setValue(f.getPosition());
            position.setPromptText(f.getPosition());

            ComboBox<Integer> number = TableControls.comboBoxHundred(f.getNumber());
            if(f.getNumber() == null){
                number.setPromptText("0");
                number.setValue(0);
            }

            ComboBox<Team> team = new ComboBox<>();
            team.setMaxWidth(1.7976931348623157E308);
            team.getItems().addAll(teams);
            if(f.getTeam() != null) {
                team.setValue(f.getTeam());
                team.setPromptText(f.getTeam().getName());
            }
            else{
                team.setPromptText("Brak Druzyny");
            }

            Button edit = TableControls.greenButton("Zapisz");
            edit.setOnMouseClicked(event -> {
                try {
                    entityManager.getTransaction().begin();
                    f.setName(name.getText());
                    f.setSurname(surname.getText());
                    f.setPosition(position.getValue());
                    if (number.getValue() == 0)
                        f.setNumber(null);
                    else
                        f.setNumber(number.getValue());
                    f.setTeam(team.getValue());
                    entityManager.getTransaction().commit();
                    Alerts.success().showAndWait();
                }catch(Exception e){
                    entityManager.getTransaction().rollback();
                    e.printStackTrace();
                    Alert transactionFail = Alerts.transactionFail();
                    transactionFail.showAndWait();
                }
            });

            Button delete = TableControls.redButton("Usuń");
            delete.setOnMouseClicked(event -> {
                try {
                    footballers.remove(f);
                    entityManager.getTransaction().begin();
                    Set<Fan> fanSet = f.getFans();
                    entityManager.remove(f);
                    for (Fan fan : fanSet) {
                        entityManager.refresh(entityManager.find(Fan.class, fan.getFanID()));
                    }
                    entityManager.getTransaction().commit();
                    fillFootballersTable();
                    Alerts.success().showAndWait();
                }
                catch(Exception e){
                    entityManager.getTransaction().rollback();
                    e.printStackTrace();
                    Alert transactionFail = Alerts.transactionFail();
                    transactionFail.showAndWait();
                }
            });

            footballerGridPane.addRow(row, name, surname, position, number, team, edit, delete);
            row++;
        }
    }

    /**
     * Uzupełnia matchGridPane danymi o meczach. Wiersz wygląda następująco: Label z nazwą drużyna gospodarza, ComboBox
     * z wynikiem gospodarza, Label ":", ComboBox z wynikiem gościa, Label z nazwą drużyny gościa, DatePicker z datą meczu,
     * przycisk "Zapisz" do zatwierdzenia zmian oraz przycisk "Usuń" do usunięcia elementu z bazy danych.
     */
    private  void fillMatchesTable(){
        int row = 0;
        matchGridPane.getChildren().remove(0, matchGridPane.getChildren().size());
        for(Match m: matches){
            Label homeTeam = new Label(m.getMatchID().getHome().getName());
            Label awayTeam = new Label(m.getMatchID().getAway().getName());
            Label colon = new Label(":");
            colon.setAlignment(Pos.CENTER);

            ComboBox<Integer> resultHome = TableControls.comboBoxHundred(m.getResultHome());

            ComboBox<Integer> resultAway = TableControls.comboBoxHundred(m.getResultAway());

            DatePicker datePicker = new DatePicker();
            datePicker.setValue(m.getMatchDate().toLocalDate());

            Button edit = TableControls.greenButton("Zapisz");
            edit.setOnMouseClicked(event -> {
                try {
                    entityManager.getTransaction().begin();
                    m.setResultHome(resultHome.getValue());
                    m.setResultAway(resultAway.getValue());
                    m.setMatchDate(Date.valueOf(datePicker.getValue()));
                    entityManager.getTransaction().commit();
                    entityManager.refresh(m.getMatchID().getHome());
                    entityManager.refresh(m.getMatchID().getAway());
                    Alerts.success().showAndWait();
                }
                catch (Exception e){
                    entityManager.getTransaction().rollback();
                    e.printStackTrace();
                    Alert transactionFail = Alerts.transactionFail();
                    transactionFail.showAndWait();
                }
            });

            Button delete = TableControls.redButton("Usuń");
            delete.setOnMouseClicked(event -> {
                try {
                    matches.remove(m);
                    entityManager.getTransaction().begin();
                    Team home = m.getMatchID().getHome();
                    Team away = m.getMatchID().getAway();
                    entityManager.remove(m);
                    entityManager.getTransaction().commit();
                    entityManager.refresh(home);
                    entityManager.refresh(away);
                    fillMatchesTable();
                    Alerts.success().showAndWait();
                }
                catch(Exception e){
                    entityManager.getTransaction().rollback();
                    e.printStackTrace();
                    Alert transactionFail = Alerts.transactionFail();
                    transactionFail.showAndWait();
                }
            });

            matchGridPane.addRow(row, homeTeam, resultHome, colon, resultAway, awayTeam, datePicker,  edit, delete);
            row++;
        }
    }

    /**
     * Uzupełnia teamGridPane danymi o drużynach. Wiersz wygląda następująco: TextField z nazwą drużyny, DatePicker z datą
     * założenia drużyny, ComboBox z trenerem, który może prowadzić drużynę, przycisk "Zapisz" do zatwierdzenia zmian oraz
     * przycisk "Usuń" do usunięcia elementu z bazy danych.
     */
    private void fillTeamsTable(){
        int row = 0;
        teamGridPane.getChildren().remove(0, teamGridPane.getChildren().size());
        setFreeCoaches();
        for (Team t : teams){
            TextField teamName = new TextField(t.getName());
            DatePicker creationDate = new DatePicker();
            if (t.getCreationDate() != null){
                creationDate.setValue(t.getCreationDate().toLocalDate());
            }
            ComboBox<Coach> coachComboBox = new ComboBox<>();
            coachComboBox.setMaxWidth(1.7976931348623157E308);
            coachComboBox.getItems().addAll(freeCoaches);
            coachComboBox.getItems().add(t.getCoach());
            coachComboBox.setValue(t.getCoach());

            Button edit = TableControls.greenButton("Zapisz");
            edit.setOnMouseClicked(event -> {
                try {
                    entityManager.getTransaction().begin();
                    t.setName(teamName.getText());
                    t.setCoach(coachComboBox.getValue());
                    if (creationDate.getValue() != null) {
                        t.setCreationDate(Date.valueOf(creationDate.getValue()));
                    } else
                        t.setCreationDate(null);
                    entityManager.getTransaction().commit();
                    Alerts.success().showAndWait();
                }
                catch(Exception e){
                    entityManager.getTransaction().rollback();
                    e.printStackTrace();
                    Alert transactionFail = Alerts.transactionFail();
                    transactionFail.showAndWait();
                }
            });

            Button delete = TableControls.redButton("Usuń");
            delete.setOnMouseClicked(event -> {
                try {
                    entityManager.getTransaction().begin();
                    for (Footballer f : footballers) {
                        if(f.getTeam() != null)
                            if (f.getTeam().getTeamID() == t.getTeamID())
                                f.setTeam(null);
                    }
                    for(Match m : matches){
                        if(m.getMatchID().getHome().getTeamID() == t.getTeamID() || m.getMatchID().getAway().getTeamID() == t.getTeamID()) {
                            entityManager.remove(m);
                        }
                    }
                    entityManager.remove(t);
                    teams.remove(t);
                    entityManager.getTransaction().commit();
                    for(Fan f: t.getTeamFans())
                        entityManager.refresh(f);
                    fillTeamsTable();
                    matches = entityManager.createQuery("select M from  Match M", Match.class).getResultList();
                    Alerts.success().showAndWait();
                }
                catch(Exception e){
                    if(entityManager.getTransaction().isActive())
                        entityManager.getTransaction().rollback();
                    e.printStackTrace();
                    Alert transactionFail = Alerts.transactionFail();
                    transactionFail.showAndWait();
                }
                for(Team t1:teams) entityManager.refresh(t1);
            });

            teamGridPane.addRow(row, teamName, creationDate, coachComboBox, edit, delete);
            row++;
        }
    }

    /**
     * Uzupełnia listę freeCoaches trenerami, którzy nie prowadzą żadnej drużyny.
     */
    private void setFreeCoaches(){
        freeCoaches.clear();
        boolean busyCoach;
        for(Coach c : coaches){
            busyCoach = false;
            for(Team team2 : teams){
                if(team2.getCoach() == c )
                    busyCoach = true;
            }
            if(!busyCoach)
                freeCoaches.add(c);
        }
    }
}
