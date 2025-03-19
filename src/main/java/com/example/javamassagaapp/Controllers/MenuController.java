package com.example.javamassagaapp.Controllers;

import com.example.javamassagaapp.Models.Model;
import com.example.javamassagaapp.Views.MenuItems;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    public Button visit_btn;
    public Button income_btn;
    public Button calender_btn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Add listeners for menu buttons
        addListeners();
    }

    private void addListeners() {
        visit_btn.setOnAction(event -> onVisits());
        income_btn.setOnAction(event -> onIncome());
        calender_btn.setOnAction(event -> onCalender());

    }

    public void onVisits(){
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.VISITS);
    }
    public void onIncome(){
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.INCOME);
    }
    public void onCalender(){
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.CALENDER);
    }
}
