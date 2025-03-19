package com.example.javamassagaapp.Views;

import com.example.javamassagaapp.Controllers.RouteController;
import com.example.javamassagaapp.Models.Visit;
import com.example.javamassagaapp.Views.MenuItems;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {

    private final ObjectProperty<MenuItems> userSelectedMenuItem;
    private AnchorPane dashboard;
    private AnchorPane visitsView;
    private AnchorPane createVisitView;
    private AnchorPane bookView;
    private AnchorPane addBookView;
    private AnchorPane calenderView;

    public ViewFactory(){
        this.userSelectedMenuItem = new SimpleObjectProperty<>();
    }

    /*
     * Getter for user selected menu item
     * @return the Object property
     */

    public ObjectProperty<MenuItems> getUserSelectedMenuItem()
    {
        return userSelectedMenuItem;
    }

    /*
     * Show dashboard
     */

    public AnchorPane getDashboardView()
    {
        if (dashboard == null)
        {
            try{
                dashboard = new FXMLLoader(getClass().getResource("/Fxml/Dashboard.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return dashboard;
    }

    /*
     * Show login window
     */

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }

    /*
     * Show register window
     */

    public void showRegisterWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Register.fxml"));
        createStage(loader);
    }

    /*
     * Show main window
     */

    public void showMainWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Main.fxml"));
        RouteController controller = new RouteController();
        loader.setController(controller);
        createStage(loader);
    }

    /**
     * Load and return authors view
     *
     * @return authorView
     */

    public AnchorPane getVisitView()
    {
        if(visitsView == null)
        {
            try
            {
                visitsView = new FXMLLoader(getClass().getResource("/Fxml/Visits.fxml")).load();
            }catch (Exception e)
            {
                System.out.println(e);
            }
        }

        return visitsView;
    }

    public AnchorPane getCreateVisitView()
    {
        if(createVisitView == null)
        {
            try
            {
                createVisitView = new FXMLLoader(getClass().getResource("/Fxml/CreateVisit.fxml")).load();
            }catch (Exception e)
            {
                System.out.println(e);
            }
        }

        return createVisitView;
    }

    public AnchorPane getCreateIncomeView()
    {
        if(createVisitView == null)
        {
            try
            {
                createVisitView = new FXMLLoader(getClass().getResource("/Fxml/Income.fxml")).load();
            }catch (Exception e)
            {
                System.out.println(e);
            }
        }

        return createVisitView;
    }

    public Node getCalenderView() {
        if(calenderView == null)
        {
            try
            {
                calenderView  = new FXMLLoader(getClass().getResource("/Fxml/Calendar.fxml")).load();
            }catch (Exception e)
            {
                System.out.println(e);
            }
        }

        return calenderView ;
    }

    /**
     * Create and display new stage.
     * @param loader the FXML loader instance. Load fxml file and create scene
     */

    public void createStage(FXMLLoader loader)
    {
        Scene scene = null;
        System.out.println(loader);
        try{
            scene = new Scene(loader.load());
        }catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Java MASSAGAApp");
        stage.show();
    }

    /*
     * Close provided stage
     * @param stage to close
     */

    public void closeStage(Stage stage)
    {
        stage.close();
    }



}
