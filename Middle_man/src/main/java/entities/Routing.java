package entities;

import com.example.middle_man.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public interface Routing {
    default void routeTo(String pathLocation){
        File file =
                new File("C:\\Users\\Santro\\Documents\\Middle_Man\\Middle_man\\src\\main\\resources\\com\\example\\middle_man\\"+pathLocation+"-view.fxml");
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
