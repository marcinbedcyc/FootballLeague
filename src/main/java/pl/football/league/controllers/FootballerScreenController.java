package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Footballer;
import pl.football.league.fxmlUtils.TableControls;
import pl.football.league.services.ItemShowService;

public class FootballerScreenController extends ItemShowService {
    @FXML
    private VBox fansVBox;

    @FXML
    private Label titleLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label teamLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private Label shirtNumberLabel;

    @FXML
    private Button supportButton;

    @FXML
    private Button stopSupportButton;

    @FXML
    void initialize() {
        supportButton.setDisable(false);
        stopSupportButton.setDisable(true);

        setFootballerInfo();
        setFans();

        teamLabel.setOnMouseClicked(event -> {
            TeamScreenController teamScreenController = new TeamScreenController();
            teamScreenController.setDependencies(currentUser, entityManager, mainScreenController, ((Footballer)currentData).getTeam());
            loadCenterScene("/fxml/teamScreen.fxml", teamScreenController);
        });
    }

    @FXML
    public void support(){
        entityManager.getTransaction().begin();
        ((Footballer)currentData).getFans().add(currentUser);
        currentUser.getSupportedFootballers().add(((Footballer)currentData));
        entityManager.getTransaction().commit();
        supportButton.setDisable(true);
        stopSupportButton.setDisable(false);
        this.setFans();
    }
    @FXML
    void stopSupport() {
        entityManager.getTransaction().begin();
        ((Footballer)currentData).getFans().remove(currentUser);
        currentUser.getSupportedFootballers().remove(((Footballer)currentData));
        entityManager.getTransaction().commit();
        supportButton.setDisable(false);
        stopSupportButton.setDisable(true);
        this.setFans();
    }


    private void setFans(){
        fansVBox.getChildren().remove(0, fansVBox.getChildren().size());
        if(((Footballer)currentData).getFans().isEmpty()){
            Label emptyLabel = TableControls.Label(150, "Brak Kibiców");
            emptyLabel.setMaxWidth(1.7976931348623157E308);
            fansVBox.getChildren().add(emptyLabel);
        }
        else {
            for (Fan f : ((Footballer)currentData).getFans()) {
                if (f.getFanID() == currentUser.getFanID()) {
                    supportButton.setDisable(true);
                    stopSupportButton.setDisable(false);
                }
                Label fanLabel = TableControls.LabelVGrow(150, f.getNickname());
                fanLabel.setOnMouseClicked(event -> {
                    FanScreenController fanScreenController = new FanScreenController();
                    fanScreenController.setDependencies(f, entityManager, mainScreenController, currentUser);
                    loadCenterScene("/fxml/fanScreen.fxml", fanScreenController);
                });
                fansVBox.getChildren().add(fanLabel);
            }
        }
    }

    private void setFootballerInfo(){
        nameLabel.setText( ((Footballer)currentData).getName() );
        surnameLabel.setText(((Footballer)currentData).getSurname());

        if(((Footballer)currentData).getTeam() != null)
            teamLabel.setText(((Footballer)currentData).getTeam().getName());
        else {
            teamLabel.setText("-");
            teamLabel.setCursor(Cursor.DEFAULT);
            teamLabel.setStyle("-fx-background-color: transparent; -fx-font-size: 20;  -fx-text-fill: forestgreen;  -fx-font-weight: bold;");
        }

        positionLabel.setText(((Footballer)currentData).getPosition());

        if(((Footballer)currentData).getNumber() != null)
            shirtNumberLabel.setText(String.valueOf(((Footballer)currentData).getNumber()));
        else
            shirtNumberLabel.setText("-");

        titleLabel.setText(((Footballer)currentData).getName() + " " +  ((Footballer)currentData).getSurname());
    }
}