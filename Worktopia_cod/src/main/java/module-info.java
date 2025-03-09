module org.example.worktopia_cod {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.apache.poi.ooxml;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires java.desktop;
    requires org.apache.logging.log4j.core;


    opens Modelos to javafx.fxml;
    opens Aplicaciones to javafx.graphics;
    opens Clases to javafx.base;
    exports Aplicaciones;
    exports Modelos;

}