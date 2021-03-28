package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import sample.models.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerCardManageController implements Initializable {

    public InfoHandler Info;
    @FXML
    private ImageView playerImage;

    @FXML
    private Text playerName;

    @FXML
    private Text playerPrice;

    @FXML
    private VBox PlayerInformation;

    // private int playerIndex;

    public JFXButton getPlayerInform() {
        return playerInform;
    }

    @FXML
    private JFXButton playerInform;

    public Group getCaptainSign() {
        return CaptainSign;
    }

    @FXML
    private Group CaptainSign;

    @FXML
    private JFXButton playerChange;


    private Player selectedPlayer;
    int playerIndex;

    public void setName(String name) {
        playerName.setText(name);
    }

    public String getName() {
        return playerName.getText();
    }


    public void setSelection(Player player, int index) {
        // isSelected = true;
        selectedPlayer = player;
        playerIndex = index;
        InputStream stream = null;
        try {
//            stream = new FileInputStream("src/images/" + player.getClub().getLogoId());
            stream = new FileInputStream("src/images/" + player.getClub().getShirtLogo());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(stream);
        playerImage.setImage(image);
        playerName.setText(player.getLastName());
        playerPrice.setText(player.getPoints() + "");
    }

    @FXML
    void removeButtonClicked(ActionEvent event) {

    }
    @FXML
    void playerInformClicked(ActionEvent event) {
        Info.Changebtn();
        playerInform.setStyle("-fx-background-color: #4dadf7");
//      playerInform.setStyle("-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");
        playerInform.setTextFill(Paint.valueOf("#FFFFFF"));
        playerInform.setRipplerFill(Paint.valueOf("#FFFFFF"));
        Info.InfoBtn(selectedPlayer);
        Info.makeCaptain(CaptainSign);

    }


    public Player getSelectedPlayer() {
        return selectedPlayer;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void changeCaptainSign(){
        CaptainSign.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CaptainSign.setVisible(false);
        playerChange.setOnAction(event -> {
            Info.substitutePlayer(playerIndex,selectedPlayer);
        });
    }


}
