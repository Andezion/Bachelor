module Bachelor
{
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.json;

    opens controller to javafx.fxml;
    opens model to javafx.base;
    opens app to javafx.graphics;

    exports controller;
    exports app;
}