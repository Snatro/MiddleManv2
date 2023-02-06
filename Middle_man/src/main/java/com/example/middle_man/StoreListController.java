package com.example.middle_man;

import dataStorage.Database;
import entities.EntitiesDataManipulation;
import entities.Person;
import entities.Routing;
import entities.Store;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

public class StoreListController implements EntitiesDataManipulation, Routing {
    @FXML
    private TableView<Store> storeTableView;
    @FXML
    private TableColumn<Store, String> tableColumnId;
    @FXML
    private TableColumn<Store, String> tableColumnName;
    @FXML
    private TableColumn<Store, String> tableColumnAddress;
    @FXML
    private TableColumn<Store, String> tableColumnCity;
    @FXML
    private Button goToStoreButton;
    @FXML
    private Button editStoreButton;
    @FXML
    private Button deleteStoreButton;
    public List<Store> stores = new ArrayList<>();

    public static Store selectedStore;

    public static Store getStore(){
        return selectedStore;
    }
    @FXML
    protected void initialize(){
        if(HelloApplication.getCurrentPerson().getRole().getId() == 3){
            deleteStoreButton.setVisible(false);
            editStoreButton.setVisible(false);
        }
        System.out.println(HelloApplication.getCurrentPerson().getRole().getId());
        try{
            Connection connection = Database.connectToDatabase();
            this.stores = Database.getListOfStores(connection);
            stores.stream().forEach(s -> System.out.println(s.getManager().getName()));

            if(HelloApplication.getCurrentPerson().getRole().getId() == 2) {
               this.stores =  stores.stream().filter(store -> store.getManager().getId()
                == (HelloApplication.getCurrentPerson().getId()))
                        .collect(Collectors.toList());
            }
            tableColumnId.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(String.valueOf(cellData.getValue().getId()));
            });
            tableColumnName.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(cellData.getValue().getName());
            });
            tableColumnAddress.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(cellData.getValue().getAddress());
            });
            tableColumnCity.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(cellData.getValue().getCity());
            });

            ObservableList<Store> observableList = FXCollections.observableList(stores);
            storeTableView.setItems(observableList);


        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void selectedStoreButton(){
        selectedStore = storeTableView.getSelectionModel().getSelectedItem();
        routeTo("supplies-in-store");
    }
    @Override
    public void deleteEntityButton() {
        Store store = storeTableView.getSelectionModel().getSelectedItem();
        Database.deleteStore(store);
        try {
            this.stores = Database.getListOfStores(Database.connectToDatabase());
            ObservableList<Store> observableList = FXCollections.observableList(stores);
            storeTableView.setItems(observableList);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void editEntityButton() {

    }
}
