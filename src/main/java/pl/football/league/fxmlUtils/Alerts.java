package pl.football.league.fxmlUtils;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Zbiór metod statycznych opowiedzialnych za wyświetlanie różnych komunikatów
 * @author Marcin Cyc
 * @see javafx.scene.control.Alert
 */
public class Alerts {
    /**
     * @return komunikat o zajętym loginie
     */
    public static Alert wrongLoginAlert(){
        return  alert("Zajęty login", "Użytkownik o podanym loginie już istnieje!", "Proszę wybrać inny login.");
    }

    /**
     * @return komunikat o podanych 2 różnych haseł
     */
    public static Alert diffrentPasswords(){
        return  alert("Różne hasła", "Podano 2 różne hasła!", "Proszę podać poprawne dane.");
    }

    /**
     * @return komunikat o podanym niepoprawnym haśle
     */
    public static Alert wrongPassword(){
        return  alert("Złe hasło","Podane hasło jest nieprawidłowe",  "Proszę podać poprawne hasło.");
    }

    /**
     * @return komunikat o pustym polu zmiany hasła
     */
    public static  Alert emptyPasswordField(){
        return alert("Puste pole z hasłem", "Nie podano nowego hasła", "Proszę podać nowe hasło.");
    }

    /**
     * @return komunikat o pustym polu obowiązkowm
     */
    public static Alert emptyField(){
        return alert("Puste pole", "Jedno z obowiązkowych pól jest puste!", "Proszę je uzupelnić.");
    }

    /**
     * @return komunikat o braku wyboru trenera dla drużyny
     */
    public static Alert noCoach(){
        return alert("Brak Trenera", "Nie wybrano trenera drużyny", "Proszę wybrać trenera z listy");
    }

    /**
     * @return komunikat o braku możliwości usunięcie trenera
     */
    public static Alert busyCoach(){
        return alert("Nie można usunąć", "Nie można usunąć, trener ma przypisaną drużynę.", "Usuń drużynę lub zmień trenera drużynie aby usunąć tego trenera.");
    }

    /**
     * @return komunikat o wyborze takich samych drużyn spotkania
     */
    public static Alert sameTeams(){
        return alert("Takie same drużyny", "Wybrano taką samą drużynę jako gospodarza i gościa meczu!", "Proszę zmienić jedną z drużyn.");
    }

    /**
     * @return komunikat o braku wyboru gospodarza meczu
     */
    public static Alert noHomeTeam(){
        return  alert("Brak gospodarza", "Nie wybrano gospodarza meczu.", "Proszę wybrać gopodarza meczu!");
    }

    /**
     * @return komunikat o braku wyboru gościa meczu
     */
    public static Alert noAwayTeam(){
        return  alert("Brak gościa", "Nie wybrano gościa meczu.", "Proszę wybrać gościa meczu!");
    }

    /**
     * @return komunikat o braku wyboru pozycji dla piłkarza
     */
    public static Alert noPosition(){
        return alert("Brak pozycji", "Nie określono pozycji dla zawodnika!", "Proszę wybrać pozycję z listy.");
    }

    /**
     * @return komunikat o błędzie przy modyfikowaniu bazy
     */
    public static Alert transactionFail(){
        return alert("Błąd", "Błąd przy modyfikowaniu bazy", "Proszę spróbować jeszcze raz.");
    }

    /**
     * @return komunikat o istniejącej już nazwie drużyny w bazie
     */
    public static Alert sameTeamName(){
        return alert("Zajęta nazwa drużyny", "Taka nazwa drużyny już istnieje w lidze.", "Proszę wybrać inną nazwę.");
    }

    /**
     * @return komunikat o wpisaniu napisu zamiast liczby lub liczby ujemnej
     */
    public static Alert wrongNumber(){
        return alert("Zła liczba", "Podano łańuch znaków zamiast liczby!", "Proszę podać poprawne dane.");
    }

    /**
     * @return komunikat o niepoprawnych danych logowania
     */
    public static Alert loginFail(){
        return alert("Błąd logowania", "Podane dane logowania sa niepoprawne!", "Spróbuj ponowanie. ");
    }

    /**
     * @return komunikat o zakończeniu operacji sukceesem
     */
    public static Alert success(){
        Alert succesAlert = alert("Powodzenie", "Operacja zakończona powodzeniem!", "Naciśnij OK");
        succesAlert.setAlertType(Alert.AlertType.INFORMATION);
        return succesAlert;
    }

    /**
     * @return komunikat o istnieniu meczu w bazie danych
     */
    public static Alert matchInBase(){
        return alert("Mecz w bazie", "Taki meczy istnieje już w bazie.", "Proszę wybrać inne drużyny");
    }

    /**
     * Tworzy nowy Alert (okienko z komunikatem) typu Alert.AlertType.ERROR oraz ustawia ikonkę okna.
     * @param title tytuł komunikatu
     * @param header nagłówek komunikatu
     * @param context zawartość komuniaktu
     * @return utworzony nowy komunikat
     */
    private static Alert alert(String title, String header, String context){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        Stage stage = (Stage)alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Alerts.class.getResourceAsStream("/images/soccer.png")));
        alert.getDialogPane().getStylesheets().add(Alerts.class.getResource("/css/myDialogs.css").toExternalForm());
        return alert;
    }
}
