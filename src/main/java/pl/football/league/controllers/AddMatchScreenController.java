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

/**
 * Kontroler do pliku addMatchScreen.fxml
 * @author Marcin Cyc
 * @see pl.football.league.services.ItemAddService
 */
public class AddMatchScreenController extends ItemAddService {
    /**
     * ComboBox z drużynami do wyboru gospodarza meczu
     * @see javafx.scene.control.ComboBox
     */
    @FXML
    private ComboBox<Team> homeTeamComboBox;

    /**
     * ComboBox z wynikami do wyboru dla gopodarza meczu
     * @see javafx.scene.control.ComboBox
     */
    @FXML
    private ComboBox<Integer> resultHomeTeamComboBox;

    /**
     * ComboBox z drużynami do wyboru gościa meczu
     * @see javafx.scene.control.ComboBox
     */
    @FXML
    private ComboBox<Team> awayTeamComboBox;

    /**
     * ComboBox z z wynikami do wyboru dla gościa meczu
     * @see javafx.scene.control.ComboBox
     */
    @FXML
    private ComboBox<Integer> resultAwayTeamComboBox;

    /**
     * Wybór daty meczu
     * @see javafx.scene.control.DatePicker
     */
    @FXML
    private DatePicker dateMatch;

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
     * Zaakceptowanie podanych danych, próba dodania meczu do bazy danych  oraz w przypadku sukcesu zamknięcie okna.
     * Zostaje utworzony nowy obiekt Match i dane z poszczególnych pól kontrolnych przekazywane są do obiektu. Wyświetlany
     * jest pierwszy napotkany błąd w formie komunikatu (Alert). Weryfikowane są: wybór gospodarza meczu, wybór gościa meczu,
     * wybór różnych drużyn oraz sprawdzenie czy mecz nie jest w bazie danych.
     * @see pl.football.league.fxmlUtils.Alerts
     */
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
     * Inicjalizacja okna: ustawienie comboBox'ów oraz daty meczu na bieżącą. Ustawienie akcji po zamknięciu okna.
     */
    @FXML
    void initialize() {
        stage.setOnCloseRequest(event -> back());
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
