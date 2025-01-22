module com.example.ejemplojavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    // Permite el acceso a JavaFX para cargar los controladores
    opens com.example.ejemplojavafx.controlador to javafx.fxml;

    // Exporta paquetes visibles desde otros módulos
    exports com.example.ejemplojavafx.controlador;
    //exports com.example.ejemplojavafx.vista;
    //exports com.example.ejemplojavafx.modelo;
    opens com.example.ejemplojavafx.modelo to javafx.base;

}