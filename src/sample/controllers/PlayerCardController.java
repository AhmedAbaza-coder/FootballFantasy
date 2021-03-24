package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import sample.models.Player;

public class PlayerCardController implements Initializable {


    @FXML
    private ImageView playerImage;

    @FXML
    private Text playerName;

    private int playerIndex;
    @FXML
    private JFXButton buttonRemove;

    @FXML
    private Text playerPrice;

    public RemoveHandler removeHandler;

    private boolean isSelected = false;

    public Player getPlayer() {
        return selectedPlayer;
    }

    public void setSelectedPlayer(Player selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
    }

    private Player selectedPlayer;

    public void setName(String name){
        playerName.setText(name);
    }

    public String getName(){
        return playerName.getText();
    }

    public void handleRemoval(){
        isSelected = false;
        buttonRemove.setVisible(isSelected);
        playerName.setText("ADD Player");
        InputStream stream = null;
        try {
//            stream = new FileInputStream("src/images/" + player.getClub().getLogoId());
            stream = new FileInputStream("src/sample/view/AddTshirt.png");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(stream);
        playerImage.setImage(image);

    }

    @FXML
    void removeButtonClicked(ActionEvent event) {
//        handleRemoval();
        removeHandler.removePlayer(selectedPlayer, playerIndex);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonRemove.setVisible(isSelected);
    }

    public void setSelection(Player player, int index) {
        isSelected = true;
        selectedPlayer = player;
        playerIndex = index;
        buttonRemove.setVisible(isSelected);
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
        playerPrice.setText(player.getPrice()+"");
    }
}
