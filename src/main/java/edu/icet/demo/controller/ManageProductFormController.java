package edu.icet.demo.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.demo.bo.custom.impl.ProductBoImpl;
import edu.icet.demo.bo.custom.impl.SupplierBoImpl;
import edu.icet.demo.model.Customer;
import edu.icet.demo.model.Product;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class ManageProductFormController implements Initializable {
    public JFXTextField productIdTxt;
    public JFXTextField productQtyTxt;
    public JFXTextField productNameTxt;
    public JFXButton addProductBtn;
    public TableView productTable;
    public TableColumn productNameColumn;
    public TableColumn prductIdColumn;
    public TableColumn qtyColumn;
    public TableColumn categoryColumn;
    public TableColumn priceColumn;
    public TableColumn supplierIdColumn;
    public TableColumn actionColumn;
    public JFXComboBox productCategoryCmb;
    public JFXTextField proudctPrice;
    public JFXComboBox SupplierIdCmb;
    public JFXButton updateBtn2;
    ProductBoImpl productBoImpl = new ProductBoImpl();
    SupplierBoImpl supplierBoImpl = new SupplierBoImpl();
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();


    public void backBtnOnAction(ActionEvent actionEvent) {
    }

    public void addProductBtnOnAction(ActionEvent actionEvent) {
        if (SupplierIdCmb != null && !productNameTxt.getText().equals("")
                && !productQtyTxt.getText().equals("") && !proudctPrice.getText().equals(""))
        {
            Product product = new Product(productIdTxt.getText(),
                    productNameTxt.getText(),
                    Integer.parseInt(productQtyTxt.getText()),
                    (String) productCategoryCmb.getValue(),
                    Double.parseDouble(proudctPrice.getText()),
                    (String) SupplierIdCmb.getValue());
            boolean isAdd =  productBoImpl.addProduct(product);
            if (isAdd) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Product Added");
                alert.setContentText("Product Added successfully");
                alert.showAndWait();
                productIdTxt.setText(productBoImpl.generateProductId());
                productNameTxt.setText("");
                productQtyTxt.setText("");
                SupplierIdCmb.setValue(null);
                productCategoryCmb.setValue(null);
                proudctPrice.setText("");
                productTable.setItems(productBoImpl.getAllProducts());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something Missing");
            alert.setContentText("Please Check your Form again..!!");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SupplierIdCmb.setItems(supplierBoImpl.getAllSupplierIds());

        productCategoryCmb.setItems(FXCollections.observableArrayList("Ladies","Gents","Kids"));
        productIdTxt.setText(productBoImpl.generateProductId());
        prductIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        supplierIdColumn.setCellValueFactory(new PropertyValueFactory<>("supId"));


        actionColumn.setCellFactory(new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<Product, Void>() {

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
                                Product product = getTableView().getItems().get(getIndex());
                                productBoImpl.deleteProduct(product.getId());
                                productTable.setItems(productBoImpl. getAllProducts());
                            }
                        });
                        updateBtn.getStyleClass().add("update-button");
                        updateBtn.setOnAction((ActionEvent event) -> {

                            addProductBtn.setVisible(false);
                            updateBtn2.setVisible(true);

                            Product product = getTableView().getItems().get(getIndex());
                            String qty = product.getQty()+"";
                            String category = product.getCategory()+"";
                            String price = product.getPrice()+"0";
                            productIdTxt.setText(product.getId());
                            productIdTxt.setEditable(false);
                            productQtyTxt.setText(qty);
                            productCategoryCmb.setValue(category);
                            productNameTxt.setText(product.getName());
                            SupplierIdCmb.setValue(product.getSupId());
                            SupplierIdCmb.setEditable(false);
                            proudctPrice.setText(price);
                            productTable.setItems(productBoImpl.getAllProducts());
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
        productTable.setItems(productBoImpl.getAllProducts());
    }

    public void updateBtn2OnAction(ActionEvent actionEvent) {
        Product product = new Product(productIdTxt.getText(),
                productNameTxt.getText(),
                Integer.parseInt(productQtyTxt.getText()),
                (String)productCategoryCmb.getValue(),
                Double.parseDouble(proudctPrice.getText()),
                (String)SupplierIdCmb.getValue());
        boolean isUpdated = productBoImpl.updateProduct(product);
        if (isUpdated){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product Update");
            alert.setContentText("Product Updated Successfully");
            alert.showAndWait();
            productIdTxt.setText("");
            productNameTxt.setText("");
            productQtyTxt.setText("");
            proudctPrice.setText("");
            SupplierIdCmb.setValue(null);
            productCategoryCmb.setValue(null);
            productTable.setItems(productBoImpl.getAllProducts());
        }
    }
}
