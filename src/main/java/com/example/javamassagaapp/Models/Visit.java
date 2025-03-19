package com.example.javamassagaapp.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Visit {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty lastName;
    private StringProperty date;
    private StringProperty time;
    private IntegerProperty status;
    private IntegerProperty price;

    public Visit(int id, String name, String lastName, String date , String time, int status, int price) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.lastName = new SimpleStringProperty(lastName);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.status = new SimpleIntegerProperty(status);
        this.price = new SimpleIntegerProperty(price);

    }

    @Override
    public String toString() {
        return "[" + id.get() + " " + name.get() + " " + date.get() + " " + time.get() + "]";
    }

    public int getId() {
        return id.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getName() {
        return name.get();
    }

    public int getStatus() {
        return status.get();
    }

    public int getPrice() {
        return price.get();
    }

    public String getTime() {
        return time.get();
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public void setStatus(int status) {
        this.status.set(status);
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
