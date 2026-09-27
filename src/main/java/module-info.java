module com.kyozho {
    requires com.fasterxml.jackson.databind;

    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.kyozho.frontend to javafx.fxml;
    opens com.kyozho.backend to com.fasterxml.jackson.databind;
    
    exports com.kyozho.frontend;
}
