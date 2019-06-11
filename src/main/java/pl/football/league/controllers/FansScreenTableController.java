package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import pl.football.league.entities.Fan;
import pl.football.league.services.TableItemsShowService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Kontroler do pliku fansTableScreen.fxml
 * @author Marcin Cyc
 * @see pl.football.league.services.TableItemsShowService
 */
public class FansScreenTableController extends TableItemsShowService {
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
     * Label z nagłówkiem kolumny pseudonim
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label nicknameLabel;

    /**
     * Label z nagłówkiem kolumny wiek
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label ageLabel;

    /**
     * GridPane'a z informacjami o kibicach z bazy danych
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
     * Inicjalizacja danych: pobranie danych z bazy danych, ustawienie sortowań oraz uzupełnienie gridPane'a danymi.
     */
    @FXML
    void initialize() {
        currentData = entityManager.createQuery("select F from Fan F").getResultList();
        allData = new ArrayList(currentData);
        searchTextField.setTooltip(new Tooltip("Wyszukiwarka po imieniu, nazwisku i psuedonimie kibica"));
        setSorts();
        fillTable();
    }

    /**
     * Uzupełnia gridPane'a danymi o kibicach z bazy
     */
    private void fillTable(){
        fillGridPane(gridPane, 4);
    }

    /**
     * Ustawia akcje kliknięcia w Label'e nagłówkowe, które posortują pobrane dane: po kliknięciu w ageLabel dane zostną
     * posortowane względem wieku, po kliknięciu w nameLabel dane będą posortowane po imieniu, po kliknięciu w surnameLabel
     * dane będą posorotowane po naziwsku, po kliknięciu w nicknameLabel dane zostaną posortowane po pseudonimie.
     */
    private  void setSorts(){
        nameLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Fan::getName));
            fillTable();
        });

        nicknameLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Fan::getNickname));
            fillTable();
        });

        surnameLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Fan::getSurname));
            fillTable();
        });

        ageLabel.setOnMouseClicked(event -> {
            currentData.sort(Comparator.comparing(Fan::getAge));
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
