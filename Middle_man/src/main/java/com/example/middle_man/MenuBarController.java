package com.example.middle_man;

import entities.Routing;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MenuBarController implements Routing {

    @FXML
    private MenuBar menu;
    @FXML
    private Menu supply;
    @FXML
    private Menu store;
    @FXML
    private Menu person;
    @FXML
    private MenuItem addStoreMenuItem;
    @FXML
    private void initialize(){
        if(HelloApplication.getCurrentPerson().getRole().getId() == 3)
            menu.setVisible(false);
        if(HelloApplication.getCurrentPerson().getRole().getId() == 2){
            supply.setVisible(false);
            person.setVisible(false);
            addStoreMenuItem.setVisible(false);
        }
    }
    @FXML
    private void storeListButton(){
        routeTo("store-list");
    }
    @FXML
    private void addStoreButton(){
        routeTo("new-store");
    }
    @FXML
    private void supplyListButton(){
        routeTo("supply-list");
    }
    @FXML
    private void addSupplyButton(){
        routeTo("new-supply");
    }
    @FXML
    private void categoryListButton(){
        routeTo("category-list");
    }
    @FXML
    private void addCategoryButton(){
        routeTo("new-category");
    }
    @FXML
    private void personListButton(){
        routeTo("person-list");
    }
    @FXML
    private void addPersonButton(){
        routeTo("new-person");
    }



}
