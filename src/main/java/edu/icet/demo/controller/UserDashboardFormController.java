package edu.icet.demo.controller;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class UserDashboardFormController {
    public void logOutBtnOnMouseClick(MouseEvent mouseEvent) {
    }

    public void supplierBtnOnAction(ActionEvent actionEvent) throws IOException {
        SceneSwitchController.getInstance().switchScene(actionEvent,"manage-supplier-form.fxml");
    }

    public void productBtnOnAction(ActionEvent actionEvent) throws IOException {
        SceneSwitchController.getInstance().switchScene(actionEvent,"manage-product-form.fxml");
    }

    public void customerBtnOnAction(ActionEvent actionEvent) throws IOException {
        SceneSwitchController.getInstance().switchScene(actionEvent,"manage-customer-form.fxml");
    }

    public void orderBtnOnAction(ActionEvent actionEvent) {
    }
}
