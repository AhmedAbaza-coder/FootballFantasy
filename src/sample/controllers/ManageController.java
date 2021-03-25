package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import sample.models.Player;
import sample.models.User;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;


public class ManageController implements Initializable, InfoHandler {

    @FXML
    private StackPane  InformationPopup;

    @FXML
    private ImageView PlayerPic,logoID;

    @FXML
    private ImageView PlayerPicInfo,logoIDInfo;

    @FXML
    private Text PlayerNum ,Name,position,clubname,Nationality,PLD,Appearances,Goals;

    @FXML
    private Text PlayerNumInfo,NameInfo,clubnameInfo,positionInfo;

    @FXML
    private Text SquadName;

    @FXML
    private JFXButton MakeCaptain,ViewInformation;

    @FXML
    private HBox PlayersPaneGK,PlayersPaneDEF,PlayersPaneMID,PlayersPaneFW,PlayersPaneSUB;

    @FXML
    private VBox PlayerInformation;

    @FXML
    private JFXComboBox<String> FormationsSelection;

    final int PLAYERS_NUMBER = 15;
    private int diff,mid,fw;
    boolean index= true;

    public ManageController() {
        getPlayerCards();
    }

    @FXML
    void ExitPopUpClicked(MouseEvent event) {
        InformationPopup.setVisible(false);
    }

    PlayerCardManageController playerCard = new PlayerCardManageController();
    ObservableList<String> formationsOptions = FXCollections.observableArrayList("4-3-3", "4-4-2", "3-4-3", "3-5-2", "5-3-2", "5-4-1", "4-5-1");
    List<AnchorPane> playerCards = new ArrayList<>();
    List<PlayerCardManageController> playerControllers = new ArrayList<>();

    User user = User.getLoggedInUser();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PlayerInformation.setVisible(false);
        InformationPopup.setVisible(false);
        SquadName.setText(user.getSquadName());
        FormationsSelection.setValue("5-4-1");
        initViewSorting();
        PlanSelection();
    }

    private void getPlayerCards() {
        Thread thread = new Thread(() -> {

            Runnable updater = new Runnable() {

                @Override
                public void run() {
                    positionPlayerCards(FormationsSelection.getValue());
                }
            };

            try {
                for (int i = 0; i < PLAYERS_NUMBER; i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("../view/player_card_Manage.fxml"));
                    AnchorPane card = fxmlLoader.load();
                    card.setCache(true);
                    card.setCacheShape(true);
                    card.setCacheHint(CacheHint.SPEED);
                    playerCards.add(card);
                    PlayerCardManageController controller = fxmlLoader.getController();
                    controller.Info = this;
                    playerControllers.add(controller);
                }

                Platform.runLater(updater);


            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }

    private void initViewSorting() {
        FormationsSelection.setItems(formationsOptions);
    }

    private void PlanSelection() {
        FormationsSelection.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                positionPlayerCards(newValue);
            }
        });
    }

    private void positionPlayerCards(String newValue) {
        diff = Integer.parseInt(newValue.substring(0, 1));
        mid = Integer.parseInt(newValue.substring(2, 3));
        fw = Integer.parseInt(newValue.substring(4));
        //System.out.println(diff);
        //System.out.println(mid);
        //System.out.println(fw);
        PlayersPaneGK.getChildren().clear();
        PlayersPaneDEF.getChildren().clear();
        PlayersPaneMID.getChildren().clear();
        PlayersPaneFW.getChildren().clear();
        PlayersPaneSUB.getChildren().clear();
        for (int i = 0; i < PLAYERS_NUMBER; i++) {
            AnchorPane card = playerCards.get(i);
            if (i == 0) {
                PlayersPaneGK.getChildren().add(card);
            }
            if (i > 0 && i <= diff) {
                PlayersPaneDEF.getChildren().add(card);
            }
            if (i >= diff + 1 && i <= diff + mid) {
                PlayersPaneMID.getChildren().add(card);
            }
            if (i >= diff + mid + 1 && i <= diff + mid + fw) {
                PlayersPaneFW.getChildren().add(card);
            }
            if (i >= 11) {
                PlayersPaneSUB.getChildren().add(card);
            }
        }
        insertPlayers();
    }

    private void insertPlayers() {
        List<Player> getPlayers = new ArrayList<>(user.getSelectedPlayers());
        getPlayers.forEach(player -> {
            player.setSelected(false);
        });
        int indexGK = 0, indexDEF = 0, indexMID = 0, indexFW = 0;
        for (int i = 0; i < PLAYERS_NUMBER; i++) {
            PlayerCardManageController playerCard = playerControllers.get(i);


//            System.out.println(hasNext + " " +  i);
            for (Player player : getPlayers) {
                if (player.getPosition().equals(Player.P_GK) && indexGK == 0 && i == 0 && !player.isSelected()) {
                    playerCard.setSelection(player);
                    player.setSelected(true);
                    indexGK++;
                    break;
                } else if (player.getPosition().equals(Player.P_DEF) && indexDEF < diff && i > 0 && i <= diff && !player.isSelected()) {
                    playerCard.setSelection(player);
                    player.setSelected(true);
                    indexDEF++;
                    break;
                } else if (player.getPosition().equals(Player.P_MID) && indexMID < diff + mid && i >= diff + 1 && i <= diff + mid && !player.isSelected()) {
                    playerCard.setSelection(player);
                    player.setSelected(true);
                    indexMID++;
                    break;
                } else if (player.getPosition().equals(Player.P_FW) && indexFW < diff + mid + fw && i >= diff + mid + 1 && i <= diff + mid + fw && !player.isSelected()) {
                    playerCard.setSelection(player);
                    indexFW++;
                    player.setSelected(true);

                    break;
                }
            }
        }

        getPlayers.removeIf(Player::isSelected);
        int j = 0;
        for (int i = 11; i < 15; i++) {
            Player player = getPlayers.get(j);
            PlayerCardManageController playerCard = playerControllers.get(i);
            playerCard.setSelection(player);
            player.setSelected(true);
            j++;
        }

        getPlayers.clear();
    }

    public void updateInfo(Player item) {
        NameInfo.setText(item.getFirstName()+" "+item.getLastName());
        clubnameInfo.setText(item.getClubName());
        positionInfo.setText(item.getPosition());
        PlayerNumInfo.setText(item.getPlayerNumber()+"");
        InputStream streamLogo = null;
        try {
            streamLogo = new FileInputStream("src/images/" + item.getClub().getLogoId());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image imageLogo = new Image(streamLogo);
        logoIDInfo.setImage(imageLogo);

        InputStream stream = null;
        try {
            stream = new FileInputStream("src/images/players/" + item.getPictureId());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(stream);
        PlayerPicInfo.setImage(image);
    }

    @Override
    public void InfoBtn(Player player) {
        updateInfo(player);
        ViewInformation.setOnAction(event -> {
            PlayerNum.setText(player.getPlayerNumber() + "");
            Name.setText(player.getFirstName()+" "+player.getLastName());
            clubname.setText(player.getClubName());
            position.setText(player.getPosition());
            Nationality.setText(player.getNationality());
            Appearances.setText(player.getAppearance() + "");
            Goals.setText(player.getGoals() + "");
            PLD.setText(player.getPld());

            InputStream stream = null;
            try {
                stream = new FileInputStream("src/images/players/" + player.getPictureId());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Image image = new Image(stream);
            PlayerPic.setImage(image);

            InputStream streamLogo = null;
            try {
                streamLogo = new FileInputStream("src/images/" + player.getClub().getLogoId());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Image imageLogo = new Image(streamLogo);
            logoID.setImage(imageLogo);

            InformationPopup.setVisible(true);
        });
        PlayerInformation.setVisible(true);
    }
    @Override
    public void makeCaptain(Group btn) {
        MakeCaptain.setOnAction(event -> {
            for (int i = 0; i < PLAYERS_NUMBER; i++) {
                PlayerCardManageController playerCard = playerControllers.get(i);
                playerCard.getCaptainSign().setVisible(false);
            }
            if(index){
                btn.setVisible(true);
                index =true;
            }
            else {
                btn.setVisible(false);
                index =false;
            }
        });
    }
    @Override
    public void Changebtn() {
            for (int i = 0; i < PLAYERS_NUMBER; i++) {
                PlayerCardManageController playerCard = playerControllers.get(i);
                playerCard.getPlayerInform().setStyle("-fx-background-color: #1b1b1b");
            }
    }
}
