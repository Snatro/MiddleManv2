package com.example.middle_man;

import entities.Person;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    private static Stage mainStage;
    public static Stage getStage() {
        return mainStage;
    }

    public static Person currentPerson;

    public static Person getCurrentPerson(){
        return currentPerson;
    }
    public static void setCurrentPerson(Person person){
        currentPerson = person;
    }
    public static void main(String[] args) {
        launch();
    }
}