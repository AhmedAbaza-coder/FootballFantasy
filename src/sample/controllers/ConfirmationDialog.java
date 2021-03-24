package sample.controllers;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;

public class ConfirmationDialog {

    private static Alert alert;
    private static DialogPane dialogPane;

    public static ButtonType showDialog(){
        if(alert == null){
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add("style.css");
            alert.initStyle(StageStyle.UNDECORATED);
        }

        alert.setHeaderText("Confirm Purchase?");

        ImageView icon = new ImageView("sample/controllers/Question.png");
        icon.setFitHeight(48);
        icon.setFitWidth(48);
        alert.getDialogPane().setGraphic(icon);
        Optional<ButtonType> result = alert.showAndWait();

        return result.get();
    }

}
