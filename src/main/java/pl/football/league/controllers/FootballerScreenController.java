package pl.football.league.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Footballer;
import pl.football.league.fxmlUtils.TableControls;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Set;

public class FootballerScreenController {
    private Footballer footballer;
    private Fan currentUser;
    private EntityManager entityManager;

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
    private Button stopSupportButton;

    private Set<Fan>fans;

    @FXML
    void initialize() {
        name.setText(footballer.getName());
        surname.setText(footballer.getSurname());
        team.setText(footballer.getTeam().getName());
        position.setText(footballer.getPosition());
        shirtNumber.setText(String.valueOf(footballer.getNumber()));
        this.setFans();
    }

    @FXML
    public void supportFootballer(){
        entityManager.getTransaction().begin();
        footballer.getFans().add(currentUser);
        entityManager.getTransaction().commit();
        supportButton.setDisable(true);
        stopSupportButton.setDisable(false);
        this.setFans();
    }
    @FXML
    void stopSupport() {
        entityManager.getTransaction().begin();
        footballer.getFans().remove(currentUser);
        entityManager.getTransaction().commit();
        supportButton.setDisable(false);
        stopSupportButton.setDisable(true);
        this.setFans();
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

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setDependency(Footballer footballer, Fan currentUser, EntityManager entityManager){
        setCurrentUser(currentUser);
        setFootballer(footballer);
        setEntityManager(entityManager);
    }

    private void setFans(){
        fans = footballer.getFans();
        fansVBox.getChildren().remove(0, fansVBox.getChildren().size());
        for (Fan f:fans) {
            if (f.getFanID() == currentUser.getFanID()) {
                supportButton.setDisable(true);
                stopSupportButton.setDisable(false);
            }
            fansVBox.getChildren().add(TableControls.Label(100, f.getNickname()));
        }
    }
}

