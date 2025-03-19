package com.example.javamassagaapp.Controllers;

import com.example.javamassagaapp.Models.Model;
import com.example.javamassagaapp.Models.Visit;
import com.example.javamassagaapp.Utilities.AlertUtility;
import com.example.javamassagaapp.Utilities.DialogUtility;
import com.example.javamassagaapp.Views.MenuItems;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Filter;

public class VisitController implements Initializable {
    public Button add_visit_btn;
    public TableView visit_table;
    public TableColumn col_Id;
    public TableColumn col_firstName;
    public TableColumn col_lastName;
    public TableColumn col_data;
    public TableColumn col_time;
    public TableColumn col_status;
    public TableColumn col_price;
    public MenuItem remove_visit;
    public TextField filterFirstName;
    public TextField filterLastname;
    public TextField filterDate;
    public Button filterButton;
    public Button update_button;

    private FilteredList<Visit> filteredList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        add_visit_btn.setOnAction(e -> onAddVisit());
        initTableCollums();
        update_button.setOnAction(e -> loadVisitData());
        loadVisitData();
        remove_visit.setOnAction(e -> onRemoveVisit());
        setRowFactoryForVisitTable();

        filteredList = new FilteredList<>(Model.getInstance().getVisits());
        System.out.println(Model.getInstance().getVisits());
        visit_table.setItems(filteredList);

    }

    public void onAddVisit(){
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.ADD_VISIT);

    }

    private void initTableCollums(){
        col_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_firstName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_data.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        col_status.setCellFactory(column -> new TableCell<Visit, Integer>() {
            @Override
            protected void updateItem(Integer status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                } else {
                    setText(status == 1 ? "Taip" : "Ne");
                }
            }
        });

        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
    /**
     * Load visits data into table
      */

    public void loadVisitData(){
        ObservableList<Visit> visits = Model.getInstance().getVisits();
        filteredList = new FilteredList<>(visits, p -> true);
        visit_table.setItems(filteredList);
    }

    private void onRemoveVisit(){
        Visit selectedVisit = (Visit) visit_table.getSelectionModel().getSelectedItem();
        if (selectedVisit != null){
            AlertUtility.displayError("Pasirinkite vizitą");
        }

        boolean confirmed = AlertUtility.displayConfirmation("Ar tikrai norite pasalinti vizitą?");
        if (confirmed){
            Model.getInstance().deleteVisit(selectedVisit.getId());
            loadVisitData();
            AlertUtility.displayInformation("Vizitas sekmingai pasalintas");
        }


    }

    private void setRowFactoryForVisitTable(){
        visit_table.setRowFactory(tv -> {
            TableRow<Visit> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())){
                    Visit selectedVisit = (Visit) row.getItem();
                    editVisit(selectedVisit);
                }
            });
            return row;
        });
    }

    private void editVisit(Visit visit){
        Optional<Visit> result = DialogUtility.showEditVisitDialog(visit);
        result.ifPresent(updateVisit -> {
            Model.getInstance().updateVisit(updateVisit);
            loadVisitData();
        });
    }


}
