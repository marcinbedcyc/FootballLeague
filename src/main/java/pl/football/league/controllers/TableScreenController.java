package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import pl.football.league.entities.Team;
import pl.football.league.services.TableItemsShowService;

import java.util.Comparator;

public class TableScreenController extends TableItemsShowService {

    @FXML
    private VBox vbox;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label teamLabel;

    @FXML
    private Label pointsLabel;

    @FXML
    private Label winsLabel;

    @FXML
    private Label drawsLabel;

    @FXML
    private Label losesLabel;

    @FXML
    private Label coachLabel;

    @FXML
    void initialize() {
        currentData = entityManager.createQuery("select t from Team t").getResultList();
        setSorts();
        fillTable();
    }

    private void fillTable() {
        fillGridPane(gridPane, 7);
    }

    private void setSorts(){
        currentData.sort((Object o1, Object o2)->((Team)o2).getPoints() - ((Team)o1).getPoints());


        teamLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Team::getName));
            fillTable();
        });

        pointsLabel.setOnMouseClicked(event -> {
            currentData.sort((Object o1, Object o2)->((Team)o2).getPoints() - ((Team)o1).getPoints());
            fillTable();
        });

        winsLabel.setOnMouseClicked(event -> {
            currentData.sort((Object o1, Object o2)->((Team)o2).getWins() - ((Team)o1).getWins());
            fillTable();
        });

        drawsLabel.setOnMouseClicked(event -> {
            currentData.sort((Object o1, Object o2)->((Team)o2).getDraws() - ((Team)o1).getDraws());
            fillTable();
        });

        losesLabel.setOnMouseClicked(event -> {
            currentData.sort((Object o1, Object o2)->((Team)o2).getLoses() - ((Team)o1).getLoses());
            fillTable();
        });

        coachLabel.setOnMouseClicked(event -> {
            currentData.sort((Object o1, Object o2)->((Team)o2).getCoach().getSurname().compareTo(((Team)o1).getCoach().getSurname()));
            fillTable();
        });
    }
}
