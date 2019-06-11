package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.football.league.entities.Coach;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.Alerts;
import pl.football.league.services.ItemAddService;
import pl.football.league.threads.Buffer;
import pl.football.league.threads.ReceiverItemTask;

import javax.persistence.NoResultException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

/**
 * Kontroler do pliku addTeamScreen.fxml
 * @author Marcin Cyc
 * @see pl.football.league.services.ItemAddService
 */
public class AddTeamScreenController extends ItemAddService {
    /**
     * Nowo dodany trener
     * @see pl.football.league.entities.Coach
     */
    private Coach coach;

    /**
     * TextField z nazwą drużyny
     * @see javafx.scene.control.TextField
     */
    @FXML
    private TextField nameTextField;

    /**
     * ComboBox z możliwymi trenerami do wyboru dla drużyny
     * @see  javafx.scene.control.ComboBox
     */
    @FXML
    private ComboBox<Coach> coachComboBox;

    /**
     * Wybór daty dla powstania drużyny
     * @see javafx.scene.control.DatePicker
     */
    @FXML
    private DatePicker creationDate;

    /**
     * Przycisk akceptujący podane dane
     * @see javafx.scene.control.Button
     */
    @FXML
    private Button acceptButton;

    /**
     * Przycisk anulujący dodawanie i zamykjący okno
     * @see javafx.scene.control.Button
     */
    @FXML
    private Button cancelButton;

    /**
     * Przycisk otwierający dodatkowe okno, w którym dodaje się nowego trenera do bazy danych
     * @see javafx.scene.control.Button
     */
    @FXML
    private Button addCoachButton;

    /**
     * Otwarcie nowego okna z dodaniem trenera do bazy danych, po zakończeniu dodawania odświeżenie comboBox'a z dostępnymi
     * trenerami. Utworzenie kolejnej sceny, utworzenie bufora komunikacyjnego utworzenie kontrolera dla okna addCoachScreen.fxml
     * oraz otworzenie tego okna. Funckja uruchamiana po kliknięciu przycisku addCoachButton.
     */
    @FXML
    void addCoach() {
        Buffer bufferAddCoach = new Buffer();
        Stage secondStage = new Stage();

        ReceiverItemTask receiverAddingCoachFinish = new ReceiverItemTask(bufferAddCoach);
        Thread t2 = new Thread(receiverAddingCoachFinish);
        t2.start();

        AddCoachScreenController addCoachScreenController = new AddCoachScreenController();
        addCoachScreenController.setDependencies(null, entityManager, null, null, secondStage, bufferAddCoach);
        secondStage.setTitle("Dodawanie Trenera");
        loadNewStage("/fxml/addWindows/addCoachScreen.fxml", addCoachScreenController, secondStage);

        receiverAddingCoachFinish.setOnSucceeded(event -> {
            if(addCoachScreenController.getCurrentData() != null) {
                coach = (Coach)addCoachScreenController.getCurrentData();
            }
            setCoachComboBox(entityManager.createQuery("select C from Coach  C", Coach.class).getResultList());
        });
    }

    /**
     * Zakceptowanie podanych danych, próba dodania elemntu do bazy danych oraz w przypadku sukcesu zamknięcie okna.
     * Zostaje utowrzony nowy obiekt i dane z poszczególnych pól kontrolnych są przekazywane do obiektu. Wyświetlany jest
     * pierwszy napotkany błąd w formie komunikatu (Alert). Werfikowane są: unikalność i niepustość nazwy, wybór trnera.
     * Wyniki są zerowane oraz ustawiany jest pusty zbiór kibiców wspierających drużynę.
     * @see pl.football.league.fxmlUtils.Alerts
     */
    @FXML
    void addTeamAndBack() {
        currentData = new Team();
        String name;

        name = nameTextField.getText();
        try {
            entityManager.createQuery("select T from Team T where T.name LIKE :param").setParameter("param", name).getSingleResult();
            Alerts.sameTeamName().showAndWait();
            return;
        }catch (NoResultException e){
            System.out.println("ok");
        }

        if (!name.equals("")) {
            ((Team)currentData).setName(nameTextField.getText());
        } else {
            Alert emptyField = Alerts.emptyField();
            emptyField.showAndWait();
            return;
        }

        if (coachComboBox.getValue() == null) {
            Alert noCoach = Alerts.noCoach();
            noCoach.showAndWait();
            return;
        } else {
            ((Team)currentData).setCoach(coachComboBox.getValue());
        }

        ((Team)currentData).setCreationDate(Date.valueOf(creationDate.getValue()));
        ((Team)currentData).setPoints(0);
        ((Team)currentData).setWins(0);
        ((Team)currentData).setDraws(0);
        ((Team)currentData).setLoses(0);
        ((Team)currentData).setTeamFans(new HashSet<>());

        addItem(currentData);
        back();
    }

    /**
     * Zakmnięcie okna
     */
    @FXML
    void back() {
        if(isCanceled)
            buffer.put();
        stage.close();
    }

    /**
     * Inicjalizacja okna. Ustawienie comboBox'a z trenerami oraz domyślnej daty na dzisiejszą. Ustawienie akcji po zamknięciu okna.
     */
    @FXML
    void initialize() {
        stage.setOnCloseRequest(event -> back());
        setCoachComboBox(entityManager.createQuery("select C from Coach  C", Coach.class).getResultList());
        creationDate.setValue(LocalDate.now());
    }

    /**
     * Ustawienie comboBox'a z możliwymi trenerami do prowadzenia drużyny w przypadku brak wolnych trenerów ustawienie
     * tesktu pomocnicznego na "Brak wolnych trenerów"
     * @param coaches lista trenerów z bazy
     */
    private void setCoachComboBox(List<Coach> coaches){
        coachComboBox.getItems().clear();
        List<Team>teams = entityManager.createQuery("select T from Team T", Team.class).getResultList();
        boolean busyCoach;
        for (Coach c : coaches) {
            busyCoach = false;
            for (Team t : teams) {
                if (t.getCoach() == c)
                    busyCoach = true;
            }
            if (!busyCoach)
                coachComboBox.getItems().add(c);
        }
        if (coachComboBox.getItems().isEmpty()) {
            coachComboBox.setPromptText("Brak wolnych trenerów");
        }
    }

    public Coach getCoach() {
        return coach;
    }
}
