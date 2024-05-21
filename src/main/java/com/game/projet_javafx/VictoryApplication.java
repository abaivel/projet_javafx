package com.game.projet_javafx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class VictoryApplication extends Application {
    Media media;
    MediaPlayer mediaPlayer;
    public VictoryApplication() {
        File file = new File("src\\main\\resources\\victory_music.m4a");
        System.out.println("here");
        final String MEDIA_URL = file.toURI().toString();
        System.out.println("here");
        media = new Media(MEDIA_URL);
        System.out.println("here");
        mediaPlayer = new MediaPlayer(media);
        System.out.println("here");
        mediaPlayer.volumeProperty().set(100);
        mediaPlayer.setStopTime(Duration.seconds(212));
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("here");
        StackPane pane = new StackPane();
        System.out.println("here");
        //pane.setStyle("-fx-background-color: black");
        Text text = new Text("You won\n\uD83C\uDFC6");
        text.setStyle("-fx-font-size: 80;-fx-font-family: 'Brush Script MT';");
        text.setFill(Color.FORESTGREEN);
        text.setTextAlignment(TextAlignment.CENTER);
        pane.getChildren().add(text);
        pane.setAlignment(text, Pos.CENTER);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setMaximized(true);
        System.out.println("here");
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            }
        });
        System.out.println(mediaPlayer.getCurrentTime());
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }
}
