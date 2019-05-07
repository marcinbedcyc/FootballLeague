package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.TableControls;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class FansScreenTableController {
    private EntityManager entityManager;
    private MainScreenController mainController;
    private Fan currentUser;
    private List<Fan> fans;

    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label nicknameLabel;

    @FXML
    private Label ageLabel;

    @FXML
    private GridPane gridPane;

    @FXML
    void initialize() {
        fans = entityManager.createQuery("select F from Fan F").getResultList();
        setSorts();
        fillTable();
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
        try{
            Parent root = loader.load();
            mainController.getBorderPane().setCenter(root);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void fillTable(){
        int i = 0;
        gridPane.getChildren().remove(0, gridPane.getChildren().size());
        for(Fan f: fans){
            Label nameLabel = TableControls.LabelVGrow(150, f.getName());
            gridPane.setHgrow(nameLabel, Priority.ALWAYS);
            nameLabel.setOnMouseClicked((MouseEvent)->{
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/fanScreen.fxml"));
                FanScreenController fanScreenController = new FanScreenController();
                Fan fan = entityManager.find(Fan.class, f.getFanID());

                fanScreenController.setDependencies(fan, entityManager, mainController, currentUser);
                loader.setController(fanScreenController);
                tryLoader(loader);
            });

            Label surnameLabel = TableControls.LabelVGrow(150, f.getSurname());
            gridPane.setHgrow(surnameLabel, Priority.ALWAYS);
            surnameLabel.setOnMouseClicked(nameLabel.getOnMouseClicked());

            Label nicknameLabel = TableControls.LabelVGrow(150, f.getNickname());
            gridPane.setHgrow(nicknameLabel, Priority.ALWAYS);
            nicknameLabel.setOnMouseClicked(nameLabel.getOnMouseClicked());

            String result = String.valueOf(f.getAge());
            if(result.equals("null"))
                result = "-";
            Label ageLabel = TableControls.Label(50, result);

            gridPane.addRow(i, nameLabel, surnameLabel, nicknameLabel, ageLabel);
            i++;
        }
    }

    private  void setSorts(){
        nameLabel.setOnMouseClicked(event -> {
            fans.sort(Comparator.comparing(Fan::getName));
            fillTable();
        });

        nicknameLabel.setOnMouseClicked(event -> {
            fans.sort(Comparator.comparing(Fan::getNickname));
            fillTable();
        });

        surnameLabel.setOnMouseClicked(event -> {
            fans.sort(Comparator.comparing(Fan::getSurname));
            fillTable();
        });

        ageLabel.setOnMouseClicked(event -> {
            fans.sort(Comparator.comparing(Fan::getAge));
            fillTable();
        });
    }
}
