package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.TableControls;

import javax.persistence.EntityManager;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class TeamsTableScreenController {
    private EntityManager entityManager;
    private MainScreenController mainScreenController;
    private Fan cuurentUser;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    public void setCuurentUser(Fan cuurentUser) {
        this.cuurentUser = cuurentUser;
    }

    @FXML
    private VBox vbox;

    @FXML
    void initialize() {
        List<Team> teams = entityManager.createQuery("select T from Team T").getResultList();

        HBox hBox;
        for(Team t: teams){
            hBox = new HBox();
            hBox.setSpacing(0);
            hBox.setAlignment(Pos.CENTER);

            Label nameLabel = TableControls.LabelVGrow(200,t.getName());
            hBox.setHgrow(nameLabel, Priority.ALWAYS);
            nameLabel.setOnMouseClicked((MouseEvent)->{
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/teamScreen.fxml"));
                TeamScreenController teamScreenController = new TeamScreenController();

                teamScreenController.setDependencies(t,entityManager, mainScreenController, cuurentUser);
                loader.setController(teamScreenController);

                tryLoader(loader);
            });
            Label coachLabel = TableControls.LabelVGrow(200, t.getCoach().getName() + " " + t.getCoach().getSurname());
            hBox.setHgrow(coachLabel, Priority.ALWAYS);
            coachLabel.setOnMouseClicked((MouseEvent)->{
                FXMLLoader loader =  new FXMLLoader(this.getClass().getResource("/fxml/coachScreen.fxml"));
                CoachScreenController coachScreenController = new CoachScreenController();

                coachScreenController.setDependencies(t.getCoach(),entityManager);
                loader.setController(coachScreenController);

                tryLoader(loader);

            });
            Date data = t.getCreationDate();
            String result;
            if(data == null)
                result = "-";
            else
                result = data.toString();
            Label dateLabel = TableControls.LabelVGrow(200, result);
            hBox.setHgrow(dateLabel, Priority.ALWAYS);
            
            hBox.getChildren().addAll(nameLabel, new Separator(Orientation.VERTICAL), coachLabel, new Separator(Orientation.VERTICAL),
                    dateLabel);
            vbox.getChildren().add(hBox);
        }
    }

    public void setDependencies(Fan currentUser, EntityManager entityManager, MainScreenController mainScreenController){
        setCuurentUser(currentUser);
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
}
