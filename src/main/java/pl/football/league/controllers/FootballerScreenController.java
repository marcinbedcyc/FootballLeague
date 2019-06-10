package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Footballer;
import pl.football.league.fxmlUtils.TableControls;
import pl.football.league.services.ItemShowService;

/**
 * Kontroler dla pliku footballerScreen.fxml
 * @author Marcin Cyc
 * @see pl.football.league.services.ItemShowService
 */
public class FootballerScreenController extends ItemShowService {
    @FXML
    private VBox fansVBox;

    /**
     * Label z tytułem okna
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label titleLabel;

    /**
     * Label z imieniem piłkarza
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label nameLabel;

    /**
     * Label z nazwiskiem piłkarza
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label surnameLabel;

    /**
     * Label z nazwą drużyny piłkarza
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label teamLabel;

    /**
     * Label z pozycją piłkarza
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label positionLabel;

    /**
     * Label z numerem na koszulce piłkarza
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label shirtNumberLabel;

    /**
     * Przycisk umożliwiającym kibicowanie piłkarzowi
     * @see javafx.scene.control.Button
     */
    @FXML
    private Button supportButton;

    /**
     * Przycisk umożliwiającym zaprzestanie kibicowania piłkarzowi
     * @see javafx.scene.control.Button
     */
    @FXML
    private Button stopSupportButton;

    /**
     * Inicjalizacja okna: uzupełnieni odpowedniego VBox'a kibcami, którzy kibicują piłkarzowi oraz uzupełenienie pól
     * kontrolnych informacjami o piłkarzu. Dodanie akcji kliknięcia na nazwę drużyny, która otwiera okno z informacjami
     * o tej drużynie.
     */
    @FXML
    void initialize() {
        supportButton.setDisable(false);
        stopSupportButton.setDisable(true);

        setFootballerInfo();
        setFans();

        teamLabel.setOnMouseClicked(event -> {
            TeamScreenController teamScreenController = new TeamScreenController();
            teamScreenController.setDependencies(currentUser, entityManager, mainScreenController, ((Footballer)currentData).getTeam());
            loadCenterScene("/fxml/teamScreen.fxml", teamScreenController);
        });
    }

    /**
     * Dodaje do bazy danych wspieranie piłkarza przez obecnie zalogowanego użytkownika.
     */
    @FXML
    public void support(){
        entityManager.getTransaction().begin();
        ((Footballer)currentData).getFans().add(currentUser);
        currentUser.getSupportedFootballers().add(((Footballer)currentData));
        entityManager.getTransaction().commit();
        supportButton.setDisable(true);
        stopSupportButton.setDisable(false);
        this.setFans();
    }

    /**
     * Usuwa z bazy danych wspieranie piłkarza przez obecnie zalogowanego użytkownika.
     */
    @FXML
    void stopSupport() {
        entityManager.getTransaction().begin();
        ((Footballer)currentData).getFans().remove(currentUser);
        currentUser.getSupportedFootballers().remove(((Footballer)currentData));
        entityManager.getTransaction().commit();
        supportButton.setDisable(false);
        stopSupportButton.setDisable(true);
        this.setFans();
    }

    /**
     * Pobiera kibiców wspierających piłkarza z bazy danych i uzupełnia danymi odpowiedniego VBox'a. W przypadku braku
     * kibiców zostaje wyświetlony napis "Brak kibiców". Dodaje akcję kliknięcia na pseduonim kibica. Akcja otwiera nowe
     * okno z informacjami o tym kibicu.
     */
    private void setFans(){
        fansVBox.getChildren().remove(0, fansVBox.getChildren().size());
        if(((Footballer)currentData).getFans().isEmpty()){
            Label emptyLabel = TableControls.Label(150, "Brak Kibiców");
            emptyLabel.setMaxWidth(1.7976931348623157E308);
            fansVBox.getChildren().add(emptyLabel);
        }
        else {
            for (Fan f : ((Footballer)currentData).getFans()) {
                if (f.getFanID() == currentUser.getFanID()) {
                    supportButton.setDisable(true);
                    stopSupportButton.setDisable(false);
                }
                Label fanLabel = TableControls.LabelVGrow(150, f.getNickname());
                fanLabel.setOnMouseClicked(event -> {
                    FanScreenController fanScreenController = new FanScreenController();
                    fanScreenController.setDependencies(f, entityManager, mainScreenController, currentUser);
                    loadCenterScene("/fxml/fanScreen.fxml", fanScreenController);
                });
                fansVBox.getChildren().add(fanLabel);
            }
        }
    }

    /**
     * Uzupełnia pola kontrolne okna informacjami o piłkarzu. nameLabel jest uzupełniany imieniem piłkarza, surnameLabel
     * jest uzupełniany nazwiskiem piłkarza, teamLabel jest uzupełniany nazwą drużyny, w przypadku braku drużyny wpisane
     * jest "-", positionLabel uzupełniany jest pozycją piłkarza, shirtNumberLabel uzupełniany jest numerem na koszulce
     * piłkarza, titleLabel uzupełniany jest imieniem i nazwiskiem piłkarza.
     */
    private void setFootballerInfo(){
        nameLabel.setText( ((Footballer)currentData).getName() );
        surnameLabel.setText(((Footballer)currentData).getSurname());

        if(((Footballer)currentData).getTeam() != null)
            teamLabel.setText(((Footballer)currentData).getTeam().getName());
        else {
            teamLabel.setText("-");
            teamLabel.setCursor(Cursor.DEFAULT);
            teamLabel.setStyle("-fx-background-color: transparent; -fx-font-size: 20;  -fx-text-fill: forestgreen;  -fx-font-weight: bold;");
        }

        positionLabel.setText(((Footballer)currentData).getPosition());

        if(((Footballer)currentData).getNumber() != null)
            shirtNumberLabel.setText(String.valueOf(((Footballer)currentData).getNumber()));
        else
            shirtNumberLabel.setText("-");

        titleLabel.setText(((Footballer)currentData).getName() + " " +  ((Footballer)currentData).getSurname());
    }
}