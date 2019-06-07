package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Footballer;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.TableControls;
import pl.football.league.services.ItemShowService;

import java.util.List;

public class TeamScreenController extends ItemShowService {
    @FXML
    private Label nameLabel;

    @FXML
    private Label coachLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label pointsLabel;

    @FXML
    private Label winsLabel;

    @FXML
    private Label drawsLabel;

    @FXML
    private Label losesLabel;

    @FXML
    private VBox footbalersVBox;

    @FXML
    private VBox fansVBox;

    @FXML
    private Button supportButton;

    @FXML
    private Button stopSupportButton;

    private List<Footballer> footballers;

    @FXML
    void initialize() {
        footballers = entityManager.createQuery("select F from Footballer F", Footballer.class).getResultList();
        setTeamInfo();
        setFootballers();
        supportButton.setDisable(false);
        stopSupportButton.setDisable(true);
        setFans();
        coachLabel.setOnMouseClicked(event -> {
            CoachScreenController coachScreenController = new CoachScreenController();
            coachScreenController.setDependencies(currentUser, entityManager, mainScreenController, ((Team)currentData).getCoach());
            loadCenterScene("/fxml/coachScreen.fxml", coachScreenController);
        });
    }

    private void setFans() {
        fansVBox.getChildren().remove(0, fansVBox.getChildren().size());

        if (((Team)currentData).getTeamFans().isEmpty()) {
            Label emptyLabel = TableControls.Label(150, "Brak kibiców");
            emptyLabel.setMaxWidth(1.7976931348623157E308);
            fansVBox.getChildren().add(emptyLabel);
        } else {
            for (Fan f : ((Team)currentData).getTeamFans()) {
                Label fanLabel = TableControls.LabelVGrow(150, f.getNickname());
                fanLabel.setOnMouseClicked(event -> {
                    FanScreenController fanScreenController = new FanScreenController();
                    fanScreenController.setDependencies(f, entityManager, mainScreenController, currentUser);
                    loadCenterScene("/fxml/fanScreen.fxml", fanScreenController);
                });
                if (f.getFanID() == currentUser.getFanID()) {
                    supportButton.setDisable(true);
                    stopSupportButton.setDisable(false);
                }
                fansVBox.getChildren().add(fanLabel);
            }
        }
    }

    private void setFootballers() {
        boolean areFootaballers = false;

        for (Footballer f : footballers) {
            if (f.getTeam() != null && f.getTeam().getTeamID() == ((Team)currentData).getTeamID()) {
                areFootaballers = true;
                Label footballerLabel = TableControls.LabelVGrow(150, f.getName() + " " + f.getSurname());
                footballerLabel.setOnMouseClicked(event -> {
                    FootballerScreenController footballerScreenController = new FootballerScreenController();
                    footballerScreenController.setDependencies(currentUser, entityManager, mainScreenController, f);
                    loadCenterScene("/fxml/footballerScreen.fxml", footballerScreenController);
                });
                footbalersVBox.getChildren().add(footballerLabel);
            }
        }
        if (!areFootaballers) {
            Label emptyLabel = TableControls.Label(150, "Brak pilkarzy");
            emptyLabel.setMaxWidth(1.7976931348623157E308);
            footbalersVBox.getChildren().add(emptyLabel);
        }

    }

    @FXML
    void stopSupport() {
        entityManager.getTransaction().begin();
        ((Team)currentData).getTeamFans().remove(currentUser);
        currentUser.getSupportedTeams().remove(((Team)currentData));
        entityManager.getTransaction().commit();
        supportButton.setDisable(false);
        stopSupportButton.setDisable(true);
        this.setFans();
    }

    @FXML
    void support() {
        entityManager.getTransaction().begin();
        ((Team)currentData).getTeamFans().add(currentUser);
        currentUser.getSupportedTeams().add(((Team)currentData));
        entityManager.getTransaction().commit();
        supportButton.setDisable(true);
        stopSupportButton.setDisable(false);
        this.setFans();
    }

    private void setTeamInfo() {
        nameLabel.setText(((Team)currentData).getName());
        coachLabel.setText(((Team)currentData).getCoach().getName() + " " + ((Team)currentData).getCoach().getSurname());
        String result;
        if (((Team)currentData).getCreationDate() == null) {
            result = "-";
        } else {
            result = ((Team)currentData).getCreationDate().toString();
        }
        dateLabel.setText(result);
        pointsLabel.setText(String.valueOf(((Team)currentData).getPoints()));
        winsLabel.setText(String.valueOf(((Team)currentData).getWins()));
        drawsLabel.setText(String.valueOf(((Team)currentData).getDraws()));
        losesLabel.setText(String.valueOf(((Team)currentData).getLoses()));
    }
}
