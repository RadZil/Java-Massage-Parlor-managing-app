package com.example.javamassagaapp.Utilities;


import com.example.javamassagaapp.Models.Visit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.PrimitiveIterator;

public class DialogUtility {
    /**
     * Display dialog for editing visit information
     * @param visit - the Author object for editing
     */

    public static Optional<Visit> showEditVisitDialog(Visit visit){
        Dialog<Visit> dialog = new Dialog();
        dialog.setTitle("Redaguoti autorių");
        dialog.setHeaderText("Redaguokite pasirinkto vizito duomenis");

        ButtonType saveButtonType = new ButtonType("Issaugoti", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField firstNameField = new TextField(visit.getName());
        TextField lastNameField = new TextField(visit.getLastName());
        TextField dateField = new TextField(visit.getDate());
        TextField timeField = new TextField(visit.getTime());
        TextField statusField = new TextField(visit.getStatus()+"");
        TextField priceField = new TextField(visit.getPrice()+"");

        grid.add(new Label("Vardas:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Pavarde:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("Data:"), 0, 2);
        grid.add(dateField, 1, 2);
        grid.add(new Label("Laikas:"), 0, 3);
        grid.add(timeField, 1, 3);
        grid.add(new Label("Statusas:"), 0, 4);
        grid.add(statusField, 1, 4);
        grid.add(new Label("Kaina:"), 0, 5);
        grid.add(priceField, 1, 5);


        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == saveButtonType){
                visit.setName(firstNameField.getText().trim());
                visit.setLastName(lastNameField.getText().trim());
                visit.setDate(dateField.getText().trim());
                visit.setTime(timeField.getText().trim());

                try{
                    visit.setStatus(Integer.parseInt(statusField.getText().trim()));
                    visit.setPrice(Integer.parseInt(priceField.getText().trim()));
                }catch (Exception e)
                {
                    AlertUtility.displayError("Reides įvestos į ten kur turi būti numeriai");
                }


            }

            return visit;
        });

        return dialog.showAndWait();
    }


}
