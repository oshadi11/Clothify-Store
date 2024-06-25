package edu.icet.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitchController {
    private static SceneSwitchController instance;

    private SceneSwitchController(){}

    public static SceneSwitchController getInstance(){
        if (instance==null){
            return instance = new SceneSwitchController();
        }
        return instance;
    }

    public void switchScene(ActionEvent event, String fxml) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("/view/"+fxml));
       Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.show();
    }

    public void switchScene(MouseEvent mouseEvent, String fxml) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/"+fxml));
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
