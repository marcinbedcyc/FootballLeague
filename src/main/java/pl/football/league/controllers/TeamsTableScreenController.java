package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.TableControls;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TeamsTableScreenController {
    private EntityManager entityManager;
    private MainScreenController mainScreenController;
    private Fan currentUser;
    private List<Team> teams;

    @FXML
    private Label teamLabel;

    @FXML
    private Label coachLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private VBox vbox;

    @FXML
    private GridPane gridPane;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    public void setCurrentUser(Fan currentUser) {
        this.currentUser = currentUser;
    }

    @FXML
    void initialize() {
        setSorts();
        fillTable();
    }

    public void setDependencies(Fan currentUser, EntityManager entityManager, MainScreenController mainScreenController){
        setCurrentUser(currentUser);
        setEntityManager(entityManager);
        setMainScreenController(mainScreenController);
    }

    private void tryLoader(FXMLLoader loader){
        try {
            Parent root = loader.load();
            mainScreenController.getBorderPane().setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void fillTable(){
        int i = 0;

        gridPane.getChildren().remove(0, gridPane.getChildren().size());
        for(Team t: teams){
            Label nameLabel = TableControls.LabelVGrow(200,t.getName());
            gridPane.setHgrow(nameLabel, Priority.ALWAYS);
            gridPane.setVgrow(nameLabel, Priority.ALWAYS);
            nameLabel.setOnMouseClicked(event ->{
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/teamScreen.fxml"));
                TeamScreenController teamScreenController = new TeamScreenController();

                teamScreenController.setDependencies(t,entityManager, mainScreenController, currentUser);
                loader.setController(teamScreenController);

                tryLoader(loader);
            });

            Label coachLabel = TableControls.LabelVGrow(200, t.getCoach().getName() + " " + t.getCoach().getSurname());
            gridPane.setHgrow(coachLabel, Priority.ALWAYS);
            gridPane.setVgrow(coachLabel, Priority.ALWAYS);
            coachLabel.setOnMouseClicked(event ->{
                FXMLLoader loader =  new FXMLLoader(this.getClass().getResource("/fxml/coachScreen.fxml"));
                CoachScreenController coachScreenController = new CoachScreenController();

                coachScreenController.setDependencies(t.getCoach(),entityManager, mainScreenController, currentUser);
                loader.setController(coachScreenController);

                tryLoader(loader);

            });

            Date data = t.getCreationDate();
            String result;
            if(data == null)
                result = "-";
            else
                result = data.toString();

            Label dateLabel = TableControls.Label(200, result);
            dateLabel.setMaxWidth(1.7976931348623157E308);
            dateLabel.setMaxHeight(1.7976931348623157E308);
            gridPane.setHgrow(dateLabel, Priority.ALWAYS);
            gridPane.setVgrow(dateLabel, Priority.ALWAYS);

            gridPane.addRow(i, nameLabel, coachLabel, dateLabel);
            i++;
        }
    }

    private void setSorts(){
        teams = entityManager.createQuery("select T from Team T").getResultList();

        teamLabel.setOnMouseClicked(event -> {
            teams.sort(Comparator.comparing(Team::getName));
            fillTable();
        });

        coachLabel.setOnMouseClicked(event -> {
            teams.sort((Team o1, Team o2) -> {
                return o1.getCoach().getSurname().compareTo(o2.getCoach().getSurname());
            });
            fillTable();
        });

        /*dateLabel.setOnMouseClicked(event -> {
            teams.sort(Comparator.comparing(Team::getCreationDate));
            fillTable();
        });*/
    }
}
