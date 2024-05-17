module com.game.projet_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.game.projet_javafx to javafx.fxml;
    exports com.game.projet_javafx;
}