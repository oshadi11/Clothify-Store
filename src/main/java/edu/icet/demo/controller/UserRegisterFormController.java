package edu.icet.demo.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.icet.demo.bo.custom.impl.UserBoImpl;
import edu.icet.demo.model.User;
import javafx.collections.FXCollections;
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

public class UserRegisterFormController implements Initializable {
    public JFXComboBox<Integer> ageCmb;
    public JFXTextField employeeEmailTxt;
    public JFXPasswordField passwordTxt;
    public JFXTextField employeeNameTxt;
    public JFXTextField CityTxt;
    public JFXTextField employeeId;
    public JFXButton registerBtn;
    public JFXButton registerBlackBtn;
    public TableView<User> employeeTable;
    public TableColumn<User, String> employeeIdColumn;
    public TableColumn<User, String> employeeNameColumn;
    public TableColumn<User, String> employeeEmailColumn;
    public TableColumn<User, Integer> employeeAgeColumn;
    public TableColumn<User, String> employeeCityColumn;
    public TableColumn<User, Void> actionCol;
    public JFXButton updateblackBtn;

    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();
    UserBoImpl userBoImpl = new UserBoImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var ageList = FXCollections.<Integer>observableArrayList();
        for (int i = 18; i <= 50; i++) {
            ageList.add(i);
        }
        ageCmb.setItems(ageList);

        employeeId.setText(userBoImpl.generateEmployeeId());

        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeeEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        employeeAgeColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        employeeCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

        // Set up the custom cell factory for the action column
        actionCol.setCellFactory(new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<User, Void>() {

                    private final JFXButton removeBtn = new JFXButton("Remove");
                    private final JFXButton updateBtn = new JFXButton("Update");

                    {
                        removeBtn.getStyleClass().add("remove-button");
                        removeBtn.setOnAction((ActionEvent event) -> {
                            User user = getTableView().getItems().get(getIndex());
                            userBoImpl.deleteUserById(user.getId());
                            employeeTable.setItems(userBoImpl.getAllUsers());
                        });
                        updateBtn.getStyleClass().add("update-button");
                        updateBtn.setOnAction((ActionEvent event) -> {
                            registerBtn.setVisible(false);
                            updateblackBtn.setVisible(true);
                            User user = getTableView().getItems().get(getIndex());
                            employeeId.setText(user.getId());
                            employeeId.setEditable(false);
                            CityTxt.setText(user.getCity());
                            employeeNameTxt.setText(user.getName());
                            passwordTxt.setText(user.getPassword());
                            employeeEmailTxt.setText(user.getEmail());
                            ageCmb.setValue(user.getAge());
                            employeeTable.setItems(userBoImpl.getAllUsers());
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

        employeeTable.setItems(userBoImpl.getAllUsers());
    }

    public void registerBtnOnAction(ActionEvent actionEvent) {
        Integer age = ageCmb.getValue();
        User user = new User(employeeId.getText(),
                employeeNameTxt.getText(),
                employeeEmailTxt.getText(),
                passwordTxt.getText(),
                CityTxt.getText(),
                age,
                "Employee");

        if (!employeeNameTxt.getText().isEmpty() && !employeeEmailTxt.getText().isEmpty()
                && !passwordTxt.getText().isEmpty()) {

            boolean isInsert = userBoImpl.insertUser(user);

            if (isInsert) {
                employeeTable.setItems(userBoImpl.getAllUsers());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Employee Added");
                alert.setContentText("Employee Added Successfully..!");
                alert.showAndWait();
                employeeId.setText(userBoImpl.generateEmployeeId());
                CityTxt.setText("");
                employeeEmailTxt.setText("");
                employeeNameTxt.setText("");
                passwordTxt.setText("");
                ageCmb.setValue(null);
            }
        }
    }


    public void updateEmployeeBtnOnAction(ActionEvent actionEvent) {
        User user = new User(employeeId.getText(),
                employeeNameTxt.getText(),
                employeeEmailTxt.getText(),
                passwordTxt.getText(),
                CityTxt.getText(),
                ageCmb.getValue(),
                "Employee");
        boolean isUpdated = userBoImpl.updateUser(user);
        System.out.println(isUpdated);
        if (isUpdated){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Employee Update");
            alert.setContentText("Employee Updated Successfully");
            alert.showAndWait();
            CityTxt.setText("");
            employeeEmailTxt.setText("");
            employeeNameTxt.setText("");
            passwordTxt.setText("");
            employeeId.setText("");
            ageCmb.setValue(null);
            employeeTable.setItems(userBoImpl.getAllUsers());
        }
    }

    public void backBtnOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(actionEvent, "admin-dashboard-form.fxml");
    }

}
