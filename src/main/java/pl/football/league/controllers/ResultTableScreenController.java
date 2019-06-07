package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import pl.football.league.entities.Match;
import pl.football.league.services.TableItemsShowService;

import java.util.Comparator;

public class ResultTableScreenController extends TableItemsShowService {
    @FXML
    private GridPane gridPane;

    @FXML
    private Label homeLabel;

    @FXML
    private Label awayLabel;

    @FXML
    private Label dateLabel;

    @FXML
    void initialize() {
        currentData = entityManager.createQuery("select M from Match M").getResultList();
        setSorts();
        fillTable();
    }

    private void fillTable(){
        fillGridPane(gridPane, 4);
    }

    private void setSorts(){
        homeLabel.setOnMouseClicked(event -> {
            currentData.sort((Object m1, Object m2) -> {
                return ((Match)m1).getMatchID().getHome().getName().compareTo(((Match)m2).getMatchID().getHome().getName());
            });
            fillTable();
        });

        awayLabel.setOnMouseClicked(event -> {
            currentData.sort((Object m1, Object m2) -> {
                return ((Match)m1).getMatchID().getAway().getName().compareTo(((Match)m2).getMatchID().getAway().getName());
            });
            fillTable();
        });

        dateLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Match::getMatchDate));
            fillTable();
        });
    }
}