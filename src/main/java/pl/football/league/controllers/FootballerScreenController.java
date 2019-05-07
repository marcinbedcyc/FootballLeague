package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Footballer;
import pl.football.league.fxmlUtils.TableControls;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Set;

public class FootballerScreenController {
    private Footballer footballer;
    private Fan currentUser;
    private EntityManager entityManager;
    private MainScreenController mainScreenController;
    private Set<Fan>fans;

    @FXML
    private VBox fansVBox;

    @FXML
    private Label titleLabel;

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

    @FXML
    void initialize() {
        fans = footballer.getFans();

        supportButton.setDisable(false);
        stopSupportButton.setDisable(true);

        setFootballerInfo();
        setFans();


        team.setOnMouseClicked(event -> {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/teamScreen.fxml"));
            TeamScreenController teamScreenController = new TeamScreenController();

            teamScreenController.setDependencies(footballer.getTeam(), entityManager, mainScreenController, currentUser);
            loader.setController(teamScreenController);
            tryLoader(loader);
        });
    }

    @FXML
    public void support(){
        entityManager.getTransaction().begin();
        footballer.getFans().add(currentUser);
        currentUser.getSupportedFootballers().add(footballer);
        entityManager.getTransaction().commit();
        supportButton.setDisable(true);
        stopSupportButton.setDisable(false);
        this.setFans();
    }
    @FXML
    void stopSupport() {
        entityManager.getTransaction().begin();
        footballer.getFans().remove(currentUser);
        currentUser.getSupportedFootballers().remove(footballer);
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

    public void setDependency(Footballer footballer, Fan currentUser, EntityManager entityManager, MainScreenController mainScreenController){
        setCurrentUser(currentUser);
        setFootballer(footballer);
        setEntityManager(entityManager);
        setMainScreenController(mainScreenController);
    }

    private void setFans(){
        fansVBox.getChildren().remove(0, fansVBox.getChildren().size());
        if(fans.isEmpty()){
            Label emptyLabel = TableControls.Label(150, "Brak Kibiców");
            emptyLabel.setMaxWidth(1.7976931348623157E308);
            fansVBox.getChildren().add(emptyLabel);
        }
        else {
            for (Fan f : fans) {
                if (f.getFanID() == currentUser.getFanID()) {
                    supportButton.setDisable(true);
                    stopSupportButton.setDisable(false);
                }
                Label fanLabel = TableControls.LabelVGrow(150, f.getNickname());
                fanLabel.setOnMouseClicked(event -> {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/fanScreen.fxml"));
                    FanScreenController fanScreenController = new FanScreenController();

                    fanScreenController.setDependencies(f, entityManager, mainScreenController, currentUser);
                    loader.setController(fanScreenController);
                    tryLoader(loader);
                });
                fansVBox.getChildren().add(fanLabel);
            }
        }
    }

    private void setFootballerInfo(){
        name.setText(footballer.getName());
        surname.setText(footballer.getSurname());
        team.setText(footballer.getTeam().getName());
        position.setText(footballer.getPosition());
        shirtNumber.setText(String.valueOf(footballer.getNumber()));
        titleLabel.setText(footballer.getName() + " " +  footballer.getSurname());
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
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

