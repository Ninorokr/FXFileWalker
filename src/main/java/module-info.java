module com.silverlink.fxfilewalker {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.silverlink.fxfilewalker to javafx.fxml;
    exports com.silverlink.fxfilewalker;
}