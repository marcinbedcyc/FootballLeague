package pl.football.league.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Footballer;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.TableControls;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class FootballersTableScreenController {
    private EntityManager entityManager;
    private MainScreenController mainController;
    private Fan currentUser;

    @FXML
    private ListView<HBox> listView;

    @FXML
    void initialize() {
        List<Footballer> footballers = entityManager.createQuery("select F from Footballer F").getResultList();
        //ObservableList<Label> content = new LinkedList<Label>();
        footballers.sort(new Comparator<Footballer>() {
            @Override
            public int compare(Footballer o1, Footballer o2) {
                return o1.getSurname().compareTo(o2.getSurname());
            }
        });
        HBox hbox;

        for (Footballer f : footballers) {
            hbox = new HBox();
            hbox.setSpacing(0);
            hbox.setAlignment(Pos.CENTER);

            Label name = TableControls.LabelVGrow(150, f.getName());
            hbox.setHgrow(name, Priority.ALWAYS);
            name.setOnMouseClicked((MouseEvent event)->{
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/footballerScreen.fxml"));
                FootballerScreenController footballerScreenController = new FootballerScreenController();
                Footballer footballer1 = entityManager.find(Footballer.class, f.getFootballerID());

                footballerScreenController.setDependency(footballer1, currentUser, entityManager);
                loader.setController(footballerScreenController);
                tryLoader(loader);
            });

            Label surname = TableControls.LabelVGrow(150, f.getSurname());
            hbox.setHgrow(surname, Priority.ALWAYS);
            surname.setOnMouseClicked(name.getOnMouseClicked());

            Label teamLabel = TableControls.LabelVGrow(150, f.getTeam().getName());
            hbox.setHgrow(teamLabel, Priority.ALWAYS);
            teamLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/teamScreen.fxml"));
                    TeamScreenController teamScreenController = new TeamScreenController();
                    Team team = entityManager.find(Team.class, f.getTeam().getTeamID());

                    teamScreenController.setDependencies(team, entityManager, mainController, currentUser);
                    loader.setController(teamScreenController);
                    tryLoader(loader);
                }
            });

            Label number = TableControls.Label(50, String.valueOf(f.getNumber()));

            Label position = TableControls.Label(50, f.getPosition());

            hbox.getChildren().addAll(name, new Separator(Orientation.VERTICAL), surname, new Separator(Orientation.VERTICAL),
                    teamLabel, new Separator(Orientation.VERTICAL), number, new Separator(Orientation.VERTICAL), position);
            listView.getItems().add(hbox);
        }

    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }

    public void setCurrentUser(Fan currentUser) {
        this.currentUser = currentUser;
    }

    private void tryLoader(FXMLLoader loader){
        try {
            Parent root = loader.load();
            mainController.getBorderPane().setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}