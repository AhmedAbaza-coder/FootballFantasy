package sample.controllers;

import com.jfoenix.controls.JFXToolbar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.database.AppDatabase;
import sample.models.Club;
import sample.models.Player;
import sample.models.User;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    @Override
    public void start( Stage primaryStage) throws Exception {


        Parent root = FXMLLoader.load(getClass().getResource("../view/register.fxml"));
        JFXToolbar titlebar = FXMLLoader.load(getClass().getResource("../view/stage_titlebar.fxml"));
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(titlebar);
        borderPane.setCenter(root);
        primaryStage.setTitle("Fantasy");
        primaryStage.setScene(new Scene(borderPane, 1300, 860));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        root.getStylesheets().add("style.css");
        primaryStage.show();

    }

    public static void main(String[] args) {
        System.out.println(Player.getRawPlayers().size());
        launch(args); }
}
