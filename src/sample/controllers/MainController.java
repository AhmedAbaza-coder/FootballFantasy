package sample.controllers;

import com.jfoenix.controls.*;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import sample.customCells.PurchaseCell;
import sample.database.AppDatabase;
import sample.models.Player;
import sample.models.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController extends JFXListView<Player> implements Initializable, NewUserConfirmHandler {


    @FXML
    private BorderPane SelectSquadPane, LeaguePane ,AboutPane,HomePane;
    /*
        Components
     */
    @FXML
    private BorderPane BorderPane;

    @FXML
    private AnchorPane WelcomePage;

    @FXML
    private JFXToolbar WindowBorder;

    @FXML
    private JFXButton homeBtn;

    @FXML
    private JFXButton SquadSelectionBtn;

    @FXML
    private JFXButton AboutBtn;

    @FXML
    private AnchorPane YardBackground;

    @FXML
    private JFXButton ConfirmBtn;

    @FXML
    private ImageView YardImg;

    @FXML
    private JFXComboBox<String> ViewSelection;

    @FXML
    private JFXTextField Search;

    @FXML
    private Text clubname;

    @FXML
    private Text position;

    @FXML
    private AnchorPane yard;

    @FXML
    private Text Nationality;

    @FXML
    private HBox CloseApp;

    @FXML
    private Text PLD;

    @FXML
    private Text PlayerCounter;

    @FXML
    private Text MoneyCounter;

    @FXML
    private Text Appearances;

    @FXML
    private Text Goals;

    @FXML
    private Text PlayerNum;

    @FXML
    private Text FirstName;

    @FXML
    private Text LastName;

    /*

     */
    @FXML
    private JFXTabPane MainTabPane;

    @FXML
    private Tab HomeTab;

    @FXML
    private Tab SquadSelectionTab;

    @FXML
    private Tab ManageTab;

    @FXML
    private Tab LeagueTab;

    @FXML
    private Tab AboutTab;

    @FXML
    private AnchorPane InformationPopup;

    @FXML
    private BorderPane ManageTeamPane;




    @FXML
    private JFXListView<Player> PlayerList = new JFXListView<>();

    /*
        Objects
     */


    private User currentUser;


    /*
        Logic
     */

    public MainController() { }

    public MainController(User user) { currentUser = user; }


    @FXML
    void ExitPopUpClicked(MouseEvent event) {
        InformationPopup.setVisible(false);
    }



    private AnchorPane LoadPages(String Page) throws IOException {

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("../view/" + Page + ".fxml"));
        AnchorPane root = Loader.load();

        return root;
    }
    ObservableList<Tab> tabs;
    ObservableList<Tab> oldUserTabs;
    Tab newUserTab;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SelectSquadController.handler = this;
        tabs = MainTabPane.getTabs();
        oldUserTabs = FXCollections.observableArrayList(tabs);
        newUserTab = tabs.get(2);


        initTabPane();
        try {

            LeaguePane.setCenter(LoadPages("league"));
            ManageTeamPane.setCenter(LoadPages("Manage"));
            AboutPane.setCenter(LoadPages("About"));
            HomePane.setCenter(LoadPages("Home"));
            SelectSquadPane.setCenter(LoadPages("select_squad"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void initTabPane() {

        System.out.println(User.getLoggedInUser().isNewUser());
        if (User.getLoggedInUser().isNewUser()){
            tabs.removeAll(tabs);
            tabs.add(newUserTab);
        }else {
            tabs.removeAll(tabs);
            tabs.addAll(oldUserTabs);
        }
    }

    @Override
    public void handleConfirm() {
        initTabPane();
    }


}
