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
    private BorderPane SelectSquadPane, LeaguePane;
    /*
        Components
     */
    @FXML
    private javafx.scene.layout.BorderPane BorderPane;

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

    private double tabWidth = 100;
    private double tabHeight = 240.0;
    public static int lastSelectedTabIndex = 0;

    private void configureTabPane() {
        MainTabPane.setTabMinWidth(tabWidth);
        MainTabPane.setTabMaxWidth(tabWidth);
        MainTabPane.setTabMinHeight(tabHeight);
        MainTabPane.setTabMaxHeight(tabHeight);
        MainTabPane.setRotateGraphic(true);

        EventHandler<Event> replaceBackgroundColorHandler = event -> {
            lastSelectedTabIndex = MainTabPane.getSelectionModel().getSelectedIndex();

            Tab currentTab = (Tab) event.getTarget();
            if (currentTab.isSelected()) {
                currentTab.setStyle("-fx-background-color: -fx-focus-color;");
            } else {
                currentTab.setStyle("-fx-background-color: #141313;");
            }
        };

        configureTab(HomeTab, "Home", "images/home.png",replaceBackgroundColorHandler);
        configureTab(SquadSelectionTab, "Squad", "images/home.png",replaceBackgroundColorHandler);
        configureTab(ManageTab, "Manage", "images/home.png",replaceBackgroundColorHandler);
        configureTab(LeagueTab, "League", "images/home.png",replaceBackgroundColorHandler);
        configureTab(AboutTab, "About", "images/home.png",replaceBackgroundColorHandler);
    }

    private void configureTab(Tab tab, String title, String iconPath,EventHandler<Event> onSelectionChangedEvent) {
        double imageWidth = 30.0;

        ImageView imageView = new ImageView(new Image(iconPath));
        imageView.setFitHeight(imageWidth);
        imageView.setFitWidth(imageWidth);
        StackPane stack_pane = new StackPane(imageView);
        stack_pane.setAlignment(Pos.CENTER);
        stack_pane.setPadding(new Insets(20, 20, 0, 0));


        Label label = new Label(title);
        label.setMaxWidth(tabWidth - 20);
        label.setPadding(new Insets(20, 0, 0, 0));
        label.setStyle("-fx-text-fill: white; -fx-font-size: 12pt; -fx-font-weight: bold;");
        label.setTextAlignment(TextAlignment.CENTER);

        BorderPane tabPane = new BorderPane();
        tabPane.setRotate(90.0);
        tabPane.setMaxWidth(tabWidth);
        tabPane.setPadding(new Insets(30,0,0,0));


        tabPane.setLeft(stack_pane);
        tabPane.setRight(label);

        /// 6.
        tab.setText("");
        tab.setGraphic(tabPane);

        tab.setOnSelectionChanged(onSelectionChangedEvent);



/// 8.
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
        configureTabPane();


        initTabPane();
        try {

            SelectSquadPane.setCenter(LoadPages("select_squad"));
            LeaguePane.setCenter(LoadPages("league"));
            ManageTeamPane.setCenter(LoadPages("Manage"));


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

   // @FXML
//    void logOut(ActionEvent event) {
//        User.logOutUser();
//
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            fxmlLoader.setLocation(getClass().getResource("../view/register.fxml"));
//            Parent rootRegister = fxmlLoader.load();
//            rootRegister.getStylesheets().add("style.css");
//            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
//            JFXToolbar titlebar = FXMLLoader.load(getClass().getResource("../view/stage_titlebar.fxml"));
//            BorderPane borderPane = new BorderPane();
//            borderPane.setTop(titlebar);
//            borderPane.setCenter(rootRegister);
//            stage.setTitle("Fantasy");
//            stage.setScene(new Scene(borderPane));
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
