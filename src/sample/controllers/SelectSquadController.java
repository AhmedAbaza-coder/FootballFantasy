package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import sample.customCells.PurchaseCell;
import sample.database.AppDatabase;
import sample.models.Player;
import sample.models.User;

public class SelectSquadController implements Initializable, RemoveHandler {

    /*
        Components
     */

    @FXML
    private AnchorPane InformationPopup;

    @FXML
    private GridPane PlayersPane, GKPane;

    @FXML
    private JFXButton ExitPopUp, ConfirmBtn;

    @FXML
    private ImageView PlayerPic, logoID;

    @FXML
    private Text PlayerNum, FirstName, LastName, clubname, position, Nationality, PLD, Appearances, Goals, MoneyCounter, PlayerCounter;

    @FXML
    private JFXTextField Search, SquadNameText;

    @FXML
    private JFXComboBox<String> ViewSelection;

    @FXML
    private JFXListView<Player> PlayerList;


    /* Objects */
    List<Player> rawPlayers = Player.getRawPlayers();
    List<Player> allPlayers = rawPlayers;
    List<Player> selectedPlayers = new ArrayList<>();
    ObservableList<String> sortingOptions = FXCollections.observableArrayList("By Name", "By Club", "By Position", "By Price");

    //
    List<AnchorPane> playerCards = new ArrayList<>();
    List<PlayerCardController> playerControllers = new ArrayList<>();

    final int PLAYERS_NUMBER = 15;
    int goalKeeperIndex = 0;
    int defenderIndex = 2;
    int midfielderIndex = 7;
    int forwardIndex = 12;
    private User loggedInUser = User.getLoggedInUser();
    public static NewUserConfirmHandler handler;

    /*
        Logic
     */

    public SelectSquadController() {
        getPlayerCards();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ViewSelection.setValue("By Name");
        populatePlayground();
        SquadNameText.setText((loggedInUser.isNewUser()) ? "" : loggedInUser.getSquadName());
        PlayerCounter.setText(selectedPlayers.size() + "");
        populateListView(ViewSelection.getValue());
        handleListViewSelection();
        initViewSorting();
        initSearchView();
        updateMoneyCounter();

    }

    private void populatePlayground() {
        if (!User.getLoggedInUser().isNewUser()) {
            List<Player> userPlayers = User.getLoggedInUser().getSelectedPlayers();
            if (userPlayers != null) {
                userPlayers.forEach(p -> {
                    checkPosition(p, true);
                });
            }
        }
    }

    private void populateListView(String comparingValue) {
//        System.out.println(comparingValue);
        sortListView(comparingValue);
        ObservableList<Player> list = FXCollections.observableArrayList(allPlayers);
        PlayerList.setItems(list);
        PlayerList.setCellFactory(param -> new PurchaseCell());

    }

    /*
       Player Selection
    */

    private void updateMoneyCounter() {
        MoneyCounter.setText(loggedInUser.getMoney() + "");
    }

    private void handleListViewSelection() {
//        ConfirmBtn.setPrefWidth(84);
//        ConfirmBtn.setPrefHeight(37);
//        InformationPopup.setVisible(false);


        PlayerList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + PlayerList.getSelectionModel().getSelectedItem());
                Player p = PlayerList.getSelectionModel().getSelectedItem();
//                System.out.println("clicked on " + p.getPosition());
                if (PlayerList.getSelectionModel().getSelectedItem() != null) {


                    ButtonType result = ConfirmationDialog.showDialog();

                    if (result == ButtonType.OK) {
                        checkPosition(p, false);
                        rawPlayers.removeAll(selectedPlayers);
                        allPlayers = rawPlayers;
                        ObservableList<Player> list = FXCollections.observableArrayList(allPlayers);

                        PlayerList.setItems(list);
                        PlayerList.setCellFactory(param -> new PurchaseCell());
                        System.out.println(allPlayers.size());
                        Search.setText("");
                        PlayerCounter.setText(selectedPlayers.size() + "");

//                        System.out.println(selectedPlayers);
                    }
                }
            }
        });

    }

    public void checkPosition(Player player, boolean isOwned) {
        switch (player.getPosition()) {
            case Player.P_GK:
                goalKeeperIndex = setPlayer(player, goalKeeperIndex, 1, isOwned);
                break;
            case Player.P_DEF:
                defenderIndex = setPlayer(player, defenderIndex, 6, isOwned);
                break;
            case Player.P_MID:
                midfielderIndex = setPlayer(player, midfielderIndex, 11, isOwned);
                break;
            case Player.P_FW:
                forwardIndex = setPlayer(player, forwardIndex, 14, isOwned);
                break;
        }
    }

    public int setPlayer(Player player, int index, int selectionScope, boolean isOwned) {


        if (index > selectionScope) {
            System.out.println("BAD SCOPE");
            return index;

        }

        if (loggedInUser.getMoney() < player.getPrice() && !isOwned) {
            System.out.println("NO ENOUGH MONEY");
            return index;
        }

        //System.out.println("ON");

        playerControllers.get(index).setSelection(player, index);

        InputStream stream = null;
        try {
//            stream = new FileInputStream("src/images/" + player.getClub().getLogoId());
            stream = new FileInputStream("src/images/" + player.getClub().getShirtLogo());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(stream);
        PlayerPic.setImage(image);

        selectedPlayers.add(player);
        if (!isOwned)
            loggedInUser.cutPrice(player.getPrice());

        updateMoneyCounter();

        return ++index;
    }


    /*
        Sorting and Searching
     */
    private void initViewSorting() {

        ViewSelection.setItems(sortingOptions);
        ViewSelection.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                populateListView(newValue);
            }
        });

    }

    private void sortListView(String newValue) {
        Comparator<Player> comparator = null;
        switch (newValue) {
            case "By Name":
                comparator = Player.BY_NAME;
                break;
            case "By Club":
                comparator = Player.BY_CLUB;
                break;
            case "By Position":
                comparator = Player.BY_POSITION;
                break;
            case "By Price":
                comparator = Player.BY_PRICE;

        }
        allPlayers.sort(comparator);
    }

    private void initSearchView() {
        Search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                allPlayers = rawPlayers.stream().filter(player -> player.getSearchableName().toLowerCase().contains(newValue.toLowerCase())).collect(Collectors.toList());
                populateListView(ViewSelection.getValue());
            }
        });
    }


    private void getPlayerCards() {
        Thread thread = new Thread(() -> {

            Runnable updater = new Runnable() {

                @Override
                public void run() {
                    positionPlayerCards();
                }
            };

            try {
                for (int i = 0; i < PLAYERS_NUMBER; i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("../view/player_card.fxml"));
                    AnchorPane card = fxmlLoader.load();
                    card.setCache(true);
                    card.setCacheShape(true);
                    card.setCacheHint(CacheHint.SPEED);
                    playerCards.add(card);
                    PlayerCardController controller = fxmlLoader.getController();
                    controller.removeHandler = this;
                    playerControllers.add(controller);
                    if (i < 2) controller.setName("ADD GK");
                    else if (i >= 2 && i < 7) controller.setName("ADD DEF");
                    else if (i >= 7 && i < 12) controller.setName("ADD MID");
                    else controller.setName("ADD FW");
                }

                Platform.runLater(updater);


            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }

    private void positionPlayerCards() {
//        PlayersPane.setAlignment(Pos.CENTER);
        int x = 0, y = 0;

        for (int i = 0; i < 2; i++) {
            y = 0;
            AnchorPane card = playerCards.get(i);
            if (i == 0) x = 2;
            GKPane.add(card, x++, y);

        }
        for (int i = 2; i < PLAYERS_NUMBER; i++) {
            AnchorPane card = playerCards.get(i);
            if (i == 2) {
                y = 1;
                x = 0;
            }
            if (i == 7) {
                y = 2;
                x = 0;
            }
            if (i == 12) {
                y = 3;
                x = 1;
            }
            PlayersPane.add(card, x++, y);
        }
    }


    @FXML
    void confirmSquad(MouseEvent event) {
        System.out.println("CLICKED");

        if (selectedPlayers.size() != 15 || SquadNameText.getText().equals(""))
            return;


        ButtonType result2 = ConfirmationDialog.showDialog();
        if (result2 == ButtonType.OK) {
            if (loggedInUser.isNewUser()) {
                loggedInUser.setSquadName(SquadNameText.getText());
                handler.handleConfirm();
            }
            AppDatabase.getInstance().confirmUser(loggedInUser.getUsername(), selectedPlayers);
            AppDatabase.getInstance().confirmSquadName(loggedInUser.getUsername(), SquadNameText.getText(), loggedInUser.getMoney());
        }
    }

    @FXML
    void ExitPopUpClicked(MouseEvent event) {


    }


    /*
        Removing Player
     */
    @Override
    public void removePlayer(Player player, int index) {
        int removalIndex = -1;
        rawPlayers.add(player);
        selectedPlayers.remove(player);
        rawPlayers.sort(Player.BY_NAME);
        allPlayers = rawPlayers;
        populateListView(ViewSelection.getValue());
        int values[];
        switch (player.getPosition()) {
            case Player.P_GK:
                values = shiftPlayers(goalKeeperIndex, index);
                removalIndex = values[1];
                goalKeeperIndex = values[0];
                break;
            case Player.P_DEF:
                values = shiftPlayers(defenderIndex, index);
                removalIndex = values[1];
                defenderIndex = values[0];
                break;
            case Player.P_MID:
                values = shiftPlayers(midfielderIndex, index);
                removalIndex = values[1];
                midfielderIndex = values[0];
                break;
            case Player.P_FW:
                values = shiftPlayers(forwardIndex, index);
                removalIndex = values[1];
                forwardIndex = values[0];
                break;
        }
        PlayerCounter.setText(selectedPlayers.size() + "");
        playerControllers.get(removalIndex).handleRemoval();
        loggedInUser.setMoney(loggedInUser.getMoney() + player.getPrice());
        updateMoneyCounter();

    }

    private int[] shiftPlayers(int positionIndex, int shiftingIndex) {
        int removalIndex = positionIndex - 1;
        while (positionIndex - 1 > shiftingIndex) {
//            System.out.println("1 " + positionIndex + ", 2 " + shiftingIndex);

            Player current = playerControllers.get(shiftingIndex + 1).getPlayer();
            playerControllers.get(shiftingIndex).setSelection(current, shiftingIndex);
            shiftingIndex++;
        }
        positionIndex = shiftingIndex;
        return new int[]{positionIndex, removalIndex};
    }
}

