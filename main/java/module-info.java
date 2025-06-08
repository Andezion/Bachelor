module Bachelor
{
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.json;
    requires com.fasterxml.jackson.databind;

    opens controller to javafx.fxml;
    opens model to javafx.base, com.fasterxml.jackson.databind, javafx.fxml;
    opens app to javafx.graphics;

    exports controller;
    exports app;
    exports model;
}