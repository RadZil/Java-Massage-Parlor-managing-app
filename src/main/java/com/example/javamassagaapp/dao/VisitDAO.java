package com.example.javamassagaapp.dao;

import com.example.javamassagaapp.Models.Model;
import com.example.javamassagaapp.Models.Visit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VisitDAO implements GenericDAO {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(VisitDAO.class);
    private Connection conn;

    private static final Logger logger = LoggerFactory.getLogger(VisitDAO.class.getName());

    public VisitDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Visit findById(int id) {
        List<Visit> visits = findAll();

        for (Visit visit : visits) {
            if (visit.getId() == id) {
                return visit;
            }
        }
        return null;
    }



    @Override
    public void create(String fname, String lastname, String date, String time, int status, int price){
        String sql = "INSERT INTO visit(name, lastName, date,time,status,price) VALUES(?, ?, ?, ?, ?, ?)";
        int userId = Model.getInstance().getLoggedUserId();
        System.out.println("DAO create0");
        try(PreparedStatement stmt = this.conn.prepareStatement(sql))
        {
            System.out.println("DAO create1");
            stmt.setString(1,fname);
            stmt.setString(2,lastname);
            stmt.setString(3,date);
            stmt.setString(4,time);
            stmt.setInt(5,status);
            stmt.setInt(6,price);
            System.out.println("DAO create2");
            stmt.executeUpdate();
            System.out.println("DAO create3");

        }catch (SQLException e) {
            logger.error("Error creating user:");
            e.printStackTrace();
        }

    }

    @Override
    public void update(Object entity) {
        if (!(entity instanceof Visit))
        {
            throw new IllegalArgumentException("Excepted Author object");
        }

        Visit visit = (Visit) entity;

        String sql = "UPDATE visit SET name = ?, lastName = ?, date = ?, time = ?, status = ?, price = ? WHERE id = ?";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
           stmt.setString(1, visit.getName());
           stmt.setString(2, visit.getLastName());
           stmt.setString(3, visit.getDate());
           stmt.setString(4, visit.getTime());
           stmt.setInt(5, visit.getStatus());
           stmt.setInt(6, visit.getPrice());
           stmt.setInt(7, visit.getId());

            int rowsUpdated = stmt.executeUpdate();

            if(rowsUpdated > 0){
                System.out.println("Visit updated: " + visit);
            }
            else {
                System.out.println("No author found with id: " + visit.getId());

            }
        }catch (SQLException e){
            logger.warn("Error updating author: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM visit WHERE id = ?";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setInt(1,id);
            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0){
                logger.info("Visit deleted: " + id);
            }else{
                logger.warn("No rows affected");
            }
        }catch (SQLException e){
            logger.warn("Error deleting visit: " + e.getMessage());
            e.printStackTrace();
        }


    }

    @Override
    public List all() {
        return List.of();
    }

    @Override
    public ObservableList findAll() {
        ObservableList<Visit> visits = FXCollections.observableArrayList();
        String sql = "SELECT id,name,lastName,date,time,status,price FROM visit";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                String date = resultSet.getString("date");
                String time = resultSet.getString("time");
                int status = resultSet.getInt("status");
                int price = resultSet.getInt("price");
                Visit visit = new Visit(id,firstName,lastName,date,time,status,price);
//                System.out.println("VISIT DAO FINDALL TIME:" + visit.getName() + " " + visit.getTime() + " Result set: " + resultSet.getString("time"));
                visits.add(visit);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        System.out.println("DAO findAll" + visits);
        return visits;
    }
}
