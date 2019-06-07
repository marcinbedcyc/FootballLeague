package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import pl.football.league.entities.Footballer;
import pl.football.league.services.TableItemsShowService;

import java.util.Comparator;

public class FootballersTableScreenController extends TableItemsShowService {
    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label teamLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private GridPane gridPane;

    @FXML
    void initialize() {
        currentData = entityManager.createQuery("select F from Footballer F", Footballer.class).getResultList();
        setSorts();
        fillTable();
    }

    private void fillTable(){
       fillGridPane(gridPane, 5);
    }

    private void setSorts() {
        currentData.sort(Comparator.comparing(Footballer::getSurname));

        surnameLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Footballer::getSurname));
            fillTable();
        });

        nameLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Footballer::getName));
            fillTable();
        });

        teamLabel.setOnMouseClicked(event->{
            currentData.sort((Object f1, Object f2) -> {
                if(((Footballer)f1).getTeam() == null)
                    return (((Footballer)f2).getTeam() == null ) ? 0 : -1;
                if(((Footballer)f2).getTeam() == null)
                    return 1;
                return ((Footballer)f1).getTeam().getName().compareTo(((Footballer)f2).getTeam().getName());
            });
            fillTable();
        });

        positionLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Footballer::getPosition));
            fillTable();
        });

        numberLabel.setOnMouseClicked(event -> {
            currentData.sort((Object f1, Object f2) -> {
                if(((Footballer)f1).getNumber() == null)
                    return (((Footballer)f2).getNumber() == null ) ? 0 : -1;
                if(((Footballer)f2).getNumber() == null)
                    return 1;
                return ((Footballer)f1).getNumber().compareTo(((Footballer)f2).getNumber());
            });
            fillTable();
        });
    }
}