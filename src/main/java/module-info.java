module com.example.clientcourseworkjava {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;


    opens com.example.clientcourseworkjava to javafx.fxml;
    exports com.example.clientcourseworkjava;

    opens com.example.clientcourseworkjava.model to javafx.fxml;
    exports  com.example.clientcourseworkjava.model;
}