package com.example.javamassagaapp.Controllers;

import com.example.javamassagaapp.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.javamassagaapp.Views.MenuItems.VISITS;

public class RouteController implements Initializable {

    public BorderPane parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().addListener((observable, oldValue, newVal)->{
            switch (newVal){
                case VISITS:
                    parent.setBottom(Model.getInstance().getViewFactory().getVisitView());
                    break;
                case ADD_VISIT:
                    System.out.println("Add visit");
                    parent.setBottom(Model.getInstance().getViewFactory().getCreateVisitView());
                    break;
                case CALENDER:
                    System.out.println("Calender visit");
                    parent.setBottom(Model.getInstance().getViewFactory().getCalenderView());
                    break;
                case INCOME:
                    System.out.println("Income visit");
                    parent.setBottom(Model.getInstance().getViewFactory().getCreateIncomeView());
                    break;

                default:
                    parent.setBottom(Model.getInstance().getViewFactory().getDashboardView());
                    break;
            }
        });


    }
}
