package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import pl.football.league.entities.Team;
import pl.football.league.services.TableItemsShowService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Kontroler do pliku tableScreen.fxml
 * @author Marcin Cyc
 * @see pl.football.league.services.TableItemsShowService
 */
public class TableScreenController extends TableItemsShowService {
    /**
     * GridPane'a z informacjami o drużynach z bazy danych
     * @see javafx.scene.layout.GridPane
     */
    @FXML
    private GridPane gridPane;

    /**
     * Label z nagłówkiem kolumny nazwy drużyny
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label teamLabel;

    /**
     * Label z nagłówkiem kolumny punktów
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label pointsLabel;

    /**
     * Label z nagłówkiem kolumny liczby zwycięstw
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label winsLabel;

    /**
     * Label z nagłówkiem kolumny liczby remisów
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label drawsLabel;

    /**
     * Label z nagłówkiem kolumny liczby przegranych
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label losesLabel;

    /**
     * Label z nagłówkiem kolumny trener drużyny
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label coachLabel;

    /**
     * Pole do filtrowania wyników w tabeli
     * @see javafx.scene.control.TextField
     */
    @FXML
    private TextField searchTextField;

    /**
     * Inicjalizacja okna: pobranie danych z bazy danych, ustawienie sortowania oraz uzupełnienie gridPane'a danymi
     */
    @FXML
    void initialize() {
        currentData = entityManager.createQuery("select t from Team t").getResultList();
        allData = new ArrayList(currentData);
        searchTextField.setTooltip(new Tooltip("Wyszukiwarka po nazwie drużyny oraz nazwisku trenera"));
        setSorts();
        fillTable();
    }

    /**
     * Uzupełnia gridPane'a danymi drużyn z bazy
     */
    private void fillTable() {
        fillGridPane(gridPane, 7);
    }

    /**
     * Ustawia akcje kliknięcia w Label'e nagłówkowe, które posortują pobrane dane: po kliknięciu w teamLabel dane są sortowane
     * wzgledem nazwy drużyny, po kliknięciu w pointsLabel dane są sortowane względem ilości zdobytych punktów, po kliknięciu w
     * winsLabel dane są sortowane względem ilości wygranych meczów, po kliknięciu w drawsLabel dane są sortowane względem
     * ilości zremisowanych meczów, po kliknięciu w losesLabel dane są sortowane względem ilości przegranych meczów.
     */
    private void setSorts(){
        currentData.sort((Object o1, Object o2)->((Team)o2).getPoints() - ((Team)o1).getPoints());


        teamLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Team::getName));
            fillTable();
        });

        pointsLabel.setOnMouseClicked(event -> {
            currentData.sort((Object o1, Object o2)->((Team)o2).getPoints() - ((Team)o1).getPoints());
            fillTable();
        });

        winsLabel.setOnMouseClicked(event -> {
            currentData.sort((Object o1, Object o2)->((Team)o2).getWins() - ((Team)o1).getWins());
            fillTable();
        });

        drawsLabel.setOnMouseClicked(event -> {
            currentData.sort((Object o1, Object o2)->((Team)o2).getDraws() - ((Team)o1).getDraws());
            fillTable();
        });

        losesLabel.setOnMouseClicked(event -> {
            currentData.sort((Object o1, Object o2)->((Team)o2).getLoses() - ((Team)o1).getLoses());
            fillTable();
        });

        coachLabel.setOnMouseClicked(event -> {
            currentData.sort((Object o1, Object o2)->((Team)o2).getCoach().getSurname().compareTo(((Team)o1).getCoach().getSurname()));
            fillTable();
        });
    }

    /**
     * Filtruje dane wyświetlane użytkownikowi
     */
    @FXML
    void searchElements() {
        currentData = (List)allData.stream().filter(f -> {
            String text = f.toString().toLowerCase() + "  " + ((Team)f).getCoach().getSurname().toLowerCase();
            return text.contains(searchTextField.getText().toLowerCase());
        }).collect(Collectors.toList());
        fillTable();
    }
}