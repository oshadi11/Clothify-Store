package edu.icet.demo.controller;

import edu.icet.demo.bo.custom.impl.UserBoImpl;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDashboardFormController {

    public AnchorPane adminDashboard;

    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    public void logOutBtnOnMouseClick(MouseEvent mouseEvent) throws IOException {
        sceneSwitch.switchScene(mouseEvent,"login-form.fxml");
    }

    public void employeeBtnOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(actionEvent,"user-register-form.fxml");
    }

    public void supplierBtnOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(actionEvent,"view-supplier-form.fxml");
    }

    public void orderBtnOnAction(ActionEvent actionEvent) {
    }

    public void customerBtnOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(actionEvent,"view-customer-form.fxml");
    }

    public void productBtnOnAction(ActionEvent actionEvent) {
    }
}
