package com.example.my_lms;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;


public class AdminPasswordController {

    public PasswordField pfNewPassword;
    public PasswordField pfConfirmPassword;
    public ImageView ivBack;

    Connection con = null;
    Statement stnt = null;


    public void ChangeAction(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(pfNewPassword.getText().length()==0 || pfConfirmPassword.getText().length()==0){
            alert.setContentText("All fields are not filled");
            alert.show();
            return;
        }
        if(pfNewPassword.getText().equals(pfConfirmPassword.getText())) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
                stnt = con.createStatement();

                String query = "UPDATE admin SET password ='" + pfNewPassword.getText() + "'   ";
                PreparedStatement ps = con.prepareStatement(query);
                ps.executeUpdate();

                stnt.close();
                con.close();
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("Password Changed Successfully!...");
                a.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{

            alert.setContentText("Password in both fields are not same");
            alert.show();
        }

    }

    public void ivBackAction(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ivBack.getScene().getWindow();
        stage.close();
        HelloApplication.SceneSwitch( "Admin.fxml", "Admin",stage );

    }
}
