module application.adminfront {
    // ماژول‌های JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    // ماژول‌های شخص ثالث
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.google.gson;
    requires java.net.http; // برای HttpClient

    // ماژول org.json (قبلاً اضافه شده)
    requires org.json;

    // باز کردن پکیج‌ها برای دسترسی FXML و Gson
    opens application.adminfront to javafx.fxml;
    opens controller to javafx.fxml;
    opens model to javafx.fxml, com.google.gson;
    opens service to javafx.fxml, com.google.gson;
    opens util to com.google.gson; // برای ErrorHandler

    // صادرات پکیج‌ها
    exports application.adminfront;
    exports controller;
}