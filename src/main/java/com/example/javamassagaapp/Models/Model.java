package com.example.javamassagaapp.Models;


import com.example.javamassagaapp.Views.ViewFactory;
import com.example.javamassagaapp.dao.UserDAO;
import com.example.javamassagaapp.dao.VisitDAO;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;


import javax.swing.text.View;
import java.time.LocalDate;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    private User currentUser;
    private boolean loginSuccessFlag;
    public final UserDAO userDAO;
    public final VisitDAO visitDAO;


    private Model() {
        this.viewFactory = new ViewFactory();
        this.currentUser = null;
        this.visitDAO = new VisitDAO(new DatabaseDriver().getConnection());
        this.userDAO = new UserDAO(new DatabaseDriver().getConnection());
    }

    /**
     * Return singleton instance of the class "MODEL"
     * @return
     */

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }


    /**
     * Get ViewFactory instance
     * @return ViewFactory instance
     */

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    /**
     *
     * @return loginSuccessFlag
     */

    public boolean getLoginSuccessFlag() {
        return loginSuccessFlag;
    }

    /**
     * set login success flag
     * @param flag
     */

    public void setLoginSuccessFlag(boolean flag){
        this.loginSuccessFlag = flag;
    }

    /**
     * Create new user in DB
     *
     * @param userName
     * @param password
     * */

    public void createUser(String userName, String password)
    {
        userDAO.createUser(userName, password, LocalDate.now());
    }

    public void checkCredentials(String userName, String password){
        User user = userDAO.findUserByCredentials(userName, password);

        if(user != null)
        {
            this.loginSuccessFlag = true;
            this.currentUser = user;
        }
    }

    /**
     * get current user name
     *
     * @return userName
     */

    public String getLoggedUserName()
    {
        return currentUser != null ? currentUser.userNameProperty(): null;
    }


    /**
     * Get current user id
     *
     * @return id - user id
     */

    public int getLoggedUserId()
    {
        return currentUser != null ? currentUser.getId() : null;
    }

    /**
     * Check if user exit in system
     * @param userName
     * @return true if user exist
     */

    public boolean isUserExist(String userName)
    {
        return userDAO.isUserExist(userName);
    }

    public boolean hasRegisteredUsers()
    {
        return userDAO.countUsers() > 0;
    }

    public void createVisit(String firstName,String lastName,String date, String time,int status,int price){
        System.out.println("createVisit Model");
        visitDAO.create(firstName,lastName,date,time,status,price);
    }
    public void findVisit(Visit visit){

    }

    public ObservableList<Visit> getVisits(){
        return visitDAO.findAll();
    }
    public void updateVisit(Visit visit){
        visitDAO.update(visit);
    }
    public void deleteVisit(int id) {

    }
}

