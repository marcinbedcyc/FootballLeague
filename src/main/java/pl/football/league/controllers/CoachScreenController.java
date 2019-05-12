package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import pl.football.league.entities.Coach;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Team;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.IOException;

public class CoachScreenController {
    private Coach coach;
    private EntityManager entityManager;
    private MainScreenController mainScreenController;
    private Fan currentUser;

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
        nameLabel.setText(coach.getName());
        surnameLabel.setText(coach.getSurname());
        ageLabel.setText((coach.getAge() != null) ? String.valueOf(coach.getAge()) : "-");
        titleLabel.setText(coach.getName() + " " + coach.getSurname());

        try {
            Team team = (Team) entityManager.createQuery("select T from Team T where T.coach.id = ?1").setParameter(1,
                    coach.getCoachID()).getSingleResult();
            teamLabel.setText(team.getName());
            teamLabel.setOnMouseClicked(event -> {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/teamScreen.fxml"));
                TeamScreenController teamScreenController = new TeamScreenController();

                teamScreenController.setDependencies(team, entityManager, mainScreenController, currentUser);
                loader.setController(teamScreenController);
                tryLoader(loader);
            });
        }catch (NoResultException e) {
            teamLabel.setText("Brak druzyny");
            teamLabel.setCursor(Cursor.DEFAULT);
            teamLabel.setStyle("-fx-background-color: transparent; -fx-font-size: 20;  -fx-text-fill: forestgreen;  -fx-font-weight: bold;");
        }
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setCurrentUser(Fan currentUser) {
        this.currentUser = currentUser;
    }

    public void setDependencies(Coach coach, EntityManager entityManager, MainScreenController mainScreenController, Fan currentUser){
        setCoach(coach);
        setEntityManager(entityManager);
        setMainScreenController(mainScreenController);
        setCurrentUser(currentUser);
    }

    private void tryLoader(FXMLLoader loader){
        try {
            Parent root = loader.load();
            mainScreenController.getBorderPane().setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
