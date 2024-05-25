package com.game.projet_javafx;

import Classes.NPC.UselessPerson;
import Classes.Player.Player;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

//Handles the front for dialogs when going near a UselessPerson
public class DialogUselessApplication extends Application {

    //region Attributes
    private final UselessPerson uselessPerson;
    private final Player player;
    //endregion

    //region Constructor
    public DialogUselessApplication(UselessPerson uselessPerson, Player player) {
        this.uselessPerson = uselessPerson;
        this.player = player;
    }
    //endregion

    //region Getters
    public UselessPerson getUselessPerson() {return uselessPerson;}
    public Player getPlayer() {return player;}
    //endregion

    //region start function
    @Override
    public void start(Stage stage) throws Exception {
        Alert uselessDialog = new Alert(Alert.AlertType.CONFIRMATION);                      //Alert dialog window : opens when nearby a UselessPerson
        uselessDialog.setTitle("Dialog with " + uselessPerson.getName());                   //set title
        uselessDialog.setHeaderText("You are interacting with " + uselessPerson.getName()); //prints the name of the NPC

        ButtonType welcomeButtonType = new ButtonType("Welcome");                         //1st dialog possible for the player
        ButtonType goodbyeButtonType = new ButtonType("Goodbye");                         //2nd dialog possible for the player
        uselessDialog.getButtonTypes().setAll(welcomeButtonType, goodbyeButtonType);        //Adding ButtonTypes to the alert window

        Optional<ButtonType> choice = uselessDialog.showAndWait();                          //Gets the choice of the user depending on the button they click
                                                                                            //Show and wait -> will close once button clicked

        if (choice.isPresent()) {                                                           //Verification if there's choice done by the player
            if (choice.get() == welcomeButtonType) {                                        //If players welcomes : so does UselessPerson
                handleWelcomeDialog();
            } else if (choice.get() == goodbyeButtonType) {                                 //If player says goodbye : so does UselessPerson
                handleGoodbyeDialog();
            } else {                                                                        //Only left possibility -> close
                System.out.println("Close");
            }
        }
    }
    //endregion

    //region Handlers
    private void handleWelcomeDialog() {                                                    //To handle situation if player welcomes
        Alert welcomeAlert = new Alert(Alert.AlertType.INFORMATION);                        //new alert that informs -> no choice to do
        welcomeAlert.setTitle("Welcome");
        welcomeAlert.setHeaderText(null);
        welcomeAlert.setContentText(uselessPerson.welcome());                               //displays UselessPeron's dialog
        welcomeAlert.showAndWait();                                                         //window will close when clicking ok button or top right cross
    }

    private void handleGoodbyeDialog() {                                                    //To handle situation if player says goodbye
        Alert goodbyeAlert = new Alert(Alert.AlertType.INFORMATION);                        //new alert that informs -> no choice to do
        goodbyeAlert.setTitle("Goodbye");
        goodbyeAlert.setHeaderText(null);
        goodbyeAlert.setContentText(uselessPerson.goodbye());                               //displays UselessPeron's dialog
        goodbyeAlert.showAndWait();                                                         //window will close when clicking ok button or top right cross
    }
    //endregion


}
