package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import pl.football.league.entities.Coach;
import pl.football.league.services.TableItemsShowService;

import java.util.Comparator;

/**
 * Kontroler do pliku coachesTableScreen.fxml
 * @author Marcin Cyc
 * @see pl.football.league.services.TableItemsShowService
 */
public class CoachesTableScreenController extends TableItemsShowService {
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
     * Label z nagłówkiem kolumny wiek
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label ageLabel;

    /**
     * GridPane'a z informacjami o trenerach z bazy danych
     * @see javafx.scene.layout.GridPane
     */
    @FXML
    private GridPane gridPane;

    /**
     * Inicjalizacja okna: pobranie danych z bazy danych, ustawienie sortowania oraz uzupełnienie gridPane'a danymi
     */
    @FXML
    void initialize() {
        currentData = entityManager.createQuery("select C from Coach C", Coach.class).getResultList();
        setSorts();
        fillTable();
    }

    /**
     * Uzupełnia gridPane'a danymi o trenerach z bazy
     */
    private void fillTable(){
        fillGridPane(gridPane, 3);
    }

    /**
     * Ustawia akcje kliknięcia w Label'e nagłówkowe, które posortują pobrane dane: po kliknięciu w ageLabel dane zostną
     * posortowane względem wieku, po kliknięciu w nameLabel dane będą posortowane po imieniu, po kliknięciu w surnameLabel
     * dane będą posorotowane po naziwsku.
     */
    private void setSorts(){
        currentData.sort(Comparator.comparing(Coach::getSurname));

        ageLabel.setOnMouseClicked(event -> {
            currentData.sort((Object c1, Object c2) ->{
                if(((Coach)c1).getAge() == null)
                    return (((Coach)c2).getAge() == null ) ? 0 : -1;
                if(((Coach)c2).getAge() == null)
                    return 1;
                return ((Coach)c1).getAge().compareTo(((Coach)c2).getAge());
            });
            fillTable();
        });

        nameLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Coach::getName));
            fillTable();
        });

        surnameLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Coach::getSurname));
            fillTable();
        });
    }
}