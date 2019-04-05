package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import pl.football.league.entities.Coach;
import pl.football.league.entities.Fan;
import pl.football.league.fxmlUtils.TableControls;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class CoachesTableScreenController {
    private EntityManager entityManager;
    private MainScreenController mainController;
    private Fan currentUser;

    @FXML
    private VBox vbox;

    @FXML
    void initialize() {
        entityManager.getTransaction().begin();
        List<Coach> coaches = entityManager.createQuery("select C from Coach C").getResultList();
        entityManager.getTransaction().commit();
        coaches.sort(Comparator.comparing(Coach::getSurname));
        HBox hbox;
        vbox.setPrefHeight((coaches.size() + 1) * 21);

        for (Coach c : coaches) {
            hbox = new HBox();
            hbox.setSpacing(0);
            hbox.setAlignment(Pos.CENTER);

            Label name = TableControls.LabelVGrow(150, c.getName());
            hbox.setHgrow(name, Priority.ALWAYS);
            name.setOnMouseClicked((MouseEvent event) -> {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/coachScreen.fxml"));
                CoachScreenController coachScreenController = new CoachScreenController();

                coachScreenController.setCoach(c);
                coachScreenController.setEntityManager(entityManager);
                loader.setController(coachScreenController);
                tryLoader(loader);
            });

            Label surname = TableControls.LabelVGrow(150, c.getSurname());
            hbox.setHgrow(surname, Priority.ALWAYS);
            surname.setOnMouseClicked(name.getOnMouseClicked());

            String result;
            if (c.getAge() == null) {
                result = "-";
            } else {
                result = String.valueOf(c.getAge());
            }
            Label age = TableControls.LabelVGrow(150, result);
            hbox.setHgrow(age, Priority.ALWAYS);

            hbox.getChildren().addAll(name, new Separator(Orientation.VERTICAL), surname, new Separator(Orientation.VERTICAL), age);
            vbox.getChildren().add(hbox);
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

    private void tryLoader(FXMLLoader loader) {
        try {
            Parent root = loader.load();
            mainController.getBorderPane().setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}