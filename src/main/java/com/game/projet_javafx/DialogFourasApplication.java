package com.game.projet_javafx;

import Classes.Player.Player;
import Classes.NPC.Fouras;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import java.util.Optional;

//Handles the front when going near a Fouras
public class DialogFourasApplication extends Application {
    //region Attributes
    private Fouras fouras;
    private Player player;
    //endregion

    //region Constructor
    public DialogFourasApplication(Fouras fouras, Player player) {
        this.fouras = fouras;
        this.player = player;
    }
    //endregion

    //region Getters
    public Fouras getFouras() {return fouras;}
    public Player getPlayer() {return player;}
    //endregion

    @Override
    public void start(Stage stage) throws Exception {
        Alert enigmaDialog = new Alert(Alert.AlertType.CONFIRMATION);
        enigmaDialog.setTitle("Dialog with Fouras");
        enigmaDialog.setHeaderText(this.getFouras().getName() + " presents an enigma:");

        String enigma = this.getFouras().tellEnigma();
        enigmaDialog.setContentText(enigma);

        ButtonType answerButtonType = new ButtonType("Answer", ButtonType.OK.getButtonData());  //to go to next window with inputText
        enigmaDialog.getButtonTypes().setAll(answerButtonType);

        Optional<ButtonType> choice = enigmaDialog.showAndWait();

        if (choice.isPresent() && choice.get() == answerButtonType) {
            handleAnswerDialog(enigma);
        } else {
            System.out.println("Close");
        }
    }

    private void handleAnswerDialog(String enigma) {
        TextInputDialog answerDialog = new TextInputDialog();
        answerDialog.setTitle("Answer the Enigma");
        answerDialog.setHeaderText("Fouras' Enigma");
        answerDialog.setContentText("Enter your answer:");

        Optional<String> result = answerDialog.showAndWait();
        if (result.isPresent()) {
            String answer = result.get();
            boolean isCorrect = this.getFouras().checkAnswer(this.getPlayer(), enigma, answer);
            if (isCorrect) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Correct Answer!");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Correct! You have earned a reward.");
                successAlert.showAndWait();
            } else {
                Alert failureAlert = new Alert(Alert.AlertType.ERROR);
                failureAlert.setTitle("Wrong Answer!");
                failureAlert.setHeaderText(null);
                failureAlert.setContentText("Sorry, that is incorrect. Better luck next time.");
                failureAlert.showAndWait();
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

