package com.example.middle_man;

import dataStorage.Database;
import entities.EntitiesDataManipulation;
import entities.Person;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PersonController implements EntitiesDataManipulation {

    @FXML
    private TableView<Person> personTableView;
    @FXML
    private TableColumn<Person,String> tableColumnId;
    @FXML
    private TableColumn<Person,String> tableColumnName;
    @FXML
    private TableColumn<Person,String> tableColumnRole;

    public List<Person> persons;

    public static Person chosenPerson;

    public static Person getPerson(){
        return chosenPerson;
    }

    @FXML
    private void initialize(){
        try {
            persons = Database.getListOfPersons(Database.connectToDatabase());
            tableColumnId.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(String.valueOf(cellData.getValue().getId()));
            });
            tableColumnName.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(cellData.getValue().getName());
            });
            tableColumnRole.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(cellData.getValue().getRole().getCode());
            });
            ObservableList<Person> observableList = FXCollections.observableList(persons);
            personTableView.setItems(observableList);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void deleteEntityButton() {
        Person person = personTableView.getSelectionModel().getSelectedItem();

        try {
            Database.deletePerson(person);
            persons = Database.getListOfPersons(Database.connectToDatabase());
            ObservableList<Person> observableList = FXCollections.observableList(persons);
            personTableView.setItems(observableList);

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void editEntityButton() {

    }
}
