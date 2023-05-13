module com.example.busto_skaiciuokle {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.busto_skaiciuokle to javafx.fxml;
    exports com.example.busto_skaiciuokle;
}