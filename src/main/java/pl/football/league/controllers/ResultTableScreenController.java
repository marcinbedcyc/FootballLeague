package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import pl.football.league.entities.Match;
import pl.football.league.services.TableItemsShowService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Kontroler do pliku resultTableScreen.fxml
 * @author Marcin Cyc
 * @see pl.football.league.services.TableItemsShowService
 */
public class ResultTableScreenController extends TableItemsShowService {
    /**
     * GridPane'a z informacjami o wynikach meczów z bazy danych
     * @see javafx.scene.layout.GridPane
     */
    @FXML
    private GridPane gridPane;

    /**
     * Label z nagłówkiem kolumny gospodarz
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label homeLabel;

    /**
     * Label z nagłówkiem kolumny gość
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label awayLabel;

    /**
     * Label z nagłówkiem kolumny data
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label dateLabel;

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
        currentData = entityManager.createQuery("select M from Match M").getResultList();
        allData = new ArrayList(currentData);
        searchTextField.setTooltip(new Tooltip("Wyszukiwarka po drużynach meczu oraz wyniku"));
        setSorts();
        fillTable();
    }

    /**
     * Uzupełnia gridPane'a danymi o wynikach meczów z bazy
     */
    private void fillTable(){
        fillGridPane(gridPane, 4);
    }

    /**
     * Ustawia akcje kliknięcia w Label'e nagłówkowe, które posortują pobrane dane: po kliknięciu w homeLabel dane zostaną
     * posorotwane po nazwie drużyny gospodarza, po kliknięciu w awayLabel dane zostaną posortowane po nazwie drużyny gościa,
     * po kliknięciu w dateLabel dane zostaną posortowane po dacie rozegrania meczu.
     */
    private void setSorts(){
        homeLabel.setOnMouseClicked(event -> {
            currentData.sort((Object m1, Object m2) -> {
                return ((Match)m1).getMatchID().getHome().getName().compareTo(((Match)m2).getMatchID().getHome().getName());
            });
            fillTable();
        });

        awayLabel.setOnMouseClicked(event -> {
            currentData.sort((Object m1, Object m2) -> {
                return ((Match)m1).getMatchID().getAway().getName().compareTo(((Match)m2).getMatchID().getAway().getName());
            });
            fillTable();
        });

        dateLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Match::getMatchDate));
            fillTable();
        });
    }

    /**
     * Filtruje dane wyświetlane użytkownikowi
     */
    @FXML
    void searchElements() {
        currentData = (List)allData.stream().filter(f -> f.toString().toLowerCase().contains(searchTextField.getText().toLowerCase())).collect(Collectors.toList());
        fillTable();
    }
}