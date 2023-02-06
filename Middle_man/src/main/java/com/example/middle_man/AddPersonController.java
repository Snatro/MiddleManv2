package com.example.middle_man;

import dataStorage.Database;
import entities.Person;
import entities.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public final class AddPersonController implements entities.EntityCreation {
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ChoiceBox<Role> roleChoiceBox;

    @FXML
    private void initialize(){

        try {
            Connection connection = Database.connectToDatabase();
            List<Role> roles = Database.getListOfRoles(connection);
            ObservableList<Role> roleObservableList = FXCollections.observableList(roles);
            roleChoiceBox.setItems(roleObservableList);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @FXML
    public void addEntityToList() {
        String name = textFieldName.getText();
        Role role = roleChoiceBox.getValue();
        Person person = new Person(0,name,role);
        Database.insertPersonIntoDatabase(person);
    }
}
