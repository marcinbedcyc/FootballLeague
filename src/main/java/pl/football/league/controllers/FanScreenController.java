package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Footballer;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.TableControls;
import pl.football.league.services.ItemShowService;

/**
 * Kontroler dla pliku fanScreen.fxml
 * @author Marcin Cyc
 * @see pl.football.league.services.ItemShowService
 */
public class FanScreenController extends ItemShowService {
    /**
     * Label z tytułem okna
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label titleLabel;

    /**
     * Label z imieniem kibica
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label nameLabel;

    /**
     * Label z nazwiskiem kibica
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label surnameLabel;

    /**
     * Label z wiekiem kibica
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label ageLabel;

    /**
     * Label z pseudonimem kibica
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label nicknameLabel;

    /**
     * VBox z piłkarzami wspieranymi przez kibica
     * @see javafx.scene.layout.VBox
     */
    @FXML
    private VBox footballersVBox;

    /**
     * VBox z druzynami wspieranymi przez kibica
     * @see javafx.scene.layout.VBox
     */
    @FXML
    private VBox teamsVBox;

    /**
     * Inicjalizacja okna: ustawienie informacji o kibicu, uzupełnienie odpowednich VBox'ów piłkarzami i drużynami wspieranymi
     * przez kibica.
     */
    @FXML
    void initialize() {
        setFanInfo();
        setFootballers();
        setTeams();
    }

    /**
     * Uzupełnia pola kontrolne informacjami o kibicu. titleLabel jesy uzupełniany imieniem naziwskiem oraz pseudonimem,
     * nameLabel imieniem kibica, surnameLabel nazwiskiem kibica, nicknameLabel pseudonimem kibica, ageLabel wiekiem
     * kibica, w przypadku braku uzupełniany jest "-".
     */
    private void setFanInfo(){
        titleLabel.setText(((Fan)currentData).getName() + " " + ((Fan)currentData).getSurname() + " ( " + ((Fan)currentData).getNickname() + " )");
        nameLabel.setText(((Fan)currentData).getName());
        surnameLabel.setText(((Fan)currentData).getSurname());
        nicknameLabel.setText(((Fan)currentData).getNickname());

        String result = String.valueOf(((Fan)currentData).getAge());
        if(result == null)  result = "-";
        ageLabel.setText(result);
    }

    /**
     * Pobiera z bazy danych piłkarzy, których wspiera kibic i uzupełnia nimi odpowiedniego VBox'a. W przypadku braku piłkarzy
     * zostaje wyświetlony napis "Brak wspieranych piłkarzy". Dodaje akcje kliknięcia na imię i nazwisko piłkarza. Akcja
     * otwiera nowe okno z informacjami o tym piłkarzu.
     */
    private void setFootballers(){
        if(((Fan)currentData).getSupportedFootballers().isEmpty()){
            Label emptyLabel = TableControls.Label(150, "Brak wspieranych piłkarzy");
            emptyLabel.setMaxWidth(1.7976931348623157E308);
            footballersVBox.getChildren().add(emptyLabel);
        }
        else {
            for (Footballer f : ((Fan)currentData).getSupportedFootballers()) {
                Label footballerLabel = TableControls.LabelVGrow(150, f.getName() + " " + f.getSurname());
                footballerLabel.setOnMouseClicked(event -> {
                    FootballerScreenController footballerScreenController = new FootballerScreenController();
                    footballerScreenController.setDependencies(currentUser, entityManager, mainScreenController, f);
                    loadCenterScene("/fxml/footballerScreen.fxml", footballerScreenController);

                });
                footballersVBox.getChildren().add(footballerLabel);
            }
        }
    }

    /**
     * Pobiera z bazy danych druzyny, które wspiera kibic i uzupełnia nimi odpowiedniego VBox'a. W przypadku braku drużyn
     * zostaje wyświetlony napis "Brak wspieranych drużyn". Dodaje akcje kliknięcia na nazwę drużyny. Akcja otwiera nowe
     * okno z informacjami o tej drużynie.
     */
    private void setTeams(){
        if(((Fan)currentData).getSupportedTeams().isEmpty()){
            Label emptyLabel = TableControls.Label(150, "Brak wspieranych drużyn");
            emptyLabel.setMaxWidth(1.7976931348623157E308);
            teamsVBox.getChildren().add(emptyLabel);
        }
        else {
            for (Team t : ((Fan)currentData).getSupportedTeams()) {
                Label teamLabel = TableControls.LabelVGrow(150, t.getName());
                teamLabel.setOnMouseClicked(event -> {
                    TeamScreenController teamScreenController = new TeamScreenController();
                    teamScreenController.setDependencies(currentUser, entityManager, mainScreenController, t);
                    loadCenterScene("/fxml/teamScreen.fxml", teamScreenController);
                });
                teamsVBox.getChildren().add(teamLabel);
            }
        }
    }
}
