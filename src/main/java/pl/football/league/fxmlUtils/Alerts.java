package pl.football.league.fxmlUtils;

import javafx.scene.control.Alert;

public class Alerts {
    public static Alert wrongLoginAlert(){
        return  alert("Zajety login", "Uyztkownik o podanym loginie juz istnieje!", "Prosze wybrac inny login.");
    }

    public static Alert diffrentPasswords(){
        return  alert("Rozne hasla", "Podano 2 rozne hasla!", "Prosze podac poprawne dane.");
    }

    public static Alert wrongPassword(){
        return  alert("Zle haslo","Podane haslo jest nieprawidlowe",  "Prosze podac poprawne haslo.");
    }

    public static  Alert emptyPasswordField(){
        return alert("Puste pole z haslem", "Nie podano nowego hasla", "Prosze podac nowe haslo.");
    }

    public static Alert emptyField(){
        return alert("Puste pole", "Jedno z obowiazkowych pol jest puste!", "Prosze je uzupelnic.");
    }

    public static Alert noCoach(){
        return alert("Brak Trenera", "Nie wybrano trenera drużyny", "Prosze wybrać trenera z listy");
    }

    public static Alert busyCoach(){
        return alert("Nie można usunąć", "Nie można usunąć, trener ma przypisaną drużynę.", "Usun druzyne lub zmien trenera drużynie aby usunąć tego trenera.");
    }

    public static Alert sameTeams(){
        return alert("Takie same drużyny", "Wybrano taką samą drużynę jako gospodarza i gościa meczu!", "Prosze zmienić jedną z drużyn.");
    }

    public static Alert noHomeTeam(){
        return  alert("Brak gospodarza", "Nie wybrano gospodarza meczu.", "Proszę wybrać gopodarza meczu!");
    }

    public static Alert noAwayTeam(){
        return  alert("Brak gościa", "Nie wybrano gościa meczu.", "Proszę wybrać gościa meczu!");
    }

    public static Alert noPosition(){
        return alert("Brak pozycji", "Nie określono pozycji dla zawodnika!", "Prosze wybrać.");
    }

    public static Alert transactionFail(){
        return alert("Blad", "Blad przy modyfikowaniu bazy", "Prosze sprobowac jeszcze raz.");
    }

    public static Alert wrongNumber(){
        return alert("Zla liczba", "Podano lanuch znakow zamiast liczby!", "Prosze podac poprawne dane.");
    }

    public static Alert loginFail(){
        return alert("Blad logowania", "Podane dane logowania sa niepoprawne!", "Sprobuj ponowanie. ");
    }

    public static Alert success(){
        Alert succesAlert = alert("Powodzenie", "Operacja zakończona powodzeniem!", "Naciśnij OK");
        succesAlert.setAlertType(Alert.AlertType.INFORMATION);
        return succesAlert;
    }

    public static Alert matchInBase(){
        return alert("Mecz w bazie", "Taki meczy istnieje już w bazie.", "Proszę wybrać inne drużyny");

    }

    private static Alert alert(String title, String header, String context){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.getDialogPane().getStylesheets().add(Alerts.class.getResource("/css/myDialogs.css").toExternalForm());
        return alert;
    }
}
