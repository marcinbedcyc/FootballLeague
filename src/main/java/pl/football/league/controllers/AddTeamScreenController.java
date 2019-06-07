package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.football.league.entities.Coach;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.Alerts;
import pl.football.league.services.ItemAddService;
import pl.football.league.threads.Buffer;
import pl.football.league.threads.ReceiverItemTask;

import javax.persistence.NoResultException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

public class AddTeamScreenController extends ItemAddService {
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
        Buffer bufferAddCoach = new Buffer();
        Stage secondStage = new Stage();

        ReceiverItemTask receiverAddingCoachFinish = new ReceiverItemTask(bufferAddCoach);
        Thread t2 = new Thread(receiverAddingCoachFinish);
        t2.start();

        AddCoachScreenController addCoachScreenController = new AddCoachScreenController();
        addCoachScreenController.setDependencies(null, entityManager, null, null, secondStage, bufferAddCoach);
        secondStage.setTitle("Dodawanie Trenera");
        loadNewStage("/fxml/addWindows/addCoachScreen.fxml", addCoachScreenController, secondStage);

        receiverAddingCoachFinish.setOnSucceeded(event -> {
            if(addCoachScreenController.getCurrentData() != null) {
                coach = (Coach)addCoachScreenController.getCurrentData();
            }
            setCoachComboBox(entityManager.createQuery("select C from Coach  C", Coach.class).getResultList());
        });
    }

    @FXML
    void addTeamAndBack() {
        currentData = new Team();
        String name;

        name = nameTextField.getText();
        try {
            entityManager.createQuery("select T from Team T where T.name LIKE :param").setParameter("param", name).getSingleResult();
            Alerts.sameTeamName().showAndWait();
            return;
        }catch (NoResultException e){
            System.out.println("ok");
        }

        if (!name.equals("")) {
            ((Team)currentData).setName(nameTextField.getText());
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
            ((Team)currentData).setCoach(coachComboBox.getValue());
        }

        ((Team)currentData).setCreationDate(Date.valueOf(creationDate.getValue()));
        ((Team)currentData).setPoints(0);
        ((Team)currentData).setWins(0);
        ((Team)currentData).setDraws(0);
        ((Team)currentData).setLoses(0);
        ((Team)currentData).setTeamFans(new HashSet<>());

        addItem(currentData);
        back();
    }

    @FXML
    void back() {
        if(isCanceled)
            buffer.put();
        stage.close();
    }

    @FXML
    void initialize() {
        setCoachComboBox(entityManager.createQuery("select C from Coach  C", Coach.class).getResultList());
        creationDate.setValue(LocalDate.now());
    }

    private void setCoachComboBox(List<Coach> coaches){
        coachComboBox.getItems().clear();
        List<Team>teams = entityManager.createQuery("select T from Team T", Team.class).getResultList();
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

    public Coach getCoach() {
        return coach;
    }
}
