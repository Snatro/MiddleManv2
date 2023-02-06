package com.example.middle_man;

import dataStorage.Directories;
import entities.Person;
import entities.Routing;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;


public class LogInController implements Routing {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void onClickLogInButton(){
        String username = usernameField.getText();
        String password = passwordField.getText();
        Optional<Person> loginPerson = Directories.findPerson(username,password);
        if(loginPerson.isPresent()){
            HelloApplication.setCurrentPerson(loginPerson.get());
            routeTo("store-list");
        }


    }
}
