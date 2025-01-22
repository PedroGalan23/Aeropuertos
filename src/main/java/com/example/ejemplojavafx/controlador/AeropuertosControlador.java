package com.example.ejemplojavafx.controlador;

import com.example.ejemplojavafx.modelo.Aeropuerto;
import com.example.ejemplojavafx.modelo.AeropuertoPrivado;
import com.example.ejemplojavafx.modelo.AeropuertoPublico;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AeropuertosControlador implements Initializable {

    @FXML
    private TableView tblAeropuertos;

    @FXML
    private TableColumn<Aeropuerto, Integer> colId;

    @FXML
    private TableColumn<Aeropuerto, String> colNombre;

    @FXML
    private TableColumn<Aeropuerto, String> colPais;

    @FXML
    private TableColumn<Aeropuerto, String> colCiudad;

    @FXML
    private TableColumn<Aeropuerto, String> colCalle;

    @FXML
    private TableColumn<Aeropuerto, Integer> colNumero;

    @FXML
    private TableColumn<Aeropuerto, Integer> colAnio;

    @FXML
    private TableColumn<Aeropuerto, Integer> colCapacidad;

    @FXML
    private TableColumn<Aeropuerto, Integer> colSocios;

    @FXML
    private TableColumn<Aeropuerto, Double> colFinanciacion;

    @FXML
    private TableColumn<Aeropuerto, Integer> colDiscapacitados;

    @FXML
    private RadioButton rdbPrivados;

    @FXML
    private RadioButton rdbPublicos;

    @FXML
    private TextField txtFiltroNombre;

    @FXML
    private void cambiarAeropuertosPrivados() {
        // Lógica para mostrar aeropuertos privados
        this.cargarAeropuertos();
    }

    @FXML
    private void cambiarAeropuertosPublicos() {
        // Lógica para mostrar aeropuertos públicos
        this.cargarAeropuertos();
    }

    @FXML
    private void añadirAeropuerto(ActionEvent event) {
        // Lógica para añadir un aeropuerto
        //TODO abrir la siguiente ventana
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/example/ejemplojavafx/vista/Aeropuerto.fxml"));

        Parent root= null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene=new Scene(root);

        Stage stage=new Stage();
        //Configuracion para que sea una ventana modal, es decir, que no se pueda abrir mas ventanas o que no deje salir
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Añadir aeropuerto");
        //Siempre que hagamos una ventana modal , se deberá de poner Show and Wait
        stage.showAndWait();

        //De esta manera recargará la pagina cuando se salga de la pantalla
        this.cargarAeropuertos();
    }

    @FXML
    private void editarAeropuerto(ActionEvent event) {

        // Lógica para editar un aeropuerto
        //Conseguimos recoger el aeropuerto con el puntero
        Aeropuerto a=(Aeropuerto) this.tblAeropuertos.getSelectionModel().getSelectedItem();
        if(a==null){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No tienes seleccionado ningún aeropuerto");
            alert.showAndWait();
        }else{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/example/ejemplojavafx/vista/Aeropuerto.fxml"));

            Parent root= null;
            try {
                root = loader.load();
                AeropuertoControlador controlador= loader.getController();
                controlador.initAtributos(a);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Scene scene=new Scene(root);

            Stage stage=new Stage();
            //Configuracion para que sea una ventana modal, es decir, que no se pueda abrir mas ventanas o que no deje salir
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Editar aeropuerto");
            //Siempre que hagamos una ventana modal , se deberá de poner Show and Wait
            stage.showAndWait();

            this.cargarAeropuertos();

        }
    }

    @FXML
    private void borrarAeropuerto() {
        // Lógica para borrar un aeropuerto
        Aeropuerto a=(Aeropuerto) this.tblAeropuertos.getSelectionModel().getSelectedItem();
        if(a==null){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No tienes seleccionado ningún aeropuerto");
            alert.showAndWait();
        }else {
            //Confirmación para borrar el aeropuerto
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Introduce");
            alert.setContentText("¿Quieres borrar el aeruopuerto?");
            Optional<ButtonType> action=alert.showAndWait();

            if(action.get()==ButtonType.OK){
                try {
                    if(a.borrarAeropuerto()){
                        if(a.getDireccion().borrar()){
                            Alert alertAeropuerto=new Alert(Alert.AlertType.INFORMATION);
                            alertAeropuerto.setHeaderText(null);
                            alertAeropuerto.setTitle("Exito");
                            alertAeropuerto.setContentText("Se ha borrado el aeropuerto y la direccion");
                            alertAeropuerto.showAndWait();
                        }else {
                            Alert alertAeropuerto = new Alert(Alert.AlertType.INFORMATION);
                            alertAeropuerto.setHeaderText(null);
                            alertAeropuerto.setTitle("Exito");
                            alertAeropuerto.setContentText("Se ha borrado el aeropuerto pero la dirección no");
                            alertAeropuerto.showAndWait();
                        }
                    }else{
                        Alert alertAeropuerto=new Alert(Alert.AlertType.ERROR);
                        alertAeropuerto.setHeaderText(null);
                        alertAeropuerto.setTitle("Error");
                        alertAeropuerto.setContentText("No se ha podido borrar el aeropuerto");
                        alertAeropuerto.showAndWait();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                this.cargarAeropuertos();
                this.tblAeropuertos.refresh();
            }
        }
    }

    @FXML
    private void gananciasAeropuerto() {
        // Lógica para calcular ganancias del aeropuerto
    }

    @FXML
    private void infoAeropuerto() {
        // Lógica para mostrar información del aeropuerto
    }

    @FXML
    private void aniadriAvion() {
        // Lógica para añadir un avión
    }

    @FXML
    private void borrarAvion() {
        // Lógica para borrar un avión
    }

    @FXML
    private void activarDesactivarAvion() {
        // Lógica para activar/desactivar un avión
    }

    @FXML
    private void filtroPorNombre(KeyEvent event) {
        // Lógica para filtrar la tabla por nombre del aeropuerto
        this.cargarAeropuertos();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Hacer que los radioButtons se comporten a la par en el codigo
        ToggleGroup group=new ToggleGroup();
        this.rdbPrivados.setToggleGroup(group);
        this.rdbPublicos.setToggleGroup(group);

        //Esto va a hacer que del objeto Aeropuerto coja el id correspondiente
        this.colId.setCellValueFactory(new PropertyValueFactory("id"));
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colPais.setCellValueFactory(new PropertyValueFactory("pais"));
        this.colCiudad.setCellValueFactory(new PropertyValueFactory("ciudad"));
        this.colCalle.setCellValueFactory(new PropertyValueFactory("calle"));
        this.colNumero.setCellValueFactory(new PropertyValueFactory("numero"));
        this.colAnio.setCellValueFactory(new PropertyValueFactory("anioInauguracion"));
        this.colCapacidad.setCellValueFactory(new PropertyValueFactory("capacidad"));
        this.colSocios.setCellValueFactory(new PropertyValueFactory("numero_socios"));
        this.colFinanciacion.setCellValueFactory(new PropertyValueFactory("finaniacion"));
        this.colDiscapacitados.setCellValueFactory(new PropertyValueFactory("numero_trab_discapacitados"));

        this.cargarAeropuertos();


    }

    private void cargarAeropuertos(){

        String busqueda=this.txtFiltroNombre.getText();
        if(this.rdbPrivados.isSelected()){
            AeropuertoPrivado ap= new AeropuertoPrivado();
            try {
                ObservableList<AeropuertoPrivado> obs=ap.getAeropuertos(busqueda);
                this.tblAeropuertos.setItems(obs);
                this.colFinanciacion.setVisible(false);
                this.colDiscapacitados.setVisible(false);
                this.colSocios.setVisible(true);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }else{
            AeropuertoPublico ap= new AeropuertoPublico();
            try {
                ObservableList<AeropuertoPublico> obs=ap.getAeropuertos(busqueda);
                this.tblAeropuertos.setItems(obs);
                this.colFinanciacion.setVisible(true);
                this.colDiscapacitados.setVisible(true);
                this.colSocios.setVisible(false);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
