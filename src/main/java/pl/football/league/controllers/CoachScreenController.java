package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import pl.football.league.entities.Coach;
import pl.football.league.entities.Team;
import pl.football.league.services.ItemShowService;

import javax.persistence.NoResultException;

/**
 * Kontroler dla pliku coachScreen.fxml
 * @author Marcin Cyc
 * @see pl.football.league.services.ItemShowService
 */
public class CoachScreenController extends ItemShowService {
    /**
     * Label z imieniem trenera
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label nameLabel;

    /**
     * Label z tytułem okna
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label titleLabel;

    /**
     * Label z nazwiskiem trenera
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label surnameLabel;

    /**
     * Label z wiekiem trenera
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label ageLabel;

    /**
     * Label z drużyną trenera
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label teamLabel;

    /**
     * Inicjalizacja okna: uzupełnienie pól kontrolnych informacjami o drużynie oraz dodanie akcji po kliknięciu
     * na nazwę drużyny(otworznie okna z informacjami o drużynie). W przypadku braku druzyny Label uzupelniany jest napisem
     * "Brak drużyny".
     */
    @FXML
    void initialize() {
        nameLabel.setText(((Coach)currentData).getName());
        surnameLabel.setText(((Coach)currentData).getSurname());
        ageLabel.setText((((Coach)currentData).getAge() != null) ? String.valueOf(((Coach)currentData).getAge()) : "-");
        titleLabel.setText(((Coach)currentData).getName() + " " + ((Coach)currentData).getSurname());

        try {
            Team team = (Team) entityManager.createQuery("select T from Team T where T.coach.id = :param").setParameter("param",
                    ((Coach)currentData).getCoachID()).getSingleResult();
            teamLabel.setText(team.getName());
            teamLabel.setOnMouseClicked(event -> {
                TeamScreenController teamScreenController = new TeamScreenController();
                teamScreenController.setDependencies(currentUser, entityManager, mainScreenController, team);
                loadCenterScene("/fxml/teamScreen.fxml", teamScreenController);
            });
        }catch (NoResultException e) {
            teamLabel.setText("Brak drużyny");
            teamLabel.setCursor(Cursor.DEFAULT);
            teamLabel.setStyle("-fx-background-color: transparent; -fx-font-size: 20;  -fx-text-fill: forestgreen;  -fx-font-weight: bold;");
        }
    }
}
