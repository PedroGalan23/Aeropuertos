package com.example.ejemplojavafx.controlador;

import com.example.ejemplojavafx.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginControlador implements Initializable {
    @FXML
    private TextField txtUsuario;
    @FXML
    private Button btnlogin;
    @FXML
    private PasswordField txtPassword;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    private void comprobarLogin(ActionEvent event){
        String usuario=this.txtUsuario.getText();

        String password=this.txtPassword.getText();

        Usuario user=new Usuario(usuario,password);

        try {
            if(user.login()){
                Alert alert= new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Exito");
                alert.setContentText("Login correcto");
                alert.showAndWait();

                //TODO abrir la siguiente ventana
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/example/ejemplojavafx/vista/AeropuertosVista.fxml"));

                Parent root=loader.load();

                Scene scene=new Scene(root);

                Stage stage=new Stage();
                stage.setScene(scene);
                stage.setTitle("Aeropuertos");
                stage.show();

                Stage myStage = (Stage) this.btnlogin.getScene().getWindow();
                myStage.close();


            }else{
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("ERROR");
                alert.setContentText("Login incorrecto");
                alert.showAndWait();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }


    }
}
