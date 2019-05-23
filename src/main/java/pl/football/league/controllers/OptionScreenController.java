package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Footballer;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.Alerts;
import pl.football.league.fxmlUtils.TableControls;

import javax.persistence.EntityManager;
import java.io.IOException;

public class OptionScreenController {
    private Fan currentUser;
    private EntityManager entityManager;
    private MainScreenController mainScreenController;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField nicknameTextField;

    @FXML
    private PasswordField oldPasswordTextFIeld;

    @FXML
    private PasswordField newPasswordTextField;

    @FXML
    private PasswordField newPasswordRepeatTextField;

    @FXML
    private TextField agetextField;

    @FXML
    private GridPane footballersGridPane;

    @FXML
    private GridPane teamsGridPane;

    @FXML
    void initialize() {
        setInfo();
        fillTables();
    }

    @FXML
    void edit() {
        boolean succes = true;
        entityManager.getTransaction().begin();
        currentUser.setName(nameTextField.getText());
        currentUser.setSurname(surnameTextField.getText());

        try {
            int age = Integer.parseUnsignedInt(agetextField.getText());
            currentUser.setAge(age);
        } catch (NumberFormatException e) {
            Alerts.wrongNumber().showAndWait();
            succes = false;
        }

        if (currentUser.getPassword().equals(oldPasswordTextFIeld.getText())) {
            if (newPasswordTextField.getText().isEmpty() || newPasswordRepeatTextField.getText().isEmpty()) {
                Alerts.emptyPasswordField().showAndWait();
                succes = false;
            } else {
                if (newPasswordTextField.getText().equals(newPasswordRepeatTextField.getText())) {
                    currentUser.setPassword(newPasswordTextField.getText());
                } else {
                    Alerts.diffrentPasswords().showAndWait();
                    succes = false;
                }
            }
        } else {
            if (!oldPasswordTextFIeld.getText().isEmpty()) {
                Alerts.wrongPassword().showAndWait();
                succes = false;
            }
        }

        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        try {
            currentUser.setNickname(nicknameTextField.getText());
            entityManager.getTransaction().commit();
        } catch (javax.persistence.RollbackException e) {
            Alert userAlreadyExistAlert = Alerts.wrongLoginAlert();
            userAlreadyExistAlert.showAndWait();
            entityManager.getTransaction().rollback();
            succes = false;
        }

        if (succes) {
            Alerts.success().showAndWait();
        }
    }

    public void setCurrentUser(Fan currentUser) {
        this.currentUser = currentUser;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    public void setDependencies(EntityManager entityManager, Fan currentUser, MainScreenController mainScreenController) {
        setEntityManager(entityManager);
        setCurrentUser(currentUser);
        setMainScreenController(mainScreenController);
    }

    private void setInfo() {
        nameTextField.setText(currentUser.getName());
        surnameTextField.setText(currentUser.getSurname());
        nicknameTextField.setText(currentUser.getNickname());
        String result;
        if (currentUser.getAge() != null) {
            result = String.valueOf(currentUser.getAge());
        } else {
            result = "-";
        }
        agetextField.setText(result);
    }

    private void fillTables() {
        int i = 0;
        footballersGridPane.getChildren().remove(0, footballersGridPane.getChildren().size());
        teamsGridPane.getChildren().remove(0, teamsGridPane.getChildren().size());

        if (currentUser.getSupportedFootballers() != null) {
            for (Footballer f : currentUser.getSupportedFootballers()) {
                Label footballerLabel = TableControls.LabelVGrow(100, f.getSurname());
                Label x = TableControls.LabelX(30);
                footballerLabel.setOnMouseClicked(event -> {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/footballerScreen.fxml"));

                    FootballerScreenController footballerScreenController = new FootballerScreenController();
                    footballerScreenController.setDependency(f, currentUser, entityManager, mainScreenController);

                    loader.setController(footballerScreenController);
                    tryLoader(loader);
                });
                x.setOnMouseClicked(event -> {
                    entityManager.getTransaction().begin();
                    f.getFans().remove(currentUser);
                    currentUser.getSupportedFootballers().remove(f);
                    entityManager.getTransaction().commit();
                    fillTables();
                });
                footballersGridPane.addRow(i, footballerLabel, x);
                i++;
            }
        }
        if (currentUser.getSupportedFootballers() != null) {
            i = 0;
            for (Team t : currentUser.getSupportedTeams()) {
                Label teamLabel = TableControls.LabelVGrow(200, t.getName());
                Label x = TableControls.LabelX(30);
                teamLabel.setOnMouseClicked(event -> {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/teamScreen.fxml"));

                    TeamScreenController teamScreenController = new TeamScreenController();
                    teamScreenController.setDependencies(t, entityManager, mainScreenController, currentUser);

                    loader.setController(teamScreenController);
                    tryLoader(loader);
                });
                x.setOnMouseClicked(event -> {
                    entityManager.getTransaction().begin();
                    t.getTeamFans().remove(currentUser);
                    currentUser.getSupportedTeams().remove(t);
                    entityManager.getTransaction().commit();
                    fillTables();
                });
                teamsGridPane.addRow(i, teamLabel, x);
                i++;
            }
        }
    }

    private void tryLoader(FXMLLoader loader) {
        try {
            Parent root = loader.load();
            mainScreenController.getBorderPane().setCenter(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

