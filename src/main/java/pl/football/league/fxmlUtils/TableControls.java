package pl.football.league.fxmlUtils;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class TableControls {
    public static Label Label(int size, String  value){
        Label label = new Label(value);
        label.setStyle("-fx-background-color: white");
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
            label.setStyle("-fx-background-color: lightsteelblue ");
        });
        label.setOnMouseExited((MouseEvent event)-> {
            label.setCursor(Cursor.DEFAULT);
            label.setStyle("-fx-background-color: white");
        });

        return label;
    }

    public static Label LabelVGrow(int size, String value){
        Label label = LabelDecorated(size, value);
        label.setStyle("-fx-background-color: white");
        label.setMaxWidth(1.7976931348623157E308);
        return label;
    }
}
