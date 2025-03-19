package com.example.javamassagaapp.Controllers;

import com.example.javamassagaapp.Models.Model;
import com.example.javamassagaapp.Models.Visit;
import com.example.javamassagaapp.Utilities.DialogUtility;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

public class IncomeController implements Initializable {
    public Text text_suma;
    public TableView income_table;
    public TableColumn col_name;
    public TableColumn col_lastName;
    public TableColumn<Visit, String> col_date;
    public TableColumn col_price;
    public DatePicker datePicker_from;
    public DatePicker datePicker_to;


    private FilteredList<Visit> filteredList;

    // Sorting patern based on date

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTableCollums();
        loadIncomeData();
        setRowFactoryForVisitTable();

        ///  Removing visits which status is 0
        ObservableList<Visit> visits = Model.getInstance().getVisits();
        Iterator<Visit> iterator = visits.iterator();
        while (iterator.hasNext()) {
            Visit visit = iterator.next();
            if (visit.getStatus() == 0) {
                iterator.remove(); // This is safe
            }
        }

        filteredList = new FilteredList<>(visits);



        /// Custom sorting due to date being a string
        col_date.setComparator((date1, date2) -> {
            try {
                LocalDate d1 = LocalDate.parse(date1, formatter);
                LocalDate d2 = LocalDate.parse(date2, formatter);
                return d1.compareTo(d2);
            } catch (Exception e) {
                e.printStackTrace();
                return 0; // Treat as equal in case of an error
            }
        });

        SortedList<Visit> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(income_table.comparatorProperty());

        income_table.setItems(sortedList);

        col_date.setSortType(TableColumn.SortType.DESCENDING);
        income_table.getSortOrder().add(col_date);

        datePicker_from.valueProperty().addListener((observable, oldValue, newValue) -> {
            applyDateFilter();
            updateSumText();
        });

        datePicker_to.valueProperty().addListener((observable, oldValue, newValue) -> {
            applyDateFilter();
            updateSumText();
        });

        // Trigger sorting
        income_table.sort();
        updateSumText();
    }



    private void initTableCollums(){

        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    public void loadIncomeData(){
        ObservableList<Visit> visits = Model.getInstance().getVisits();

        /// Removing unpayed visits from the visits List
        Iterator<Visit> iterator = visits.iterator();
        while (iterator.hasNext()) {
            Visit visit = iterator.next();
            if (visit.getStatus() == 0) {
                iterator.remove(); // This is safe
            }
        }

        filteredList = new FilteredList<>(visits, p -> true);
        income_table.setItems(filteredList);
        income_table.sort();
        updateSumText();
    }

    private void setRowFactoryForVisitTable(){
        income_table.setRowFactory(tv -> {
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
            loadIncomeData();
        });
    }

    private void applyDateFilter() {
        LocalDate fromDate = datePicker_from.getValue();
        LocalDate toDate = datePicker_to.getValue();

        filteredList.setPredicate(visit -> {
            if (fromDate == null && toDate == null) {
                return true;
            }

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate visitDate = LocalDate.parse(visit.getDate(), formatter);

                if (fromDate != null && visitDate.isBefore(fromDate)) {
                    return false;
                }
                if (toDate != null && visitDate.isAfter(toDate)) {
                    return false;
                }

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return true;
            }
        });
        updateSumText();
    }

    private void updateSumText() {
        double totalSum = 0.0;

        for (Visit visit : filteredList) {
            totalSum += visit.getPrice();
        }

        String formattedSum = String.format("%.2f", totalSum);

        // Update the text field
        text_suma.setText("SUMA: " + formattedSum);
    }

}
