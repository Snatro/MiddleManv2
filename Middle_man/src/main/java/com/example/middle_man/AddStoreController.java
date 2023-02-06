package com.example.middle_man;

import dataStorage.Database;
import entities.EntityCreation;
import entities.Person;
import entities.Store;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public final class AddStoreController implements EntityCreation {

    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldAddress;
    @FXML
    private TextField textFieldCity;
    @FXML
    private ChoiceBox<Person> personChoiceBox;

    @FXML
    private void initialize(){

        try {
            Connection connection = Database.connectToDatabase();
            List<Person> managerList = Database.getListOfPersons(connection);
            managerList = managerList.stream().filter(x -> x.getRole().getCode().equals("STMAN")).collect(Collectors.toList());
            ObservableList<Person> observableManagerList = FXCollections.observableList(managerList);
            personChoiceBox.setItems(observableManagerList);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @FXML
    public void addEntityToList() {
        String name = textFieldName.getText();
        String address = textFieldAddress.getText();
        String city = textFieldCity.getText();
        Person manager = personChoiceBox.getValue();
        Store store = new Store(0,name,manager,city,address);
        Database.insertStoreIntoDatabase(store);

    }
}
