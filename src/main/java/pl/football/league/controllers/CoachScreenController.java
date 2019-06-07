package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import pl.football.league.entities.Coach;
import pl.football.league.entities.Team;
import pl.football.league.services.ItemShowService;

import javax.persistence.NoResultException;

public class CoachScreenController extends ItemShowService {
    @FXML
    private Label nameLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label ageLabel;

    @FXML
    private Label teamLabel;

    @FXML
    void initialize() {
        nameLabel.setText(((Coach)currentData).getName());
        surnameLabel.setText(((Coach)currentData).getSurname());
        ageLabel.setText((((Coach)currentData).getAge() != null) ? String.valueOf(((Coach)currentData).getAge()) : "-");
        titleLabel.setText(((Coach)currentData).getName() + " " + ((Coach)currentData).getSurname());

        try {
            Team team = (Team) entityManager.createQuery("select T from Team T where T.coach.id = :param").setParameter("param",
                    ((Coach)currentData).getCoachID()).getSingleResult();
            teamLabel.setText(team.getName());
            teamLabel.setOnMouseClicked(event -> {
                TeamScreenController teamScreenController = new TeamScreenController();
                teamScreenController.setDependencies(currentUser, entityManager, mainScreenController, team);
                loadCenterScene("/fxml/teamScreen.fxml", teamScreenController);
            });
        }catch (NoResultException e) {
            teamLabel.setText("Brak druzyny");
            teamLabel.setCursor(Cursor.DEFAULT);
            teamLabel.setStyle("-fx-background-color: transparent; -fx-font-size: 20;  -fx-text-fill: forestgreen;  -fx-font-weight: bold;");
        }
    }
}
