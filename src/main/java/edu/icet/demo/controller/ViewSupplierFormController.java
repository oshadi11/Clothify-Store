package edu.icet.demo.controller;

import edu.icet.demo.bo.BoFactory;
import edu.icet.demo.bo.custom.impl.SupplierBoImpl;
import edu.icet.demo.util.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewSupplierFormController  implements Initializable {
    public TableView supplierTable;
    public TableColumn supplierIdColumn;
    public TableColumn supplierEmailColumn;
    public TableColumn supplierNameColumn;
    public TableColumn companyColumn;

    SupplierBoImpl supplierBoImpl = BoFactory.getInstance().getBo(BoType.SUPPLIER);
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        supplierIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        supplierNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        supplierEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        companyColumn.setCellValueFactory(new PropertyValueFactory<>("company"));

        supplierTable.setItems(supplierBoImpl.getAllSuppliers());

    }

    public void backBtnOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(actionEvent,"admin-dashboard-form.fxml");
    }
}
