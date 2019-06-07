package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import pl.football.league.entities.Fan;
import pl.football.league.services.TableItemsShowService;

import java.util.Comparator;

public class FansScreenTableController extends TableItemsShowService {
    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label nicknameLabel;

    @FXML
    private Label ageLabel;

    @FXML
    private GridPane gridPane;

    @FXML
    void initialize() {
        currentData = entityManager.createQuery("select F from Fan F").getResultList();
        setSorts();
        fillTable();
    }

    private void fillTable(){
        fillGridPane(gridPane, 4);
    }

    private  void setSorts(){
        nameLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Fan::getName));
            fillTable();
        });

        nicknameLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Fan::getNickname));
            fillTable();
        });

        surnameLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Fan::getSurname));
            fillTable();
        });

        ageLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Fan::getAge));
            fillTable();
        });
    }
}
