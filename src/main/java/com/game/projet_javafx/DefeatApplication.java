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

//Window that appears when the player dies
//Nice sound effect added
public class DefeatApplication extends Application {

    //region start function
    @Override
    public void start(Stage stage) throws Exception {
        StackPane pane = new StackPane();
        pane.setStyle("-fx-background-color: black");
        Text text = new Text("You died\n☠");
        text.setStyle("-fx-font-size: 80;-fx-font-family: 'Brush Script MT';");
        text.setFill(Color.DARKRED);
        text.setTextAlignment(TextAlignment.CENTER);
        pane.getChildren().add(text);
        StackPane.setAlignment(text, Pos.CENTER);

        /*try {
            File file = new File("src\\main\\resources\\death_music.mp3");
            final String MEDIA_URL = file.toURI().toString();
            Media media = new Media(MEDIA_URL);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.volumeProperty().set(100);
            mediaPlayer.setStopTime(Duration.seconds(212));
            mediaPlayer.play();
        }catch (Exception e){
            System.out.println("Music is not supported by the jar");
        }*/
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }
    //endregion

    public static void main(String[] args) {
        launch();
    }
}
