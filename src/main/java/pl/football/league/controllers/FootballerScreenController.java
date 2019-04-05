package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Footballer;
import pl.football.league.fxmlUtils.TableControls;

import java.util.Set;

public class FootballerScreenController {
    private Footballer footballer;
    private Fan currentUser;

    @FXML
    private VBox fansVBox;

    @FXML
    private Label name;

    @FXML
    private Label surname;

    @FXML
    private Label team;

    @FXML
    private Label position;

    @FXML
    private Label shirtNumber;

    @FXML
    private Button supportButton;

    @FXML
    void initialize() {
        name.setText(footballer.getName());
        surname.setText(footballer.getSurname());
        team.setText(footballer.getTeam().getName());
        position.setText(footballer.getPosition());
        shirtNumber.setText(String.valueOf(footballer.getNumber()));

        Set<Fan> fans = footballer.getFans();
        for (Fan f:fans) {
            if(f.getNickname().equals(currentUser.getNickname())){
                supportButton.setDisable(true);
            }
            fansVBox.getChildren().add(TableControls.Label(100, f.getNickname()));
        }


    }

    public Footballer getFootballer() {
        return footballer;
    }

    public void setFootballer(Footballer footballer) {
        this.footballer = footballer;
    }

    public Fan getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Fan currentUser) {
        this.currentUser = currentUser;
    }

    public void setDependency(Footballer footballer, Fan currentUser){
        setCurrentUser(currentUser);
        setFootballer(footballer);
    }
}

