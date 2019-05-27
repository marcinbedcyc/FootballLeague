package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pl.football.league.entities.*;
import pl.football.league.fxmlUtils.Alerts;
import pl.football.league.fxmlUtils.TableControls;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.IOException;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ManagementScreenController {
    private EntityManager entityManager;
    private List<Coach> coaches;
    private List<Coach> freeCoaches;
    private List<Fan> fans;
    private List<Footballer> footballers;
    private List<Team> teams;
    private List<Match> matches;

    @FXML
    private GridPane coachGridPane;

    @FXML
    private GridPane fanGridPane;

    @FXML
    private  GridPane footballerGridPane;

    @FXML
    private  GridPane matchGridPane;

    @FXML
    private  GridPane teamGridPane;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TabPane tabPane;

    @FXML
    void initialize() {
        fillCoachesTable();
        tabPane.getStylesheets().add(this.getClass().getResource("/css/tab.css").toExternalForm());
        freeCoaches = new LinkedList<>();
    }

    @FXML
    void coachFill() {
        if(coaches == null)
            coaches = entityManager.createQuery("select C from Coach  C", Coach.class).getResultList();
        fillCoachesTable();
    }

    @FXML
    void fanFill() {
        if(fans == null)
            fans = entityManager.createQuery("select F from Fan F", Fan.class).getResultList();
        fillFansTable();
    }

    @FXML
    void footballerFill() {
        if(teams == null)
            teams = entityManager.createQuery("select T from Team T", Team.class).getResultList();
        if(footballers == null)
            footballers = entityManager.createQuery("select F from Footballer  F", Footballer.class).getResultList();
        fillFootballersTable();
    }

    @FXML
    void matchFill() {
        if(matches == null)
            matches = entityManager.createQuery("select M from  Match M", Match.class).getResultList();
        fillMatchesTable();
    }

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

    @FXML
    void addCoach(){
        Stage secondStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addWindows/addCoachScreen.fxml"));
        AddCoachScreenController addCoachScreenController = new AddCoachScreenController();
        addCoachScreenController.setDependecies(entityManager, secondStage);
        loader.setController(addCoachScreenController);
        Parent root;
        try {
            root = loader.load();
            secondStage.setScene(new Scene(root, 320, 600));
            secondStage.setMinWidth(320);
            secondStage.setMinHeight(600);
            secondStage.setTitle("Dodawanie Trenera");
            secondStage.showAndWait();
            if(addCoachScreenController.getCoach() != null)
                coaches.add(addCoachScreenController.getCoach());
            fillCoachesTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addFan() {
        Stage secondStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registerScreen.fxml"));
        RegisterScreenController registerScreenController = new RegisterScreenController();
        registerScreenController.setEntityManager(entityManager);
        registerScreenController.setSeparatelyWindow();
        loader.setController(registerScreenController);
        Parent root;
        try {
            root = loader.load();
            secondStage.setScene(new Scene(root, 320, 600));
            secondStage.setMinWidth(320);
            secondStage.setMinHeight(600);
            secondStage.setTitle("Dodawanie Kibica");
            secondStage.showAndWait();
            if(registerScreenController.getNewUser() != null)
                fans.add(registerScreenController.getNewUser());
            fillFansTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addFootballer(){
        Stage secondStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addWindows/addFootballerScreen.fxml"));
        AddFootballerScreenController addFootballerScreenController = new AddFootballerScreenController();
        addFootballerScreenController.setDependecies(entityManager, secondStage);
        loader.setController(addFootballerScreenController);
        Parent root;
        try {
            root = loader.load();
            secondStage.setScene(new Scene(root, 320, 600));
            secondStage.setMinWidth(320);
            secondStage.setMinHeight(600);
            secondStage.setTitle("Dodawanie Piłkarza");
            secondStage.showAndWait();
            if(addFootballerScreenController.getFootballer() != null)
                footballers.add(addFootballerScreenController.getFootballer());
            fillFootballersTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addMatch(){
        Stage secondStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addWindows/addMatchScreen.fxml"));
        AddMatchScreenController addMatchScreenController = new AddMatchScreenController();
        addMatchScreenController.setDependecies(entityManager, secondStage);
        loader.setController(addMatchScreenController);
        Parent root;
        try {
            root = loader.load();
            secondStage.setScene(new Scene(root, 320, 600));
            secondStage.setMinWidth(320);
            secondStage.setMinHeight(600);
            secondStage.setTitle("Dodawanie Meczu");
            secondStage.showAndWait();
            if(addMatchScreenController.getMatch() != null)
                matches.add(addMatchScreenController.getMatch());
            fillMatchesTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addTeam(){
        Stage secondStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addWindows/addTeamScreen.fxml"));
        AddTeamScreenController addTeamScreenController = new AddTeamScreenController();
        addTeamScreenController.setDependecies(entityManager, secondStage);
        loader.setController(addTeamScreenController);
        Parent root;
        try {
            root = loader.load();
            secondStage.setScene(new Scene(root, 320, 600));
            secondStage.setMinWidth(320);
            secondStage.setMinHeight(600);
            secondStage.setTitle("Dodawanie ");
            secondStage.setTitle("Dodawanie Drużyny");
            secondStage.showAndWait();
            if(addTeamScreenController.getTeam() != null) {
                teams.add(addTeamScreenController.getTeam());
            }
            if(addTeamScreenController.getCoach() != null ){
                coaches.add(addTeamScreenController.getCoach());
            }
            fillCoachesTable();
            fillTeamsTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

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
            fanGridPane.addRow(row, name, surname, password, nickname, age, edit, delete);
            row++;
        }
    }

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

            matchGridPane.addRow(row, homeTeam, resultHome,colon, resultAway, awayTeam, datePicker,  edit, delete);
            row++;
        }
    }

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
            });

            teamGridPane.addRow(row, teamName, creationDate, coachComboBox, edit, delete);
            row++;
        }
    }

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
