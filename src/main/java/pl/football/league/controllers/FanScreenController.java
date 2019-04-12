package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Footballer;
import pl.football.league.entities.Team;

import javax.persistence.EntityManager;
import java.util.Set;

public class FanScreenController {
    private Fan fan;
    private EntityManager entityManager;

    @FXML
    private Label namelabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label ageLabel;

    @FXML
    private Label nicknameLabel;

    @FXML
    private VBox footballersVBox;

    @FXML
    private VBox teamsVBox;

    @FXML
    void initialize() {
        namelabel.setText(fan.getName());
        surnameLabel.setText(fan.getSurname());
        nicknameLabel.setText(fan.getNickname());
        String result = String.valueOf(fan.getAge());
        if(result == null)  result = "-";
        ageLabel.setText(result);

        for(Footballer f : fan.getSupportedFootballers()){
            footballersVBox.getChildren().add(new Label(f.getName() + " " + f.getSurname()));
        }

        for(Team t : fan.getSupportedTeams()){
            teamsVBox.getChildren().add(new Label(t.getName()));
        }

    }

    public void setFan(Fan fan) {
        this.fan = fan;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setDependencies(Fan fan, EntityManager entityManager){
        setFan(fan);
        setEntityManager(entityManager);
    }
}
