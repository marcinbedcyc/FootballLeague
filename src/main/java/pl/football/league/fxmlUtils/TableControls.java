package pl.football.league.fxmlUtils;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
        label.setStyle("-fx-text-fill: red; -fx-border-width: 1; -fx-border-color: lightgray; -fx-background-color: transparent; -fx-font-weight: bold");
        label.setOnMouseEntered(event -> {
            label.setStyle("-fx-text-fill: white; -fx-border-width: 1; -fx-border-color: lightgray; -fx-background-color: red; -fx-font-weight: bold");
            label.setCursor(Cursor.HAND);
        });
        label.setOnMouseExited(event -> {
            label.setStyle("-fx-text-fill: red; -fx-border-width: 1; -fx-border-color: lightgray; -fx-background-color: transparent; -fx-font-weight: bold");
            label.setCursor(Cursor.DEFAULT);
        });
        return label;
    }
}
