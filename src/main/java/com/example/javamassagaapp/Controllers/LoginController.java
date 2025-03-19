package com.example.javamassagaapp.Controllers;

import com.example.javamassagaapp.Models.Model;
import com.example.javamassagaapp.Utilities.AlertUtility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    public TextField user_name_field;
    @FXML
    public PasswordField password_field;
    @FXML
    public Hyperlink register_link;
    @FXML
    public Button login_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        register_link.setOnAction(actionEvent -> onRegister());
        login_btn.setOnAction(actionEvent -> onLogin());
    }

    /*
     * Handle login action
     */

    public void onLogin(){
        Stage stage = (Stage) register_link.getScene().getWindow();
//        Model.getInstance().getViewFactory().showMainWindow();

        //Check cred
        Model.getInstance().checkCredentials(user_name_field.getText(), password_field.getText());

        //If login succes, open dashboard

        if (Model.getInstance().getLoginSuccessFlag())
        {
            Model.getInstance().getViewFactory().showMainWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
        }
        else{
            user_name_field.setText("");
            password_field.setText("");
            AlertUtility.displayError("Neteisingi prisijungimo duomenys");
        }

    }

    /*
    Handle register action
     */

    public void onRegister(){
        Stage stage = (Stage) register_link.getScene().getWindow();

        Model.getInstance().getViewFactory().showRegisterWindow();

        Model.getInstance().getViewFactory().closeStage(stage);
    }

}
