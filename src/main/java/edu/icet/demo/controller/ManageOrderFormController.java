package edu.icet.demo.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.demo.bo.BoFactory;
import edu.icet.demo.bo.custom.impl.*;
import edu.icet.demo.model.Order;
import edu.icet.demo.model.OrderHasItem;
import edu.icet.demo.model.Product;
import edu.icet.demo.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ManageOrderFormController implements Initializable {
    public JFXTextField OrderIdTxt;
    public JFXComboBox employeeIdCmb;
    public JFXComboBox customerIdCmb;
    public JFXTextField productQtyTxt;
    public JFXComboBox productIdCmb;
    public JFXButton addProductBtn;
    public TableColumn productIdColumn;
    public TableColumn qtyColumn;
    public TableColumn amountColumn;
    public TableColumn actionColumn;
    public JFXTextField totalCostTxt;
    public TableView itemTable;
    public TableColumn nameColumn;

    CustomerBoImpl customerBoImpl = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    UserBoImpl userBoImpl = BoFactory.getInstance().getBo(BoType.USER);
    ProductBoImpl productBoImpl = BoFactory.getInstance().getBo(BoType.PRODUCT);
    PlaceOrderBoImpl placeOderBoImpl = BoFactory.getInstance().getBo(BoType.CART);
    OrderBoImpl orderBoImpl = BoFactory.getInstance().getBo(BoType.ORDER);

    double totalCost;
    int cardId;

    ObservableList<OrderHasItem> cartList = FXCollections.observableArrayList();
    int invoiceCid = 1;

    public void placeOrderBtnOnAction(ActionEvent actionEvent) {
        if(employeeIdCmb !=null && customerIdCmb!= null ){
            Date date = new Date();
            Order order = new Order(OrderIdTxt.getText(),
                    (String) customerIdCmb.getValue(),
                    "Pending",
                    date,
                    Double.parseDouble(totalCostTxt.getText()),
                    (String) employeeIdCmb.getValue());

            boolean isSaved = orderBoImpl.saveOrder(order);

            placeOderBoImpl.saveOrderDetails(cartList);//line 64
            cartList.clear();
            itemTable.setItems(cartList);
            OrderIdTxt.setText(placeOderBoImpl.generateOrderId());
            customerIdCmb.setValue(null);
            employeeIdCmb.setValue(null);
            productIdCmb.setValue(null);
            totalCost=0;
            totalCostTxt.setText(totalCost+"0");
        }
    }

    public void backBtnOnAction(ActionEvent actionEvent) throws IOException {
        SceneSwitchController.getInstance().switchScene(actionEvent,"user-dashboard-form.fxml");
    }

    public void addProductBtnOnAction(ActionEvent actionEvent) {
        Product product = productBoImpl.getProductById((String) productIdCmb.getValue());
        int oldQty = product.getQty();
        int qty =  Integer.parseInt(productQtyTxt.getText());
        if(oldQty>qty){
            if (productIdCmb != null && !productQtyTxt.getText().equals(""))
            {
                OrderHasItem orderItem = new OrderHasItem(cardId++,
                        OrderIdTxt.getText(),
                        (String) productIdCmb.getValue(),
                        product.getName(),
                        qty,
                        product.getPrice()*qty
                );
                totalCost+=product.getPrice()*qty;
                cartList.add(orderItem);
                productQtyTxt.setText("");
                productIdCmb.setValue(null);
                totalCostTxt.setText(totalCost+"0");
                itemTable.setItems(cartList);

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Something Missing");
                alert.setContentText("Please Check your Form again..!!");
                alert.showAndWait();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIdCmb.setItems(customerBoImpl.getAllCustomerIds());
        employeeIdCmb.setItems(userBoImpl.getAllUserIds());
        productIdCmb.setItems(productBoImpl.getAllProductIds());

        OrderIdTxt.setText(placeOderBoImpl.generateOrderId());

        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));



        actionColumn.setCellFactory(new Callback<TableColumn<OrderHasItem, Void>, TableCell<OrderHasItem, Void>>() {
            @Override
            public TableCell<OrderHasItem, Void> call(final TableColumn<OrderHasItem, Void> param) {
                final TableCell<OrderHasItem, Void> cell = new TableCell<>() {

                    private final JFXButton removeBtn = new JFXButton("Remove");

                    {
                        removeBtn.getStyleClass().add("remove-button");
                        removeBtn.setOnAction((ActionEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Remove");
                            alert.setContentText("Are you sure want to remove?");
                            Optional<ButtonType> result = alert.showAndWait();

                            if (result.get() == ButtonType.OK) {
                                OrderHasItem orderItem = getTableView().getItems().get(getIndex());
                                placeOderBoImpl.deleteById(orderItem.getId() + "");
                                cartList.remove(orderItem);
                                itemTable.setItems(cartList);
                            }
                        });

                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttonsBox = new HBox(removeBtn);
                            buttonsBox.setSpacing(10);
                            setGraphic(buttonsBox);
                        }
                    }
                };
                return cell;
            }
        });

    }
}
