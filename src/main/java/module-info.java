module com.example.league_final_entry {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.league_final_entry to javafx.fxml;
    exports com.example.league_final_entry;
}