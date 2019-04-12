package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import pl.football.league.entities.Fan;
import pl.football.league.fxmlUtils.TableControls;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;

public class FansScreenTableController {
    private EntityManager entityManager;
    private MainScreenController mainController;
    private Fan currentUser;

    @FXML
    private ListView<HBox> listView;

    @FXML
    void initialize() {
        List<Fan> fans = entityManager.createQuery("select F from Fan F").getResultList();

        HBox hBox;
        for(Fan f: fans){
            hBox = new HBox();
            hBox.setSpacing(0);
            hBox.setAlignment(Pos.CENTER);

            Label nameLabel = TableControls.LabelVGrow(150, f.getName());
            hBox.setHgrow(nameLabel, Priority.ALWAYS);
            nameLabel.setOnMouseClicked((MouseEvent)->{
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/fanScreen.fxml"));
                FanScreenController fanScreenController = new FanScreenController();
                Fan fan = entityManager.find(Fan.class, f.getFanID());

                fanScreenController.setDependencies(fan, entityManager);
                loader.setController(fanScreenController);
                tryLoader(loader);
            });

            Label surnameLabel = TableControls.LabelVGrow(150, f.getSurname());
            hBox.setHgrow(surnameLabel, Priority.ALWAYS);
            surnameLabel.setOnMouseClicked(nameLabel.getOnMouseClicked());

            Label nicknameLabel = TableControls.LabelVGrow(150, f.getNickname());
            hBox.setHgrow(nicknameLabel, Priority.ALWAYS);
            nicknameLabel.setOnMouseClicked(nameLabel.getOnMouseClicked());

            String result = String.valueOf(f.getAge());
            if(result.equals("null"))
                result = "-";
            Label ageLabel = TableControls.Label(50, result);

            hBox.getChildren().addAll(nameLabel, new Separator(Orientation.VERTICAL), surnameLabel, new Separator(Orientation.VERTICAL),
                    nicknameLabel,new Separator(Orientation.VERTICAL), ageLabel );
            listView.getItems().add(hBox);
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
        try{
            Parent root = loader.load();
            mainController.getBorderPane().setCenter(root);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
