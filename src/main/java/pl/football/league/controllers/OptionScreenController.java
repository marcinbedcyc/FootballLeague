package pl.football.league.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Footballer;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.Alerts;
import pl.football.league.fxmlUtils.TableControls;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;

public class OptionScreenController {
    private Fan currentUser;
    private EntityManager entityManager;

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
    private VBox supportedTeamsVBox;

    @FXML
    void initialize() {
        nameTextField.setText(currentUser.getName());
        surnameTextField.setText(currentUser.getSurname());
        nicknameTextField.setText(currentUser.getNickname());
        String result;
        if(currentUser.getAge() != null ){
            result = String.valueOf(currentUser.getAge());
        }
        else{
            result = "-";
        }
        agetextField.setText(result);
        int i =0;

        for (Footballer f : currentUser.getSupportedFootballers()){
            Label l1 = new Label(f.getSurname());
            Label x = TableControls.LabelVGrow(30, "X");
            x.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    entityManager.getTransaction().begin();
                    f.getFans().remove(currentUser);
                    currentUser.getSupportedFootballers().remove(f);
                    entityManager.getTransaction().commit();
                }
            });
            l1.setMaxWidth(123456);
            l1.setAlignment(Pos.CENTER);
            footballersGridPane.addRow(i, l1, x);
            i++;
        }
        i=0;
        for(Team t : currentUser.getSupportedTeams()){
            supportedTeamsVBox.getChildren().add(TableControls.Label(200, t.getName()));
        }
    }

    @FXML
    void edit(){
        entityManager.getTransaction().begin();
        currentUser.setName(nameTextField.getText());
        currentUser.setSurname(surnameTextField.getText());
        try {
            int age = Integer.parseInt(agetextField.getText());
            currentUser.setAge(age);
        }
        catch(NumberFormatException e){
            System.out.println("Zla liczba");
        }
        if(currentUser.getPassword().equals(oldPasswordTextFIeld.getText())) {
            if (newPasswordTextField.getText().isEmpty() || newPasswordRepeatTextField.getText().isEmpty()) {
                    Alerts.emptyPasswordField().showAndWait();
                }
                else {
                    if (newPasswordTextField.getText().equals(newPasswordRepeatTextField.getText())) {
                        currentUser.setPassword(newPasswordTextField.getText());
                    }
                    else {
                        Alerts.diffrentPasswords().showAndWait();
                    }
                }
            }
        else {
            if(!oldPasswordTextFIeld.getText().isEmpty())
                Alerts.wrongPassword().showAndWait();
        }

        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        try {
            currentUser.setNickname(nicknameTextField.getText());
            entityManager.getTransaction().commit();
        }
        catch(javax.persistence.RollbackException e){
            Alert userAlreadyExistAlert = Alerts.wrongLoginAlert();
            userAlreadyExistAlert.showAndWait();
            entityManager.getTransaction().rollback();
        }
    }

    public void setCurrentUser(Fan currentUser) {
        this.currentUser = currentUser;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setDependencies(EntityManager entityManager, Fan currentUser){
        setEntityManager(entityManager);
        setCurrentUser(currentUser);
    }
}

