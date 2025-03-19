package com.example.javamassagaapp.Controllers;

import com.example.javamassagaapp.Models.Model;
import com.example.javamassagaapp.Models.Visit;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class CalendarController implements Initializable {
    public DatePicker datePicker_from;
    public GridPane calendar_Grid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createCalendarLabels();
        datePicker_from.setValue(LocalDate.now());
        datePicker_from.valueProperty().addListener((observable, oldValue, newValue) -> createCalendarLabels(newValue));

    }

    ///  Creates labels with client information in calender format for the current month or specified month
    private void createCalendarLabels(LocalDate... date) {
        calendar_Grid.getChildren().clear();
        LocalDate localDate;

        if (date == null || date.length == 0) {
            localDate = LocalDate.now();
        } else {
            localDate = date[0];
        }

        System.out.println("Displaying calendar for: " + localDate);

        YearMonth yearMonth = YearMonth.of(localDate.getYear(), localDate.getMonth());
        int daysInMonth = yearMonth.lengthOfMonth();

        ObservableList<Visit> visits = Model.getInstance().getVisits();

        /// Filtering visit list for given arguments

        List<Visit> filteredVisits = visits.stream()
                .filter(visit -> {
                    try {
                        LocalDate visitDate = LocalDate.parse(visit.getDate());
                        return visitDate.getMonth() == localDate.getMonth() && visitDate.getYear() == localDate.getYear();
                    } catch (DateTimeParseException e) {
                        System.err.println("Invalid date format in visit: " + visit.getDate());
                        return false;
                    }
                })
                .toList();

        int startingDay = localDate.getDayOfMonth();

        for (int i = startingDay; i <= daysInMonth; i++) {
            VBox dayBox = new VBox();
            dayBox.setPrefSize(100, 100);
            dayBox.setStyle("-fx-border-color: lightgray; -fx-alignment: top-left; -fx-padding: 5;");

            Label dayLabel = new Label(String.valueOf(i));
            dayLabel.setStyle("-fx-font-weight: bold;");

            dayBox.getChildren().add(dayLabel);

            int day = i;
            List<Visit> dayVisits = filteredVisits.stream()
                    .filter(visit -> {
                        try {
                            LocalDate visitDate = LocalDate.parse(visit.getDate());
                            return visitDate.getDayOfMonth() == day;
                        } catch (DateTimeParseException e) {
                            return false;
                        }
                    })
                    .toList();

            for (Visit visit : dayVisits) {
                String visitInfo = String.format("%s %s\n%s",
                        visit.getName(),
                        visit.getLastName(),
                        visit.getTime());
                System.out.println(visit.getTime());
                Label visitLabel = new Label(visitInfo);
                visitLabel.setStyle("-fx-font-size: 10; -fx-text-fill: darkblue;");
                dayBox.getChildren().add(visitLabel);
            }

            int row = (i - startingDay) / 7;
            int col = (i - startingDay) % 7;

            calendar_Grid.add(dayBox, col, row);
        }
    }



}
