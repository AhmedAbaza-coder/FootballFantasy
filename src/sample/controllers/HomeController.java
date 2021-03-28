package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import sample.models.User;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Text total_point;
    User user = User.getLoggedInUser();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        total_point.setText(user.getTotalPoints() +" ");

    }


}