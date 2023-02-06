package com.example.middle_man;

import dataStorage.Database;
import dataStorage.Directories;
import entities.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws SQLException, IOException {
        File file = new File("C:\\Users\\Santro\\Documents\\Middle_Man\\Middle_man\\src\\main\\resources\\com\\example\\middle_man\\login-view.fxml");
        URL fxmlFile = null;
        try {
            fxmlFile = new URL("file:///" + file.getAbsolutePath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlFile);
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HelloApplication.getStage().setTitle("Middle_Man");
        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }
}