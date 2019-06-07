package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Footballer;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.TableControls;
import pl.football.league.services.ItemShowService;


public class FanScreenController extends ItemShowService {
    @FXML
    private Label titleLabel;

    @FXML
    private Label nameLabel;

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
        setFanInfo();
        setFootballers();
        setTeams();
    }

    private void setFanInfo(){
        titleLabel.setText(((Fan)currentData).getName() + " " + ((Fan)currentData).getSurname() + " ( " + ((Fan)currentData).getNickname() + " )");
        nameLabel.setText(((Fan)currentData).getName());
        surnameLabel.setText(((Fan)currentData).getSurname());
        nicknameLabel.setText(((Fan)currentData).getNickname());

        String result = String.valueOf(((Fan)currentData).getAge());
        if(result == null)  result = "-";
        ageLabel.setText(result);
    }

    private void setFootballers(){
        if(((Fan)currentData).getSupportedFootballers().isEmpty()){
            Label emptyLabel = TableControls.Label(150, "Brak wspieranych piłkarzy");
            emptyLabel.setMaxWidth(1.7976931348623157E308);
            footballersVBox.getChildren().add(emptyLabel);
        }
        else {
            for (Footballer f : ((Fan)currentData).getSupportedFootballers()) {
                Label footballerLabel = TableControls.LabelVGrow(150, f.getName() + " " + f.getSurname());
                footballerLabel.setOnMouseClicked(event -> {
                    FootballerScreenController footballerScreenController = new FootballerScreenController();
                    footballerScreenController.setDependencies(currentUser, entityManager, mainScreenController, f);
                    loadCenterScene("/fxml/footballerScreen.fxml", footballerScreenController);

                });
                footballersVBox.getChildren().add(footballerLabel);
            }
        }
    }

    private void setTeams(){
        if(((Fan)currentData).getSupportedTeams().isEmpty()){
            Label emptyLabel = TableControls.Label(150, "Brak wspieranych drużyn");
            emptyLabel.setMaxWidth(1.7976931348623157E308);
            teamsVBox.getChildren().add(emptyLabel);
        }
        else {
            for (Team t : ((Fan)currentData).getSupportedTeams()) {
                Label teamLabel = TableControls.LabelVGrow(150, t.getName());
                teamLabel.setOnMouseClicked(event -> {
                    TeamScreenController teamScreenController = new TeamScreenController();
                    teamScreenController.setDependencies(currentUser, entityManager, mainScreenController, t);
                    loadCenterScene("/fxml/teamScreen.fxml", teamScreenController);
                });
                teamsVBox.getChildren().add(teamLabel);
            }
        }
    }
}
