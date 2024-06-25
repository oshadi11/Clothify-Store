package edu.icet.demo.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.demo.bo.BoFactory;
import edu.icet.demo.bo.custom.impl.SupplierBoImpl;
import edu.icet.demo.model.Supplier;
import edu.icet.demo.util.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageSupplierFormController implements Initializable {
    public TableColumn companyColumn;
    public TableColumn supplierEmailColumn;
    public TableColumn supplierNameColumn;
    public TableColumn supplierIdColumn;
    public JFXButton addSupplierBtn;
    public TableView supplierTable;
    public JFXTextField companyTxt;
    public JFXTextField supplierNameTxt;
    public JFXTextField supplierEmailTxt;
    public JFXTextField supplierIdTxt;
    public TableColumn actionColumn;
    public JFXButton updateBtn2;

    SupplierBoImpl supplierBoImpl = BoFactory.getInstance().getBo(BoType.SUPPLIER);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(supplierBoImpl);
        supplierIdTxt.setText(supplierBoImpl.generateSupplierId());
        supplierIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        supplierNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        supplierEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        companyColumn.setCellValueFactory(new PropertyValueFactory<>("company"));


        actionColumn.setCellFactory(new Callback<TableColumn<Supplier, Void>, TableCell<Supplier, Void>>() {
            @Override
            public TableCell<Supplier, Void> call(final TableColumn<Supplier, Void> param) {
                final TableCell<Supplier, Void> cell = new TableCell<Supplier, Void>() {

                    private final JFXButton removeBtn = new JFXButton("Remove");
                    private final JFXButton updateBtn = new JFXButton("Update");

                    {
                        removeBtn.getStyleClass().add("remove-button");
                        removeBtn.setOnAction((ActionEvent event) -> {
                            Supplier supplier = getTableView().getItems().get(getIndex());
                            supplierBoImpl.deleteSupplierById(supplier.getId());
                            supplierTable.setItems(supplierBoImpl. getAllSuppliers());
                        });
                        updateBtn.getStyleClass().add("update-button");
                        updateBtn.setOnAction((ActionEvent event) -> {
                            addSupplierBtn.setVisible(false);
                            updateBtn2.setVisible(true);
                            Supplier supplier = getTableView().getItems().get(getIndex());
                            supplierIdTxt.setText(supplier.getId());
                            supplierIdTxt.setEditable(false);
                            companyTxt.setText(supplier.getCompany());
                            supplierNameTxt.setText(supplier.getName());
                            supplierEmailTxt.setText(supplier.getEmail());
                            supplierTable.setItems(supplierBoImpl.getAllSuppliers());
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
        supplierTable.setItems(supplierBoImpl.getAllSuppliers());
    }
    public void backBtnOnAction(ActionEvent actionEvent) throws IOException {
        SceneSwitchController.getInstance().switchScene(actionEvent,"user-dashboard-form.fxml");
    }

    public void addSupplierBtnOnAction(ActionEvent actionEvent) {
        if (!supplierNameTxt.getText().equals("") && !supplierEmailTxt.getText().equals("") && !companyTxt.equals("")) {
            Supplier supplier = new Supplier(supplierIdTxt.getText(),
                    supplierNameTxt.getText(),
                    supplierEmailTxt.getText(),
                    companyTxt.getText());
            boolean isAdded = supplierBoImpl.addSupplier(supplier);

            if (isAdded) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Supplier");
                alert.setContentText("Supplier Added Successfully");
                alert.showAndWait();
                supplierIdTxt.setText(supplierBoImpl.generateSupplierId());
                supplierEmailTxt.setText("");
                supplierNameTxt.setText("");
                companyTxt.setText("");
                supplierTable.setItems(supplierBoImpl.getAllSuppliers());

            }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Something Missing");
            alert.setContentText("Please check your form again");
            alert.showAndWait();
            }
        }
    }


    public void updateBtn2OnAction(ActionEvent actionEvent) {
        Supplier supplier = new Supplier(supplierIdTxt.getText(),
                supplierNameTxt.getText(),
                supplierEmailTxt.getText(),
                companyTxt.getText());
        boolean isUpdated = supplierBoImpl.updateSupplier(supplier);
        System.out.println(isUpdated);
        if (isUpdated){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Supplier Update");
            alert.setContentText("Supplier Updated Successfully");
            alert.showAndWait();
            supplierNameTxt.setText("");
            supplierEmailTxt.setText("");
            companyTxt.setText("");
            supplierIdTxt.setText("");
            supplierTable.setItems(supplierBoImpl.getAllSuppliers());
        }
    }
}
