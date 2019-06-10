package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Footballer;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.TableControls;
import pl.football.league.services.ItemShowService;

import java.util.List;

/**
 * Kontroler do pliku teamScreen.fxml
 * @author Marcin Cyc
 * @see pl.football.league.services.ItemShowService
 */
public class TeamScreenController extends ItemShowService {
    /**
     * Label z nazwą drużyny
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label nameLabel;

    /**
     * Label z trenerem
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label coachLabel;

    /**
     * Label z datą powstania drużyny
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label dateLabel;

    /**
     * Label z ilością punktów drużyny
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label pointsLabel;

    /**
     * Label z ilością wygranych meczów
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label winsLabel;

    /**
     * Label z ilością zremisowanych meczów
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label drawsLabel;

    /**
     * Label z ilością przegranych meczów
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label losesLabel;

    /**
     * VBox z piłkarzami grającymi w drużynie
     * @see javafx.scene.layout.VBox
     */
    @FXML
    private VBox footbalersVBox;

    /**
     * VBox z kibicami wspierającymi drużynę
     * @see javafx.scene.layout.VBox
     */
    @FXML
    private VBox fansVBox;

    /**
     * Przycisk umożliwiającym kibicowanie drużynie
     * @see javafx.scene.control.Button
     */
    @FXML
    private Button supportButton;

    /**
     * Przycisk umożliwiającym zaprzestanie kibicowania drużynie
     * @see javafx.scene.control.Button
     */
    @FXML
    private Button stopSupportButton;

    /**
     * Lista piłkarzy w drużynie
     * @see pl.football.league.entities.Footballer
     */
    private List<Footballer> footballers;

    /**
     * Inicjalizacja okna: ustawienie informacji o drużynie, piłkarzy w niej grających, kibiców jej kibicujących
     * oraz ustawienie akcji po kliknięciu na nazwę trenera.
     */
    @FXML
    void initialize() {
        footballers = entityManager.createQuery("select F from Footballer F", Footballer.class).getResultList();
        setTeamInfo();
        setFootballers();
        supportButton.setDisable(false);
        stopSupportButton.setDisable(true);
        setFans();
        coachLabel.setOnMouseClicked(event -> {
            CoachScreenController coachScreenController = new CoachScreenController();
            coachScreenController.setDependencies(currentUser, entityManager, mainScreenController, ((Team)currentData).getCoach());
            loadCenterScene("/fxml/coachScreen.fxml", coachScreenController);
        });
    }

    /**
     * Pobiera kibiców wspierających drużynę z bazy danych i uzupełnia danymi odpowiedniego VBox'a. W przypadku braku
     * kibiców zostaje wyświetlony napis "Brak kibiców". Dodaje akcję kliknięcia na pseduonim kibica. Akcja otwiera nowe
     * okno z informacjami o tym kibicu.
     */
    private void setFans() {
        fansVBox.getChildren().remove(0, fansVBox.getChildren().size());

        if (((Team)currentData).getTeamFans().isEmpty()) {
            Label emptyLabel = TableControls.Label(150, "Brak kibiców");
            emptyLabel.setMaxWidth(1.7976931348623157E308);
            fansVBox.getChildren().add(emptyLabel);
        } else {
            for (Fan f : ((Team)currentData).getTeamFans()) {
                Label fanLabel = TableControls.LabelVGrow(150, f.getNickname());
                fanLabel.setOnMouseClicked(event -> {
                    FanScreenController fanScreenController = new FanScreenController();
                    fanScreenController.setDependencies(f, entityManager, mainScreenController, currentUser);
                    loadCenterScene("/fxml/fanScreen.fxml", fanScreenController);
                });
                if (f.getFanID() == currentUser.getFanID()) {
                    supportButton.setDisable(true);
                    stopSupportButton.setDisable(false);
                }
                fansVBox.getChildren().add(fanLabel);
            }
        }
    }

    /**
     * Pobiera piłkarzy grających w drużynie z bazy danych i uzupełnia nimi odpowiedniego VBox'a. W przypadku braku piłkarzy
     * zostaje wyświetlony napis "Brak piłkarzy".Dodaje akcję kliknięcia na imię i naziwsko piłkarza. Akcja otwiera nowe
     * okno z informacjami o tym piłkarzu.
     */
    private void setFootballers() {
        boolean areFootaballers = false;

        for (Footballer f : footballers) {
            if (f.getTeam() != null && f.getTeam().getTeamID() == ((Team)currentData).getTeamID()) {
                areFootaballers = true;
                Label footballerLabel = TableControls.LabelVGrow(150, f.getName() + " " + f.getSurname());
                footballerLabel.setOnMouseClicked(event -> {
                    FootballerScreenController footballerScreenController = new FootballerScreenController();
                    footballerScreenController.setDependencies(currentUser, entityManager, mainScreenController, f);
                    loadCenterScene("/fxml/footballerScreen.fxml", footballerScreenController);
                });
                footbalersVBox.getChildren().add(footballerLabel);
            }
        }
        if (!areFootaballers) {
            Label emptyLabel = TableControls.Label(150, "Brak piłkarzy");
            emptyLabel.setMaxWidth(1.7976931348623157E308);
            footbalersVBox.getChildren().add(emptyLabel);
        }
    }

    /**
     * Usuwa z bazy danych wspieranie drużyny przez obecnie zalogowanego użytkownika.
     */
    @FXML
    void stopSupport() {
        entityManager.getTransaction().begin();
        ((Team)currentData).getTeamFans().remove(currentUser);
        currentUser.getSupportedTeams().remove(((Team)currentData));
        entityManager.getTransaction().commit();
        supportButton.setDisable(false);
        stopSupportButton.setDisable(true);
        this.setFans();
    }

    /**
     * Dodaje do bazy danych wspieranie drużyny przez obecnie zalogowanego użytkownika.
     */
    @FXML
    void support() {
        entityManager.getTransaction().begin();
        ((Team)currentData).getTeamFans().add(currentUser);
        currentUser.getSupportedTeams().add(((Team)currentData));
        entityManager.getTransaction().commit();
        supportButton.setDisable(true);
        stopSupportButton.setDisable(false);
        this.setFans();
    }

    /**
     * Uzupełnia pola kontrolne okna informacjami z drużyny. nameLabel jest uzupełniany nazwą drużyny, coachLabel imieniem
     * i nazwiskiem trenera, który prowadzi drużynę, dateLabel jest uzupełniany datą założenia drużyny w przypadku braku
     * daty jest wpisany "-", pointsLabel jest uzupełniany ilością punktów, winsLabel jest uzupełniany ilością zwycięstw,
     * drwasLabel jest uzupełniany ilością remisów, losesLabel jest uzupełniany ilością porażek.
     */
    private void setTeamInfo() {
        nameLabel.setText(((Team)currentData).getName());
        coachLabel.setText(((Team)currentData).getCoach().getName() + " " + ((Team)currentData).getCoach().getSurname());
        String result;
        if (((Team)currentData).getCreationDate() == null) {
            result = "-";
        } else {
            result = ((Team)currentData).getCreationDate().toString();
        }
        dateLabel.setText(result);
        pointsLabel.setText(String.valueOf(((Team)currentData).getPoints()));
        winsLabel.setText(String.valueOf(((Team)currentData).getWins()));
        drawsLabel.setText(String.valueOf(((Team)currentData).getDraws()));
        losesLabel.setText(String.valueOf(((Team)currentData).getLoses()));
    }
}
