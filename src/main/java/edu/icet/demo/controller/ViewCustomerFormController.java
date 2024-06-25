package edu.icet.demo.controller;

import edu.icet.demo.bo.BoFactory;
import edu.icet.demo.bo.custom.impl.CustomerBoImpl;
import edu.icet.demo.util.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewCustomerFormController implements Initializable {
    public TableColumn custIdColumn;
    public TableColumn custNameColumn;
    public TableColumn custEmailColumn;
    public TableColumn cityColumn;
    public TableView customerTable;
    CustomerBoImpl customerBoImpl = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    public void backBtnOnAction(ActionEvent actionEvent) throws IOException {
        SceneSwitchController.getInstance().switchScene(actionEvent,"admin-dashboard-form.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        custIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        custNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        custEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

        customerTable.setItems(customerBoImpl.getAllCustomer());
    }
}
