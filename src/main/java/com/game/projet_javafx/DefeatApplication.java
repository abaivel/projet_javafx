package com.game.projet_javafx;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class DefeatApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        StackPane pane = new StackPane();
        pane.setStyle("-fx-background-color: black");
        Text text = new Text("You died\n☠");
        text.setStyle("-fx-font-size: 80;-fx-font-family: 'Brush Script MT';");
        text.setFill(Color.DARKRED);
        text.setTextAlignment(TextAlignment.CENTER);
        pane.getChildren().add(text);
        pane.setAlignment(text, Pos.CENTER);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }
}
