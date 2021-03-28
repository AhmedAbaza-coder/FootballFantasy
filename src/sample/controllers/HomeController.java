package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import sample.database.AppDatabase;
import sample.models.Player;
import sample.models.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private Text userName;

    @FXML
    private Text bestSquad;

    @FXML
    private Text bestSquadPoints;

    @FXML
    private Text total_point;

    @FXML
    private ImageView tshirt;

    @FXML
    private Text PlayerName;

    @FXML
    private Text Playerpoint;

    User user = User.getLoggedInUser();
    User bestUser= AppDatabase.getInstance().getBestSquadUser();
    Player bestPlayer=AppDatabase.getInstance().getBestPlayer();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        total_point.setText(user.getTotalPoints() +"");
        userName.setText(user.getFirstName()+" " +user.getLastName());
        bestSquad.setText(bestUser.getSquadName());
        bestSquadPoints.setText(bestUser.getTotalPoints()+"");
        PlayerName.setText(bestPlayer.getLastName());
        Playerpoint.setText(bestPlayer.getPoints()+" Points");

     /*   InputStream stream = null;
        try {
//            stream = new FileInputStream("src/images/" + player.getClub().getLogoId());
            stream = new FileInputStream("../images/" + bestPlayer.getClub().getShirtLogo());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(stream);
        tshirt.setImage(image);
*/
    }


}