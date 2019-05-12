package pl.football.league.fxmlUtils;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class TableControls {
    public static Label Label(int size, String  value){
        Label label = new Label(value);
        label.setStyle("-fx-background-color: transparent; -fx-border-color: gray; -fx-border-width: 1");
        label.setPrefWidth(size);
        label.setMinWidth(size);
        label.setMaxWidth(size);
        label.setAlignment(Pos.CENTER);
        return label;
    }

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

    public static Label LabelVGrow(int size, String value){
        Label label = LabelDecorated(size, value);
        label.setStyle("-fx-background-color: transparent; -fx-border-color: lightgray; -fx-border-width: 1");
        label.setMaxWidth(1.7976931348623157E308);
        label.setMaxHeight(1.7976931348623157E308);
        return label;
    }

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

    public static Button greenButton(String text){
        Button greenButton = new Button(text);
        greenButton.setStyle("-fx-background-color: forestgreen; -fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-background-radius: 15; -fx-cursor: hand;");
        greenButton.setMinWidth(100);
        return greenButton;
    }

    public static Button redButton(String text){
        Button redButton = new Button(text);
        redButton.setStyle("-fx-background-color: crimson; -fx-text-fill: white; -fx-font-weight: bold;" +
                " -fx-background-radius: 15; -fx-cursor: hand;");
        redButton.setMinWidth(100);
        return redButton;
    }

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

    public static ComboBox<Integer> comboBoxHundred(Integer iniValue){
        ComboBox<Integer> comboBox = new ComboBox<>();
        comboBox.setMinWidth(80);
        for(int i = 0; i <= 99 ; i++){
            comboBox.getItems().add(i);
        }
        if(iniValue != null){
            comboBox.setValue(iniValue);
            comboBox.setPromptText(iniValue.toString());
        }
        return  comboBox;
    }
}
