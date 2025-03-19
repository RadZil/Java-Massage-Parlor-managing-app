package com.example.javamassagaapp.Controllers;

import com.example.javamassagaapp.Models.Model;
import com.example.javamassagaapp.Utilities.AlertUtility;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateVisit implements Initializable {
    public TextField field_firstName;
    public TextField field_lastName;
    public DatePicker field_date;
    public CheckBox field_status;
    public TextField field_price;
    public Button create_visit_btn;
    public TextField time_field;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_visit_btn.setOnAction(event -> onVisit());
    }

    private void onVisit() {

        String firstName = field_firstName.getText();
        String lastName = field_lastName.getText();
        String date = field_date.getValue().toString();
        int status = 0;
        if (field_status.isSelected()) {
            status = 1;
        }
        int price = Integer.parseInt(field_price.getText());
        String time = time_field.getText();
        System.out.printf("%d %d %s %s\n", status, price, time,date);
        Model.getInstance().createVisit(firstName,lastName,date,time,status,price);

        AlertUtility.displayInformation("Autorius sekmingai sukurtas");

        emptyFields();
//        Model.getInstance().getViewFactory().closeStage(Model.getInstance().getViewFactory().getCreateVisitView());
    }
    private void emptyFields()
    {
        field_firstName.setText("");
        field_lastName.setText("");
        field_date.setValue(null);
        field_status.setSelected(false);
        field_price.setText("");
        time_field.setText("");
    }


}
