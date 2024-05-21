package com.game.projet_javafx;

import Classes.Item.NotConsumableItem.Book;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class BookApplication extends Application {
    //region Attributes
    private Book book;
    //endregion

    //region Constructor
    public BookApplication(Book book){
        this.book = book;
    }
    //endregion

    //region start function
    @Override
    public void start(Stage stage) throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reading Book");
        alert.setHeaderText(book.getName());
        alert.setContentText(book.getText());
        alert.showAndWait();
    }
    //endregion
}
