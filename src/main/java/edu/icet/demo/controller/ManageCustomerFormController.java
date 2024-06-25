package edu.icet.demo.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.demo.bo.BoFactory;
import edu.icet.demo.bo.custom.CustomerBo;
import edu.icet.demo.bo.custom.impl.CustomerBoImpl;
import edu.icet.demo.bo.custom.impl.SupplierBoImpl;
import edu.icet.demo.dao.custom.CustomerDao;
import edu.icet.demo.model.Customer;
import edu.icet.demo.model.Supplier;
import edu.icet.demo.util.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageCustomerFormController implements Initializable {
    public JFXButton updateBtn2;
    public TableColumn actionColumn;
    public TableColumn cityColumn;
    public TableColumn custEmailColumn;
    public TableColumn custNameColumn;
    public TableColumn custIdColumn;
    public JFXTextField custIdTxt;
    public JFXTextField custEmailTxt;
    public JFXTextField custNameTxt;
    public JFXTextField cityTxt;
    public JFXButton addCustomerBtn;
    public TableView customerTable;

    CustomerBoImpl customerBoImpl = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        custIdTxt.setText(customerBoImpl.generateCustomerId());
        custIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        custNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        custEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));


        actionColumn.setCellFactory(new Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>>() {
            @Override
            public TableCell<Customer, Void> call(final TableColumn<Customer, Void> param) {
                final TableCell<Customer, Void> cell = new TableCell<Customer, Void>() {

                    private final JFXButton removeBtn = new JFXButton("Remove");
                    private final JFXButton updateBtn = new JFXButton("Update");

                    {
                        removeBtn.getStyleClass().add("remove-button");
                        removeBtn.setOnAction((ActionEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Remove");
                            alert.setContentText("Are you sure want to remove?");
                            Optional<ButtonType> result = alert.showAndWait();

                            if (result.get() == ButtonType.OK) {
                                Customer customer = getTableView().getItems().get(getIndex());
                                customerBoImpl.deleteCustomerById(customer.getId());
                                customerTable.setItems(customerBoImpl. getAllCustomer());
                            }
                        });
                        updateBtn.getStyleClass().add("update-button");
                        updateBtn.setOnAction((ActionEvent event) -> {
                            addCustomerBtn.setVisible(false);
                            updateBtn2.setVisible(true);
                            Customer customer = getTableView().getItems().get(getIndex());
                            custIdTxt.setText(customer.getId());
                            custIdTxt.setEditable(false);
                            cityTxt.setText(customer.getCity());
                            custNameTxt.setText(customer.getName());
                            custEmailTxt.setText(customer.getEmail());
                            customerTable.setItems(customerBoImpl.getAllCustomer());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttonsBox = new HBox(removeBtn, updateBtn);
                            buttonsBox.setSpacing(10);
                            setGraphic(buttonsBox);
                        }
                    }
                };
                return cell;
            }
        });
        customerTable.setItems(customerBoImpl.getAllCustomer());

    }
    public void updateBtn2OnAction(ActionEvent actionEvent) {
        Customer customer = new Customer(custIdTxt.getText(),
                custNameTxt.getText(),
                custEmailTxt.getText(),
                cityTxt.getText());
        boolean isUpdated = customerBoImpl.updateCustomer(customer);
        System.out.println(isUpdated);
        if (isUpdated){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Customer Update");
            alert.setContentText("Customer Updated Successfully");
            alert.showAndWait();
            custNameTxt.setText("");
            custEmailTxt.setText("");
            cityTxt.setText("");
            custIdTxt.setText("");
            customerTable.setItems(customerBoImpl.getAllCustomer());
        }
    }

    public void backBtnOnAction(ActionEvent actionEvent) throws IOException {
        SceneSwitchController.getInstance().switchScene(actionEvent,"user-dashboard-form.fxml");
    }

    public void addCustomerBtnOnAction(ActionEvent actionEvent) {
        if (!custNameTxt.getText().equals("") && !custEmailTxt.getText().equals("")) {
            Customer customer = new Customer(custIdTxt.getText(),
                    custNameTxt.getText(),
                    custEmailTxt.getText(),
                    cityTxt.getText());
            boolean isAdded = customerBoImpl.insertCustomer(customer);

            if (isAdded) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Supplier");
                alert.setContentText("Supplier Added Successfully");
                alert.showAndWait();
                custIdTxt.setText(customerBoImpl.generateCustomerId());
                custEmailTxt.setText("");
                custNameTxt.setText("");
                cityTxt.setText("");
                customerTable.setItems(customerBoImpl.getAllCustomer());

            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Something Missing");
                alert.setContentText("Please check your form again");
                alert.showAndWait();
            }
        }
    }



}
