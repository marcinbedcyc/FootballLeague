package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.football.league.entities.Coach;
import pl.football.league.entities.Team;

import javax.persistence.EntityManager;

public class CoachScreenController {
    private Coach coach;
    private EntityManager entityManager;

    @FXML
    private Label nameLabel;

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
        ageLabel.setText(String.valueOf(coach.getAge()));

        Team team = (Team) entityManager.createQuery("select T from Team T where T.coach.id = ?1").setParameter(1,
                coach.getCoachID()).getSingleResult();
        teamLabel.setText(team.getName());
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setDependencies(Coach coach, EntityManager entityManager){
        setCoach(coach);
        setEntityManager(entityManager);
    }
}
