package com.game.projet_javafx;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

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

        File file = new File("src\\main\\resources\\death_music.mp3");//"H:\\Documents\\école\\ING1\\POO Java\\Programmes\\tp2 ihm\\tp2\\src\\main\\resources\\ts.mp3");
        System.out.println("here");
        final String MEDIA_URL = file.toURI().toString();
        System.out.println("here");
        Media media = new Media(MEDIA_URL);
        System.out.println("here");
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        System.out.println("here");
        mediaPlayer.volumeProperty().set(100);
        mediaPlayer.setStopTime(Duration.seconds(212));
        mediaPlayer.play();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }
}
