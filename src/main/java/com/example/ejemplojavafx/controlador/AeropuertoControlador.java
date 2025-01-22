package com.example.ejemplojavafx.controlador;

import com.example.ejemplojavafx.modelo.Aeropuerto;
import com.example.ejemplojavafx.modelo.AeropuertoPrivado;
import com.example.ejemplojavafx.modelo.AeropuertoPublico;
import com.example.ejemplojavafx.modelo.Direccion;
import com.example.ejemplojavafx.util.MetodosSueltos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AeropuertoControlador implements Initializable {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPais;

    @FXML
    private TextField txtCiudad;

    @FXML
    private TextField txtCalle;

    @FXML
    private TextField txtNumero;

    @FXML
    private TextField txtAnio;

    @FXML
    private TextField txtCapacidad;

    @FXML
    private TextField txtFinanciacion;

    @FXML
    private TextField txtDiscapacitados;

    @FXML
    private TextField txtSocios;

    @FXML
    private RadioButton rdbPublicos;

    @FXML
    private RadioButton rdbPrivados;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnCancelar;

    private Aeropuerto aeropuerto;

    public void initAtributos(Aeropuerto a){
        this.aeropuerto=a;

        this.txtNombre.setText(a.getNombre());
        this.txtPais.setText(a.getPais());
        this.txtCiudad.setText(a.getCiudad());
        this.txtCalle.setText(a.getCiudad());
        //Importante para los numero hay que poner +"" sino da error
        this.txtNumero.setText(a.getNumero()+"");
        this.txtAnio.setText(a.getAnioInauguracion()+"");
        this.txtCapacidad.setText(a.getCapacidad()+"");

        if(a instanceof AeropuertoPrivado){
            this.rdbPrivados.setSelected(true);
            AeropuertoPrivado ap=(AeropuertoPrivado) a;
            this.txtSocios.setText( ap.getNumero_socios()+"");
            //Hacer que no aparezcan cuando sea privado
            this.txtDiscapacitados.setEditable(false);
            this.txtFinanciacion.setEditable(false);
            this.txtSocios.setEditable(true);



        }else{
            this.rdbPublicos.setSelected(true);
            AeropuertoPublico ap=(AeropuertoPublico) a;
            this.txtDiscapacitados.setText(ap.getNumero_trab_discapacitados()+"");
            this.txtFinanciacion.setText(ap.getFinaniacion()+"");
            this.txtDiscapacitados.setEditable(true);
            this.txtFinanciacion.setEditable(true);
            this.txtSocios.setEditable(false);
        }
        this.rdbPrivados.setDisable(true);
        this.rdbPublicos.setDisable(true);





    }



    // Método para manejar la acción del botón "Guardar"
    @FXML
    private void guardar(ActionEvent event) {
        // Implementación pendiente
        String errores="";

        String nombre=this.txtNombre.getText();
        String pais=this.txtPais.getText();
        String ciudad=this.txtCiudad.getText();
        String calle=this.txtCalle.getText();

        if(!MetodosSueltos.validaNumeroEntero_Exp(this.txtNumero.getText())){
            errores +="-El numero de la calle debe de ser un numero. \n ";
        }
        if(!MetodosSueltos.validaNumeroEntero_Exp(this.txtCapacidad.getText())){
            errores +="-La capacidad debe de ser un numero.\n ";
        }
        if(!MetodosSueltos.validaNumeroEntero_Exp(this.txtAnio.getText())){
            errores +="-El año debe de ser un numero.\n ";
        }

        if(this.rdbPublicos.isSelected() && !MetodosSueltos.validaNumeroReal_Exp(this.txtFinanciacion.getText())){
            errores +="-La financiación debe de ser un numero.\n ";
        }

        if(this.rdbPublicos.isSelected() && !MetodosSueltos.validaNumeroEntero_Exp(this.txtDiscapacitados.getText())){
            errores +="-El numero de discapacitados debe de ser un numero.\n ";
        }

        if(this.rdbPrivados.isSelected() && !MetodosSueltos.validaNumeroEntero_Exp(this.txtSocios.getText())){
            errores +="-El numero de socios debe de ser un numero.\n ";
        }

        if(errores.isEmpty()){
           //En caso de que los numeros introducidos sean correctos:

            int numero =Integer.parseInt(this.txtNumero.getText());
            int capacidad =Integer.parseInt(this.txtCapacidad.getText());
            int anio =Integer.parseInt(this.txtAnio.getText());

            if(this.aeropuerto ==null){
                //Insertar
                Aeropuerto aux;
                Direccion dir=new Direccion(pais,ciudad,calle,numero);

                try {
                    if(dir.insertar()){
                        System.out.println("Buenas para comer aqui");
                        if(this.rdbPublicos.isSelected()){
                            double financiacion=Double.parseDouble(this.txtFinanciacion.getText());
                            int discapacitados=Integer.parseInt(this.txtDiscapacitados.getText());

                            aux=new AeropuertoPublico(financiacion,discapacitados,nombre,dir,anio,capacidad);
                        }else{
                            int socios=Integer.parseInt(this.txtSocios.getText());
                            aux=new AeropuertoPrivado(socios,nombre,dir,anio,capacidad);
                        }

                        aux.setDireccion(dir);

                        if(aux.insertar()){
                            Alert alert=new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setTitle("Exito");
                            alert.setContentText("El aeropuerto se ha insertado");
                            alert.showAndWait();
                        }else{
                            Alert alert=new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText(null);
                            alert.setTitle("Error");
                            alert.setContentText("El aeropuerto no se ha insertado");
                            alert.showAndWait();
                        }

                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }else{
                //Actualizar
                this.aeropuerto.setNombre(nombre);
                this.aeropuerto.setCapacidad(capacidad);
                this.aeropuerto.setAnioInauguracion(anio);
                Direccion dir=this.aeropuerto.getDireccion();

                dir.setPais(pais);
                dir.setCiudad(ciudad);
                dir.setCalle(calle);
                dir.setNumero(numero);
                try {
                    if(dir.actualizar()){
                        if(this.rdbPublicos.isSelected()){
                            double financiacion=Double.parseDouble(this.txtFinanciacion.getText());
                            int discapacitados=Integer.parseInt(this.txtDiscapacitados.getText());

                            AeropuertoPublico ap=(AeropuertoPublico) this.aeropuerto;
                            ap.setFinaniacion(financiacion);
                            ap.setNumero_trab_discapacitados(discapacitados);
                        }else{
                            int socios=Integer.parseInt(this.txtSocios.getText());
                            AeropuertoPrivado ap=(AeropuertoPrivado) this.aeropuerto;
                            ap.setNumero_socios(socios);
                        }
                    }

                    if(this.aeropuerto.actualizar()){
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Exito");
                        alert.setContentText("El aeropuerto se ha actualizado");
                        alert.showAndWait();
                    }else{
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setTitle("Error");
                        alert.setContentText("El aeropuerto no se ha actualizado");
                        alert.showAndWait();
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }


        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(errores);
            alert.showAndWait();
        }

    }

    // Método para manejar la acción del botón "Cancelar"
    @FXML
    private void cancelar(ActionEvent event) {
        Stage myStage = (Stage) this.btnCancelar.getScene().getWindow();
        myStage.close();
    }

    // Método para limpiar los campos de texto
    private void limpiarCampos() {
        txtNombre.clear();
        txtPais.clear();
        txtCiudad.clear();
        txtCalle.clear();
        txtNumero.clear();
        txtAnio.clear();
        txtCapacidad.clear();
        txtFinanciacion.clear();
        txtDiscapacitados.clear();
        txtSocios.clear();
        rdbPublicos.setSelected(true);
        rdbPrivados.setSelected(false);
    }

    // Método para mostrar alertas
    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    public void cambiarCamposPublicos(){
        this.txtDiscapacitados.setEditable(true);
        this.txtFinanciacion.setEditable(true);

        this.txtSocios.setEditable(false);

        //Vaciar el texto cuando se cambie de radioButton
        this.txtSocios.setText("");


    }
    public void cambiarCamposPrivado(){
        this.txtDiscapacitados.setEditable(false);
        this.txtFinanciacion.setEditable(false);
        this.txtSocios.setEditable(true);

        //Vaciar el texto cuando se cambie de radioButton
        this.txtDiscapacitados.setText("");
        this.txtFinanciacion.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup group=new ToggleGroup();
        this.rdbPublicos.setToggleGroup(group);
        this.rdbPrivados.setToggleGroup(group);

        this.txtSocios.setEditable(false);
    }
}
