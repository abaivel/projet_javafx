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

    //region Start function
    @Override
    public void start(Stage stage) throws Exception {
        Alert enigmaDialog = new Alert(Alert.AlertType.CONFIRMATION);                           //Alert window
        enigmaDialog.setTitle("Dialog with Fouras");                                            //sets title
        enigmaDialog.setHeaderText(this.getFouras().getName() + " presents an enigma:");        //sets header

        String enigma = this.getFouras().tellEnigma();                                          //Fouras tells an enigma caught in variable
        enigmaDialog.setContentText(enigma);                                                    //sets content with the enigma

        ButtonType answerButtonType = new ButtonType("Answer", ButtonType.OK.getButtonData());  //to go to next window with inputText for the player to enter their answer
        enigmaDialog.getButtonTypes().setAll(answerButtonType);                                 //add ButtonType to window

        Optional<ButtonType> choice = enigmaDialog.showAndWait();                               //gets player choice -> answer or close window

        if (choice.isPresent() && choice.get() == answerButtonType) {
            handleAnswerDialog(enigma);                                                         //calls handler
        } else {
            System.out.println("Close");                                                        //window closes
        }
    }
    //endregion

    //region Handler
    private void handleAnswerDialog(String enigma) {
        TextInputDialog answerDialog = new TextInputDialog();                                   //TextInput for player's answer
        answerDialog.setTitle("Answer the Enigma");                                             //sets title
        answerDialog.setHeaderText("Fouras' Enigma");                                           //sets header
        answerDialog.setContentText("Enter your answer:");                                      //sets content

        Optional<String> result = answerDialog.showAndWait();                                   //gets the player's answer
        if (result.isPresent()) {
            String answer = result.get().toLowerCase();                                         //convert to String the answer
            boolean isCorrect = this.getFouras().checkAnswer(this.getPlayer(), enigma, answer); //checks the answer with Fouras' function
            if (isCorrect) {                                                                    //is the player is correct
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Correct Answer!");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Correct! You have earned a reward.");              //the reward is money
                successAlert.showAndWait();
            } else {                                                                            //is the player is not correct
                Alert failureAlert = new Alert(Alert.AlertType.ERROR);
                failureAlert.setTitle("Wrong Answer!");
                failureAlert.setHeaderText(null);
                failureAlert.setContentText("Sorry, that is incorrect. Better luck next time.");
                failureAlert.showAndWait();
            }
        }
    }
    //endregion

}

