package com.example.ejemplojavafx.controlador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        try {
            FXMLLoader loader=new FXMLLoader();  //Nos ayuda a cargar una vista en concreto
            loader.setLocation(Main.class.getResource("/com/example/ejemplojavafx/vista/LoginVista.fxml"));
            Pane ventana = (Pane) loader.load();

            Scene scene=new Scene(ventana);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Login");
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
