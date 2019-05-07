package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
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
    private List<Coach> coaches;

    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label ageLabel;

    @FXML
    private GridPane gridPane;

    @FXML
    void initialize() {
        coaches = entityManager.createQuery("select C from Coach C").getResultList();
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

    private void tryLoader(FXMLLoader loader) {
        try {
            Parent root = loader.load();
            mainController.getBorderPane().setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillTable(){
        int i = 0;
        gridPane.getChildren().remove(0, gridPane.getChildren().size());
        for (Coach c : coaches) {
            Label name = TableControls.LabelVGrow(150, c.getName());
            gridPane.setHgrow(name, Priority.ALWAYS);
            name.setOnMouseClicked((MouseEvent event) -> {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/coachScreen.fxml"));
                CoachScreenController coachScreenController = new CoachScreenController();

                coachScreenController.setCoach(c);
                coachScreenController.setEntityManager(entityManager);
                loader.setController(coachScreenController);
                tryLoader(loader);
            });

            Label surname = TableControls.LabelVGrow(150, c.getSurname());
            gridPane.setHgrow(surname, Priority.ALWAYS);
            surname.setOnMouseClicked(name.getOnMouseClicked());

            String result;
            if (c.getAge() == null) {
                result = "-";
            } else {
                result = String.valueOf(c.getAge());
            }
            Label age = TableControls.Label(150, result);
            age.setMaxWidth(1.7976931348623157E308);
            gridPane.setHgrow(age, Priority.ALWAYS);

            gridPane.addRow(i, name, surname, age);
            i++;
        }
    }

    private void setSorts(){
        coaches.sort(Comparator.comparing(Coach::getSurname));

        ageLabel.setOnMouseClicked(event -> {
            coaches.sort((Coach c1, Coach c2) ->{
                if(c1.getAge() == null)
                    return (c2.getAge() == null ) ? 0 : -1;
                if(c2.getAge() == null)
                    return 1;
                return c1.getAge().compareTo(c2.getAge());
            });
            fillTable();
        });

        nameLabel.setOnMouseClicked(event -> {
            coaches.sort(Comparator.comparing(Coach::getName));
            fillTable();
        });

        surnameLabel.setOnMouseClicked(event -> {
            coaches.sort(Comparator.comparing(Coach::getSurname));
            fillTable();
        });
    }
}