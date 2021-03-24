package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class TitleBarController {

    @FXML
    private HBox CloseApp;

    @FXML
    void closeApp(MouseEvent event) {
        StageUtils.closeApp();
    }

    @FXML
    void maximize(MouseEvent event) {
        StageUtils.maximize(event);
    }

    @FXML
    void minimize(MouseEvent event) {
        StageUtils.minimize(event);
    }

    @FXML
    void Dragged(MouseEvent event) {
        StageUtils.onDragged(event);
    }

    @FXML
    void Pressed(MouseEvent event) {
        StageUtils.onPressed(event);
    }

}

