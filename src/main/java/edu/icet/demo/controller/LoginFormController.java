package edu.icet.demo.controller;

import com.jfoenix.controls.*;
import edu.icet.demo.bo.BoFactory;
import edu.icet.demo.bo.custom.impl.UserBoImpl;
import edu.icet.demo.entity.UserEntity;
import edu.icet.demo.util.BoType;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    public JFXButton enterBtn;
    public JFXTextField emailTxt;

    public JFXPasswordField passwordTxt;
    public JFXComboBox userTypeCmb;
    public StackPane stackPane;
    public AnchorPane loginform;

    private Parent root;
    private Stage stage;

    private Scene scene;

    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    public void loginBtnOnAction(ActionEvent actionEvent) throws IOException {

        UserBoImpl userBoImpl = BoFactory.getInstance().getBo(BoType.USER);
        UserEntity userEntity = userBoImpl.getUserByEmail(emailTxt.getText());

        if (userEntity==null){
            new Alert(Alert.AlertType.ERROR,"Invalid Email Address").show();
            return;
        }
        if (userEntity.getRole().equals("Admin") && (userEntity.getPassword()).equals(passwordTxt.getText())){
            System.out.println("Logged");
            try {
                SceneSwitchController.getInstance().switchScene(actionEvent,"admin-dashBoard-form.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else if (userEntity.getRole().equals("Employee") && (userEntity.getPassword()).equals(passwordTxt.getText())){
            SceneSwitchController.getInstance().switchScene(actionEvent,"user-dashboard-form.fxml");
        } else if (userEntity.getId()==null) {
            System.out.println("Null");
        } else{
            new Alert(Alert.AlertType.ERROR,"Invalid Password").show();
        }

    }

    public void loadUserTypeRequiredAlert(){
        JFXDialogLayout alert = new JFXDialogLayout();
        alert.setHeading(new Text("Please Select the type of User"));
        JFXButton btn = new JFXButton("Ok");
        JFXDialog dialog = new JFXDialog(stackPane,alert,JFXDialog.DialogTransition.CENTER);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dialog.close();
            }
        });
        alert.setActions(btn);
        dialog.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userTypeCmb.setItems(FXCollections.observableArrayList("Admin","Default User"));
    }
}
