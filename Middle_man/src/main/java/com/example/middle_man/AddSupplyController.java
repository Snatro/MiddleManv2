package com.example.middle_man;

import dataStorage.Database;
import entities.Category;
import entities.Person;
import entities.Supply;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import javax.xml.catalog.CatalogResolver;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public final class AddSupplyController implements entities.EntityCreation {

    @FXML
    private TextField textFieldName;
    @FXML
    private ChoiceBox<Category> categoryChoiceBox;

    @FXML
    private void initialize(){

        try {
            Connection connection = Database.connectToDatabase();
            List<Category> categories = Database.getListOfCategories(connection);
            ObservableList<Category> observableCategoriesList = FXCollections.observableList(categories);
            categoryChoiceBox.setItems(observableCategoriesList);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @FXML
    public void addEntityToList() {
        String name = textFieldName.getText();
        Category category = categoryChoiceBox.getValue();
        Supply supply = new Supply(0,name,category);
        Database.insertSupplyIntoDatabase(supply);
    }
}
