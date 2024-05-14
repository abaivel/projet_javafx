module com.game.projet_javafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.game.projet_javafx to javafx.fxml;
    exports com.game.projet_javafx;
}