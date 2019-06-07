package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import pl.football.league.entities.Coach;
import pl.football.league.services.TableItemsShowService;

import java.util.Comparator;

public class CoachesTableScreenController extends TableItemsShowService {
    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label ageLabel;

    @FXML
    private GridPane gridPane;

    @FXML
    void initialize() {
        currentData = entityManager.createQuery("select C from Coach C", Coach.class).getResultList();
        setSorts();
        fillTable();
    }

    private void fillTable(){
        fillGridPane(gridPane, 3);
    }

    private void setSorts(){
        currentData.sort(Comparator.comparing(Coach::getSurname));

        ageLabel.setOnMouseClicked(event -> {
            currentData.sort((Object c1, Object c2) ->{
                if(((Coach)c1).getAge() == null)
                    return (((Coach)c2).getAge() == null ) ? 0 : -1;
                if(((Coach)c2).getAge() == null)
                    return 1;
                return ((Coach)c1).getAge().compareTo(((Coach)c2).getAge());
            });
            fillTable();
        });

        nameLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Coach::getName));
            fillTable();
        });

        surnameLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Coach::getSurname));
            fillTable();
        });
    }
}