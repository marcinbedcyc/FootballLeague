package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.football.league.entities.Coach;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.Alerts;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

public class AddTeamScreenController {
    private EntityManager entityManager;
    private Stage stage;
    private List<Coach> coaches;
    private List<Team> teams;
    private Team team;
    private Coach coach;

    @FXML
    private TextField nameTextField;

    @FXML
    private ComboBox<Coach> coachComboBox;

    @FXML
    private DatePicker creationDate;

    @FXML
    private Button acceptButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button addCoach;

    @FXML
    void addCoach() {
        Stage secondStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addWindows/addCoachScreen.fxml"));
        AddCoachScreenController addCoachScreenController = new AddCoachScreenController();
        addCoachScreenController.setDependecies(entityManager, secondStage);
        loader.setController(addCoachScreenController);
        Parent root;
        try {
            root = loader.load();
            secondStage.setScene(new Scene(root, 320, 600));
            secondStage.setMinHeight(600);
            secondStage.setMinWidth(320);
            secondStage.setTitle("Dodawanie Trenera");
            secondStage.showAndWait();
            coaches = entityManager.createQuery("select C from Coach  C", Coach.class).getResultList();
            if(addCoachScreenController.getCoach() != null) {
                coach = addCoachScreenController.getCoach();
            }
            setCoachComboBox();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addTeamAndBack() {
        team = new Team();
        String name;

        name = nameTextField.getText();

        if (!name.equals("")) {
            team.setName(nameTextField.getText());
        } else {
            Alert emptyField = Alerts.emptyField();
            emptyField.showAndWait();
            return;
        }

        if (coachComboBox.getValue() == null) {
            Alert noCoach = Alerts.noCoach();
            noCoach.showAndWait();
            return;
        } else {
            team.setCoach(coachComboBox.getValue());
        }

        team.setCreationDate(Date.valueOf(creationDate.getValue()));
        team.setPoints(0);
        team.setWins(0);
        team.setDraws(0);
        team.setLoses(0);
        team.setTeamFans(new HashSet<>());

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(team);
            entityManager.getTransaction().commit();
            back();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            Alert transactionFail = Alerts.transactionFail();
            transactionFail.showAndWait();
        }
    }

    @FXML
    void back() {
        stage.close();
    }

    @FXML
    void initialize() {
        coaches = entityManager.createQuery("select C from Coach  C", Coach.class).getResultList();
        teams = entityManager.createQuery("select T from Team T", Team.class).getResultList();
        setCoachComboBox();
        creationDate.setValue(LocalDate.now());
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDependecies(EntityManager entityManager, Stage stage) {
        setEntityManager(entityManager);
        setStage(stage);
    }

    private void setCoachComboBox(){
        coachComboBox.getItems().clear();
        boolean busyCoach;
        for (Coach c : coaches) {
            busyCoach = false;
            for (Team t : teams) {
                if (t.getCoach() == c)
                    busyCoach = true;
            }
            if (!busyCoach)
                coachComboBox.getItems().add(c);
        }
        if (coachComboBox.getItems().isEmpty()) {
            coachComboBox.setPromptText("Brak wolnych trenerów");
        }
    }

    public Team getTeam() {
        return team;
    }

    public Coach getCoach() {
        return coach;
    }
}
