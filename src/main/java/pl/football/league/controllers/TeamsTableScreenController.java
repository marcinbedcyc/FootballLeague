package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import pl.football.league.entities.Team;
import pl.football.league.services.TableItemsShowService;

import java.util.Comparator;

/**
 * Kontroler do pliku teamsTable.fxml
 * @author Marcin Cyc
 * @see pl.football.league.services.TableItemsShowService
 */
public class TeamsTableScreenController extends TableItemsShowService {
    /**
     * Label z nagłówkiem kolumny nazwy drużyny
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label teamLabel;

    /**
     * Label z nagłówkiem kolumny trener drużyny
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label coachLabel;

    /**
     * Label z nagłówkiem kolumny data założenia drużyny
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label dateLabel;

    /**
     * GridPane'a z informacjami o drużynach z bazy danych
     * @see javafx.scene.layout.GridPane
     */
    @FXML
    private GridPane gridPane;

    /**
     * Inicjalizacja okna: pobranie danych z bazy danych, ustawienie sortowania oraz uzupełnienie gridPane'a danymi
     */
    @FXML
    void initialize() {
        currentData = entityManager.createQuery("select T from Team T").getResultList();
        setSorts();
        fillTable();
    }

    /**
     * Uzupełnia gridPane'a danymi drużyn z bazy
     */
    private void fillTable(){
        fillGridPane(gridPane, 3);
    }

    /**
     * Ustawia akcje kliknięcia w Label'e nagłówkowe, które posortują pobrane dane: po kliknięciu w teamLabel dane są sortowane
     * wzgledem nazwy drużyny, po kliknięciu w coachLabel dane są sortowane względem nazwiska trenera drużyny, a po kliknięciu
     * w dateLabel dane są sortowane względem daty założenia drużyny.
     */
    private void setSorts(){
        teamLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Team::getName));
            fillTable();
        });

        coachLabel.setOnMouseClicked(event -> {
            currentData.sort((Object o1, Object o2) -> ((Team)o1).getCoach().getSurname().compareTo(((Team)o2).getCoach().getSurname()));
            fillTable();
        });

        dateLabel.setOnMouseClicked(event -> {
            currentData.sort((Object o1, Object o2) ->{
                if(((Team)o1).getCreationDate() == null)
                    return (((Team)o2).getCreationDate() == null ) ? 0 : -1;
                if(((Team)o2).getCreationDate() == null)
                    return 1;
                return ((Team)o1).getCreationDate().compareTo(((Team)o2).getCreationDate());
            });
            fillTable();
        });
    }
}