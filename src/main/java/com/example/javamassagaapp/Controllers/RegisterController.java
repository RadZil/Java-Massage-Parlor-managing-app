package com.example.javamassagaapp.Controllers;

import com.example.javamassagaapp.Models.Model;
import com.example.javamassagaapp.Utilities.AlertUtility;
import com.example.javamassagaapp.Utilities.UserUtility;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


    public class RegisterController implements Initializable {
        public TextField user_name_field;
        public PasswordField password_field;
        public PasswordField repeatPasswor_field;
        public Button register_btn;
        public Hyperlink register_link;

        @Override
        public void initialize(URL location, ResourceBundle resources)
        {
            register_btn.setOnAction(actionEvent -> onRegister());
            register_link.setOnAction(actionEvent -> OnLogin());
        }

        public void OnLogin()
        {
            Stage stage = (Stage) register_link.getScene().getWindow();

            Model.getInstance().getViewFactory().showLoginWindow();

            Model.getInstance().getViewFactory().closeStage(stage);
        }

        public void onRegister()
        {
            Stage stage = (Stage) register_btn.getScene().getWindow();

            //Check if password match

            if(Model.getInstance().isUserExist(user_name_field.getText()))
            {
                AlertUtility.displayError("Vartotojas tokiu vardu sitemoje jau registruotas");
                emptyFields();
            }
            else if(!UserUtility.doPasswordsMatch(password_field.getText(), repeatPasswor_field.getText()))
            {
                AlertUtility.displayError("Nesutampa slaptažodžiaia");
            }
            else {

                if (user_name_field.getText().isEmpty()  || password_field.getText().isEmpty())
                {
                    AlertUtility.displayError("Vardas arba slaptažodis yra tuščias");
                }
                else{
                    Model.getInstance().createUser(user_name_field.getText(), password_field.getText());

                    AlertUtility.displayInformation("Vartotojas sekmingai sukurtas. Prasome prisijungti");

                    Model.getInstance().getViewFactory().showLoginWindow();

                    Model.getInstance().getViewFactory().closeStage(stage);
                }

            }

        }

        /**
         * Empty from fields
         */

        public void emptyFields()
        {
            user_name_field.setText("");
            password_field.setText("");
            repeatPasswor_field.setText("");
        }
    }

