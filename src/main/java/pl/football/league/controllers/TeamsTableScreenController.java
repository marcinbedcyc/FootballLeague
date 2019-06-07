package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import pl.football.league.entities.Team;
import pl.football.league.services.TableItemsShowService;

import java.util.Comparator;

public class TeamsTableScreenController extends TableItemsShowService {

    @FXML
    private Label teamLabel;

    @FXML
    private Label coachLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private VBox vbox;

    @FXML
    private GridPane gridPane;

    @FXML
    void initialize() {
        currentData = entityManager.createQuery("select T from Team T").getResultList();
        setSorts();
        fillTable();
    }

    private void fillTable(){
        fillGridPane(gridPane, 3);
    }

    private void setSorts(){
        teamLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Team::getName));
            fillTable();
        });

        coachLabel.setOnMouseClicked(event -> {
            currentData.sort((Object o1, Object o2) -> ((Team)o1).getCoach().getSurname().compareTo(((Team)o2).getCoach().getSurname()));
            fillTable();
        });

        dateLabel.setOnMouseClicked(event -> {
            currentData.sort((Object o1, Object o2) ->{
                if(((Team)o1).getCreationDate() == null)
                    return (((Team)o2).getCreationDate() == null ) ? 0 : -1;
                if(((Team)o2).getCreationDate() == null)
                    return 1;
                return ((Team)o1).getCreationDate().compareTo(((Team)o2).getCreationDate());
            });
            fillTable();
        });
    }
}
