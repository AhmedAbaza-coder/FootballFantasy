package sample.controllers;

import com.jfoenix.controls.JFXTreeTableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import jdk.nashorn.internal.objects.annotations.Property;
import sample.database.AppDatabase;
import sample.models.User;

public class LeagueController implements Initializable {

    @FXML
    private TableView<User> LeagueTable;

    @FXML
    private TableColumn<User, Integer> RankColumn, PointsColumn;

    @FXML
    private TableColumn<User, String> NameColumn, EmailColumn, SquadColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LeagueTable.getStylesheets().add("league_styles.css");
        RankColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("Rank"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("FirstName"));
        ///EmailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Username"));
        SquadColumn.setCellValueFactory(new PropertyValueFactory<User, String>("SquadName"));
        PointsColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("totalPoints"));
        LeagueTable.setItems(getUsers());

    }

    private ObservableList<User> getUsers() {
        List<User> usersList = User.getCurrentUsers();
        for (int i = 0; i < usersList.size(); i++)
            usersList.get(i).setRank(i + 1);

        usersList.sort(User.BY_POINTS);
        ObservableList<User> users = FXCollections.observableArrayList(usersList);
        //System.out.println(users);
        return users;
    }
}
