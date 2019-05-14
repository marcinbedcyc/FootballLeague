package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import pl.football.league.entities.Match;
import pl.football.league.entities.MatchID;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.Alerts;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class AddMatchScreenController {
    private EntityManager entityManager;
    private Stage stage;
    private List<Team> teams;
    private Match match;

    @FXML
    private ComboBox<Team> homeTeamComboBox;

    @FXML
    private ComboBox<Integer> resultHomeTeamComboBox;

    @FXML
    private ComboBox<Team> awayTeamComboBox;

    @FXML
    private ComboBox<Integer> resultAwayTeamComboBox;

    @FXML
    private DatePicker dateMatch;

    @FXML
    private Button acceptButton;

    @FXML
    private Button cancelButton;

    @FXML
    void addMatchAndBack() {
        match = new Match();
        MatchID id = new MatchID();

        if(homeTeamComboBox.getValue() != null)
            id.setHome(homeTeamComboBox.getValue());
        else{
            Alert noHomeTeam = Alerts.noHomeTeam();
            noHomeTeam.showAndWait();
            return;
        }

        if(awayTeamComboBox.getValue() != null)
            id.setAway(awayTeamComboBox.getValue());
        else{
            Alert noAwayTeam = Alerts.noAwayTeam();
            noAwayTeam.showAndWait();
            return;
        }

        if(homeTeamComboBox.getValue().getTeamID() == awayTeamComboBox.getValue().getTeamID()){
            Alert sameTeams = Alerts.sameTeams();
            sameTeams.showAndWait();
            return;
        }

        match.setMatchID(id);
        match.setResultAway(resultAwayTeamComboBox.getValue());
        match.setResultHome(resultHomeTeamComboBox.getValue());
        match.setMatchDate(Date.valueOf(dateMatch.getValue()));

        try{
            entityManager.getTransaction().begin();
            entityManager.persist(match);
            entityManager.getTransaction().commit();
            entityManager.refresh(match.getMatchID().getHome());
            entityManager.refresh(match.getMatchID().getAway());
            back();
        }catch (Exception e ){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            Alert transactionFail = Alerts.transactionFail();
            transactionFail.showAndWait();
        }
    }

    @FXML
    void back() {
        stage.close();
    }

    @FXML
    void initialize() {
        teams = entityManager.createQuery("select T from Team T", Team.class).getResultList();

        homeTeamComboBox.getItems().addAll(teams);
        awayTeamComboBox.getItems().addAll(teams);

        for(int i = 0; i <= 99 ; i++){
            resultHomeTeamComboBox.getItems().add(i);
            resultAwayTeamComboBox.getItems().add(i);
        }

        resultHomeTeamComboBox.setValue(0);
        resultAwayTeamComboBox.setValue(0);

        dateMatch.setValue(LocalDate.now());
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public  void setDependecies(EntityManager entityManager, Stage stage){
        setEntityManager(entityManager);
        setStage(stage);
    }

    public Match getMatch() {
        return match;
    }
}
