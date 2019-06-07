package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import pl.football.league.entities.Match;
import pl.football.league.entities.MatchID;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.Alerts;
import pl.football.league.services.ItemAddService;

import javax.persistence.NoResultException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class AddMatchScreenController extends ItemAddService {
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
        currentData = new Match();
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

        ((Match)currentData).setMatchID(id);
        ((Match)currentData).setResultAway(resultAwayTeamComboBox.getValue());
        ((Match)currentData).setResultHome(resultHomeTeamComboBox.getValue());
        ((Match)currentData).setMatchDate(Date.valueOf(dateMatch.getValue()));

        try {
            entityManager.createQuery("select M from Match  M where M.matchID.home = :home AND M.matchID.away = :away")
                    .setParameter("home", homeTeamComboBox.getValue())
                    .setParameter("away", awayTeamComboBox.getValue())
                    .getSingleResult();
            currentData = null;
            Alerts.matchInBase().showAndWait();
            return;
        }
        catch (NoResultException e){}

        addItem(currentData);
        back();
    }

    @FXML
    void back() {
        stage.close();
    }

    @FXML
    void initialize() {
        List<Team> teams = entityManager.createQuery("select T from Team T", Team.class).getResultList();

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
}
