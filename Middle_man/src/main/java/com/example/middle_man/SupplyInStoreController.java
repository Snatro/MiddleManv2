package com.example.middle_man;

import dataStorage.Database;
import entities.EntitiesDataManipulation;
import entities.Store;
import entities.SuppliesInStore;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SupplyInStoreController implements EntitiesDataManipulation {
    @FXML
    private TableView<SuppliesInStore> suppliesTableView;

    @FXML
    private TableColumn<SuppliesInStore,String> tableColumnSupplyName;
    @FXML
    private TableColumn<SuppliesInStore,String> tableColumnSupplyCategory;
    @FXML
    private TableColumn<SuppliesInStore,String> tableColumnSupplyAmount;
    @FXML
    private TableColumn<SuppliesInStore,String> tableColumnSupplyPrice;
    @FXML
    private Label storeName;
    Set<SuppliesInStore> supplies = new HashSet<>();
    Store chosenStore;
    Connection connection;
    @FXML
    private void initialize(){

        chosenStore = StoreListController.getStore();
        storeName.setText(chosenStore.getName());

        try{
            connection = Database.connectToDatabase();
           supplies = Database.getSuppliesByStoreId(connection,chosenStore.getId());
           tableColumnSupplyName.setCellValueFactory(cellData -> {
               return new SimpleStringProperty(cellData.getValue().getSupplies().getName());
           });
            tableColumnSupplyCategory.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(cellData.getValue().getSupplies().getCategory().name());
            });
            tableColumnSupplyAmount.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(String.valueOf(cellData.getValue().getAmount()));
            });
            tableColumnSupplyPrice.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(cellData.getValue().getPrice().toString());
            });
            ObservableList<SuppliesInStore> observableList = FXCollections.observableList(new ArrayList<>(supplies));
            suppliesTableView.setItems(observableList);

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    protected void onHelloButtonClick(){

    }

    @Override
    public void deleteEntityButton() {
        SuppliesInStore deleteSupply = suppliesTableView.getSelectionModel().getSelectedItem();
        try {
            Database.deleteSupplyFromStore(deleteSupply.getSupplies().getId(), chosenStore.getId(), Database.connectToDatabase());
            supplies = Database.getSuppliesByStoreId(connection,chosenStore.getId());
            ObservableList<SuppliesInStore> observableList = FXCollections.observableList(new ArrayList<>(supplies));
            suppliesTableView.setItems(observableList);

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void editEntityButton() {

    }
}
