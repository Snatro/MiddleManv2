package com.example.middle_man;

import dataStorage.Database;
import entities.Category;
import entities.EntitiesDataManipulation;
import entities.Supply;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplyController implements EntitiesDataManipulation {
    @FXML
    private TableView<Supply> supplyTableView;
    @FXML
    private TableColumn<Supply,String> tableColumnSupplyId;
    @FXML
    private TableColumn<Supply,String> tableColumnSupplyName;
    @FXML
    private TableColumn<Supply,String> tableColumnSupplyCategoryName;

    public List<Supply> supplies = new ArrayList<>();

    private static Supply chosenSupply;

    public static Supply getChosenSupply(){return chosenSupply;}
    Connection connection;
    @FXML
    private void initialize(){
        try{

            connection = Database.connectToDatabase();
            supplies = Database.getListOfSupplies(connection);

            tableColumnSupplyId.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(String.valueOf(cellData.getValue().getId()));
            });
            tableColumnSupplyName.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(cellData.getValue().getName());
            });
            tableColumnSupplyCategoryName.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(cellData.getValue().getCategory().name());
            });

            ObservableList<Supply> supplyObservableList = FXCollections.observableList(supplies);
            supplyTableView.setItems(supplyObservableList);

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void deleteEntityButton() {
        Supply supply = supplyTableView.getSelectionModel().getSelectedItem();
        try {
            Database.deleteSupply(supply);
            supplies = Database.getListOfSupplies(connection);
            ObservableList<Supply> supplyObservableList = FXCollections.observableList(supplies);
            supplyTableView.setItems(supplyObservableList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void editEntityButton() {

    }
}
