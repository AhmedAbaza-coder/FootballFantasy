package sample.customCells;

import com.jfoenix.controls.JFXButton;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import sample.models.Player;

public class PurchaseCell extends ListCell<Player> {

    private Text PlayerName, PLayerTeam, PlayerPosition, PlayerPrice;
    private HBox PlayerCard, AddPlayer, HboxPosAndTeam;
    private VBox PlayerForm;
    private JFXButton PlayerInformBtn;
    private ImageView TShirtPlayer;
    private Line SeparateLine;


    public PurchaseCell() {
        super();

        PLayerTeam = new Text();
        PlayerPosition = new Text();
        PLayerTeam.getStyleClass().add("PLayerTeam");
        PlayerPosition.getStyleClass().add("PlayerPostion");

        HboxPosAndTeam = new HBox(PLayerTeam, PlayerPosition);
        HboxPosAndTeam.setSpacing(10);
        HboxPosAndTeam.setAlignment(Pos.CENTER);

        PlayerName = new Text();
        PlayerName.getStyleClass().add("PlayerName");

        PlayerForm = new VBox(PlayerName, HboxPosAndTeam);
        PlayerForm.setSpacing(10);
        PlayerForm.setAlignment(Pos.CENTER_LEFT);
//            Image image = null;
//            try {
//                image = new Image(new FileInputStream("src/images/T-Shirt.png"));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
        TShirtPlayer = new ImageView();

        TShirtPlayer.setFitHeight(39);
        TShirtPlayer.setFitWidth(35);
        TShirtPlayer.getStyleClass().add("TShirtPlayer");

        AddPlayer = new HBox(TShirtPlayer, PlayerForm);
        AddPlayer.setSpacing(10);
        AddPlayer.setAlignment(Pos.CENTER);
        AddPlayer.setMargin(AddPlayer, new Insets(0, 0, 0, 5));

        PlayerPrice = new Text();
        PlayerPrice.getStyleClass().add("PlayerPrice");
        PlayerPrice.setTextAlignment(TextAlignment.CENTER);

        SeparateLine = new Line();
        SeparateLine.setStroke(Color.WHITE);
        SeparateLine.setStrokeWidth(1);
        SeparateLine.setStartX(99.79289245605469);
        SeparateLine.setStartY(38.29289245605469);
        SeparateLine.setEndX(99.79098510742188);
        SeparateLine.setEndY(69.08578491210938);

        PlayerInformBtn = new JFXButton("i");
        PlayerInformBtn.getStyleClass().add("PlayerInformBtn");
        PlayerInformBtn.setPadding(new Insets(0, 10, 0, 0));


        PlayerCard = new HBox(PlayerInformBtn, AddPlayer, SeparateLine, PlayerPrice);
        PlayerCard.setSpacing(10);
        PlayerCard.setAlignment(Pos.CENTER_LEFT);
        PlayerCard.setMargin(PlayerCard, new Insets(0, 10, 0, 0));
        PlayerCard.getStyleClass().add("PlayerCard");
        PlayerCard.getStylesheets().add("style.css");

    }


    @Override
    protected void updateItem(Player item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
//            if (item.getClub() == null)
//                item.setClubObject();


            PlayerName.setText(item.getFullName());
            PlayerPosition.setText(item.getPosition());
            PlayerPrice.setText(item.getPrice() + "");
            PLayerTeam.setText(item.getClubName());


            Image shirtImage = null;
            try {
                shirtImage = new Image(new FileInputStream("src/images/" + item.getClub().getShirtLogo()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            TShirtPlayer.setImage(shirtImage);

//                setGraphic(PlayerCard);
//
//            PlayerInformBtn.setOnAction(event -> {
//                InputStream stream = null;
//                try {
//                    stream = new FileInputStream("src/images/" + item.getPictureId());
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                Image image = new Image(stream);
//                PlayerPic.setImage(image);
//
//                InputStream streamLogo = null;
//                try {
//                    streamLogo = new FileInputStream("src/images/" + item.getClub().getLogoId());
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                Image imageLogo = new Image(streamLogo);
//                logoID.setImage(imageLogo);
//
//                PlayerNum.setText(item.getPlayerNumber() + "");
//                FirstName.setText(item.getFirstName());
//                LastName.setText(item.getLastName());
//                clubname.setText(item.getClubName());
//                position.setText(item.getPosition());
//                Nationality.setText(item.getNationality());
//                Appearances.setText(item.getAppearance() + "");
//                Goals.setText(item.getGoals() + "");
//                PLD.setText(item.getPld());
//                InformationPopup.setVisible(true);
//
//
//            });
            setGraphic(PlayerCard);

        }
        else {
            setGraphic(null);
        }
    }
}
