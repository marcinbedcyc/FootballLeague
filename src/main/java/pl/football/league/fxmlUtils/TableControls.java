package pl.football.league.fxmlUtils;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * Zbiór metod statycznych odpowidzialnych za tworznie pewnych kontrolek dla okien JavyFx
 * @author Marcin Cyc
 * @see javafx.scene.control.Label
 * @see javafx.scene.control.ComboBox
 * @see javafx.scene.control.Button
 */
public class TableControls {
    /**
     *  Tworzy nowy przeroczysty pl.football.league.fxmlUtils.TableControls.Label(), z szarym obramowanie, o stałej
     *  wielkości podanej w argumencie oraz o wyśrodkowanej
     *  zawartości.
     * @param size szerokość Label'a
     * @param value zawartość Label'a
     * @return nowy Label
     */
    public static Label Label(int size, String  value){
        Label label = new Label(value);
        label.setStyle("-fx-background-color: transparent; -fx-border-color: gray; -fx-border-width: 1");
        label.setPrefWidth(size);
        label.setMinWidth(size);
        label.setMaxWidth(size);
        label.setAlignment(Pos.CENTER);
        return label;
    }

    /**
     * Tworzy nowy Label jak w pl.football.league.fxmlUtils.TableControls.Label() ze zmianą koloru tła, tekstu,
     * obramowania oraz kursora podczas najechania myszką.
     * @param size szerokość Label'a
     * @param value zawartość Label'a
     * @return nowy Label
     */
    public static  Label LabelDecorated(int size, String value){
        Label label = Label(size, value);
        label.setOnMouseEntered((MouseEvent event)-> {
            label.setCursor(Cursor.HAND);
            label.setStyle("-fx-background-color: darkgray; -fx-text-fill: white; -fx-border-color: lightgray; -fx-border-width: 1;");
        });
        label.setOnMouseExited((MouseEvent event)-> {
            label.setCursor(Cursor.DEFAULT);
            label.setStyle("-fx-background-color: transparent; -fx-border-color: lightgray; -fx-border-width: 1; -fx-text-fill: black");
        });

        return label;
    }

    /**
     * Tworzy nowy Label jak w pl.football.league.fxmlUtils.TableControls.LabelDecorated() skalowalny według szerokości
     * okna, w którym się znajduje.
     * @param size szerokość Label'a
     * @param value zawartość Label'a
     * @return nowy Label
     */
    public static Label LabelVGrow(int size, String value){
        Label label = LabelDecorated(size, value);
        label.setStyle("-fx-background-color: transparent; -fx-border-color: lightgray; -fx-border-width: 1");
        label.setMaxWidth(1.7976931348623157E308);
        label.setMaxHeight(1.7976931348623157E308);
        return label;
    }

    /**
     * Tworzy Label o zawartości "X", czerwonym, pogrubionym napisie, przezroczystym tle, jasno-szarym obramowaniu,
     * zmienijącym się kolorze napisu, kolorze tła oraz kursorze po najechaniu myszką.
     * @param size szerokość Label'a
     * @return nowy Label
     */
    public static Label LabelX(int size){
        Label label = new Label("X");
        label.setMinWidth(size);
        label.setPrefWidth(size);
        label.setMaxWidth(size);
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-text-fill: red; -fx-border-width: 1; -fx-border-color: lightgray; " +
                "-fx-background-color: transparent; -fx-font-weight: bold");
        label.setOnMouseEntered(event -> {
            label.setStyle("-fx-text-fill: white; -fx-border-width: 1; -fx-border-color: lightgray; " +
                    "-fx-background-color: red; -fx-font-weight: bold");
            label.setCursor(Cursor.HAND);
        });
        label.setOnMouseExited(event -> {
            label.setStyle("-fx-text-fill: red; -fx-border-width: 1; -fx-border-color: lightgray; " +
                    "-fx-background-color: transparent; -fx-font-weight: bold");
            label.setCursor(Cursor.DEFAULT);
        });
        return label;
    }

    /**
     * Tworzy nowy przycisk o zielonym kolorze tła, zaokrąglonym na krawędziach z biały napisem, o minimalenej szerokości
     * 100.
     * @param text tekst na przycisku
     * @return nowy przycisk
     */
    public static Button greenButton(String text){
        Button greenButton = new Button(text);
        greenButton.setStyle("-fx-background-color: forestgreen; -fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-background-radius: 15; -fx-cursor: hand;");
        greenButton.setMinWidth(100);
        return greenButton;
    }

    /**
     * Tworzy nowy przycisk o czerwonym kolorze tła, zaokrąglonym na krawędziach z biały napisem, o minimalenej szerokości
     * 100.
     * @param text tekst na przycisku
     * @return nowy przycisk
     */
    public static Button redButton(String text){
        Button redButton = new Button(text);
        redButton.setStyle("-fx-background-color: crimson; -fx-text-fill: white; -fx-font-weight: bold;" +
                " -fx-background-radius: 15; -fx-cursor: hand;");
        redButton.setMinWidth(100);
        return redButton;
    }

    /**
     * Tworzy nowy comboBox uzupełniony liczbami od 0 do 120
     * @param initValue początkowa wartość w comboBox'ie
     * @return nowy comboBox
     */
    public static ComboBox<Integer> comboBoxAge(Integer initValue){
        ComboBox<Integer> age = new ComboBox<>();
        age.setMinWidth(80);
        for(int i = 0; i <= 120 ; i++){
            age.getItems().add(i);
        }
        if(initValue != null) {
            age.setValue(initValue);
            age.setPromptText(initValue.toString());
        }
        else{
            age.setPromptText("0");
            age.setValue(0);
        }
        return  age;
    }

    /**
     * Tworzy nowy comboBox uzupełniony liczbami od 0 do 99
     * @param initValue początkowa wartość w comboBox'ie
     * @return nowy comboBox
     */
    public static ComboBox<Integer> comboBoxHundred(Integer initValue){
        ComboBox<Integer> comboBox = new ComboBox<>();
        comboBox.setMinWidth(80);
        for(int i = 0; i <= 99 ; i++){
            comboBox.getItems().add(i);
        }
        if(initValue != null){
            comboBox.setValue(initValue);
            comboBox.setPromptText(initValue.toString());
        }
        return  comboBox;
    }
}
