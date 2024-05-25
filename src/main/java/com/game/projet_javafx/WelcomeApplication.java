package com.game.projet_javafx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

//First window of the game
//Welcome !
public class WelcomeApplication extends Application {

    //region start function
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Game");
        Button btPlay = new Button("Play");
        btPlay.setStyle("-fx-font-size: 40;-fx-font-family: 'Brush Script MT';-fx-background-color: white;-fx-border-color: black");
        Text text = new Text("Welcome to this game");
        text.setStyle("-fx-font-size: 80;-fx-font-family: 'Brush Script MT'");
        StackPane pane = new StackPane();
        StackPane.setAlignment(text, Pos.TOP_CENTER);
        pane.getChildren().add(text);
        pane.getChildren().add(btPlay);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        btPlay.setOnAction(actionEvent -> {
            GameApplication game = new GameApplication();
            try {
                game.start(new Stage());
                stage.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    //endregion

    public static void main(String[] args) {
        launch();
    }
}