package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import pl.football.league.entities.Footballer;
import pl.football.league.entities.Team;
import pl.football.league.services.TableItemsShowService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Kontroler do pliku footballersTableScreen.fxml
 * @author Marcin Cyc
 * @see pl.football.league.services.TableItemsShowService
 */
public class FootballersTableScreenController extends TableItemsShowService {
    /**
     * Label z nagłówkiem kolumny imię
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label nameLabel;

    /**
     * Label z nagłówkiem kolumny nazwisko
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label surnameLabel;

    /**
     * Label z nagłówkiem kolumny drużyna
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label teamLabel;

    /**
     * Label z nagłówkiem kolumny numer na koszulce
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label numberLabel;

    /**
     * Label z nagłówkiem kolumny pozycja
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label positionLabel;

    /**
     * GridPane'a z informacjami o piłkarzach z bazy danych
     * @see javafx.scene.layout.GridPane
     */
    @FXML
    private GridPane gridPane;

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
        currentData = entityManager.createQuery("select F from Footballer F", Footballer.class).getResultList();
        allData = new ArrayList(currentData);
        searchTextField.setTooltip(new Tooltip("Wyszukiwarka po imieniu i nazwisku piłkarza"));
        setSorts();
        fillTable();
    }

    /**
     * Uzupełnia gridPane'a danymi o piłkarzach z bazy
     */
    private void fillTable(){
       fillGridPane(gridPane, 5);
    }

    /**
     * Ustawia akcje kliknięcia w Label'e nagłówkowe, które posortują pobrane dane: po kliknięciu w teamLabel dane zostną
     * posortowane względem drużyny, po kliknięciu w nameLabel dane będą posortowane po imieniu, po kliknięciu w surnameLabel
     * dane będą posorotowane po naziwsku, po kliknięciu w positionLabel dane zostaną posortowane po pozycji, po kliknięciu
     * w numberLabel dane zostaną posortowane po numerze na koszulce.
     */
    private void setSorts() {
        currentData.sort(Comparator.comparing(Footballer::getSurname));

        surnameLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Footballer::getSurname));
            fillTable();
        });

        nameLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Footballer::getName));
            fillTable();
        });

        teamLabel.setOnMouseClicked(event->{
            currentData.sort((Object f1, Object f2) -> {
                if(((Footballer)f1).getTeam() == null)
                    return (((Footballer)f2).getTeam() == null ) ? 0 : -1;
                if(((Footballer)f2).getTeam() == null)
                    return 1;
                return ((Footballer)f1).getTeam().getName().compareTo(((Footballer)f2).getTeam().getName());
            });
            fillTable();
        });

        positionLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Footballer::getPosition));
            fillTable();
        });

        numberLabel.setOnMouseClicked(event -> {
            currentData.sort((Object f1, Object f2) -> {
                if(((Footballer)f1).getNumber() == null)
                    return (((Footballer)f2).getNumber() == null ) ? 0 : -1;
                if(((Footballer)f2).getNumber() == null)
                    return 1;
                return ((Footballer)f1).getNumber().compareTo(((Footballer)f2).getNumber());
            });
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