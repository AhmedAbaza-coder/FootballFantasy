package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToolbar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.database.AppDatabase;
import sample.models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginRegisterController implements Initializable {


    @FXML
    private AnchorPane RegisterScene;

    @FXML
    private StackPane RegisterForm, LoginForm;

    @FXML
    private TextField FirstNameRegister, LastNameRegister, EmailRegister, EmailLogin;

    @FXML
    private PasswordField PasswordRegister;

    @FXML
    private JFXPasswordField passwordLogin;

    @FXML
    private JFXButton RegisterBtn, LoginBtn;

    @FXML
    private JFXRadioButton RadioMale, RadioFemale;

    @FXML
    private ToggleGroup gender;

    @FXML
    private Label Login_title, email_label, password_label, EmptyInput, InvaildEmail, EmptyInputReg, InvaildEmailReg;

    @FXML
    private Hyperlink CreateAccount, Signin;

    public void CreateAccountClicked() {
        InvaildEmailReg.setVisible(false);
        EmptyInputReg.setVisible(false);
        InvaildEmail.setVisible(false);
        EmptyInput.setVisible(false);
        LoginForm.setVisible(false);
        RegisterForm.setVisible(true);
    }

    public void SigninClicked() {
        InvaildEmailReg.setVisible(false);
        EmptyInputReg.setVisible(false);
        InvaildEmail.setVisible(false);
        EmptyInput.setVisible(false);
        RegisterForm.setVisible(false);
        LoginForm.setVisible(true);
    }

    public void RegisterAccount() {
        if (isTextFieldEmpty(FirstNameRegister) || isTextFieldEmpty(LastNameRegister) || isTextFieldEmpty(EmailRegister)
                || isTextFieldEmpty(PasswordRegister)) {
            System.out.println("EMPTY INPUT");
            InvaildEmailReg.setVisible(false);
            EmptyInputReg.setVisible(true);

        } else {
            String firstName = FirstNameRegister.getText();
            String lastName = LastNameRegister.getText();
            String email = EmailRegister.getText();
            String password = PasswordRegister.getText();
            RadioButton selectedGenderRadio = (RadioMale.isSelected()) ? RadioMale : RadioFemale;

            if (!User.validateUserRegistration(email, password)) {
                System.out.println("INVALID EMAIL OR PASSWORD");
                EmptyInputReg.setVisible(false);
                InvaildEmailReg.setVisible(true);
            } else {
                AppDatabase.getInstance().insertUser(new User(firstName, lastName, email, password, selectedGenderRadio.getText(), "null"));
                System.out.println("REGISTERED");
                RegisterForm.setVisible(false);
                LoginForm.setVisible(true);
                User.setCurrentUsers(AppDatabase.getInstance().getAllUsers());
            }

        }


    }

    User user;

    @FXML
    public void Login(MouseEvent event) {

        if (isTextFieldEmpty(EmailLogin) && isTextFieldEmpty(passwordLogin)) {
            System.out.println("EMPTY INPUT");
            InvaildEmail.setVisible(false);
            EmptyInput.setVisible(true);
        } else {
            if (User.validateUserLogin(EmailLogin.getText(), passwordLogin.getText())) {
                System.out.println("ENTERED");
                System.out.println(User.getLoggedInUser());
                ChangeScene(event);
            } else {
                EmptyInput.setVisible(false);
                InvaildEmail.setVisible(true);
                System.out.println("WRONG EMAIL OR PASSWORD");
            }
        }
    }

    private void ChangeScene(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setController(new MainController(user));
            fxmlLoader.setLocation(getClass().getResource("..//view/main.fxml"));
            Parent rootRegister = fxmlLoader.load();
            rootRegister.getStylesheets().add("style.css");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            JFXToolbar titlebar = FXMLLoader.load(getClass().getResource("../view/stage_titlebar.fxml"));
            BorderPane borderPane = new BorderPane();
            borderPane.setTop(titlebar);
            borderPane.setCenter(rootRegister);

            stage.setTitle("Fantasy");
            stage.setScene(new Scene(borderPane));
//            stage.initStyle(StageStyle.UNDECORATED);
//            LoginBtn.getScene().getWindow().hide();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isTextFieldEmpty(TextField textField) {
        return textField.getText().isEmpty();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InvaildEmailReg.setVisible(false);
        EmptyInputReg.setVisible(false);
        InvaildEmail.setVisible(false);
        EmptyInput.setVisible(false);
        RegisterScene.getStyleClass().add("RegisterScene");
        RegisterForm.setVisible(false);


    }
}