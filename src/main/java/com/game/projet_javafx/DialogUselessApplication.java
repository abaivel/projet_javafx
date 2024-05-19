package com.game.projet_javafx;

import Classes.NPC.UselessPerson;
import Classes.Player.Player;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class DialogUselessApplication extends Application {
    //region Attributes
    private UselessPerson uselessPerson;
    private Player player;
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

    @Override
    public void start(Stage stage) throws Exception {
        Alert uselessDialog = new Alert(Alert.AlertType.CONFIRMATION);
        uselessDialog.setTitle("Dialog with " + uselessPerson.getName());
        uselessDialog.setHeaderText("You are interacting with " + uselessPerson.getName());

        ButtonType welcomeButtonType = new ButtonType("Welcome");
        ButtonType goodbyeButtonType = new ButtonType("Goodbye");
        uselessDialog.getButtonTypes().setAll(welcomeButtonType, goodbyeButtonType);

        Optional<ButtonType> choice = uselessDialog.showAndWait();

        if (choice.isPresent()) {
            if (choice.get() == welcomeButtonType) {
                handleWelcomeDialog();
            } else if (choice.get() == goodbyeButtonType) {
                handleGoodbyeDialog();
            } else {
                System.out.println("Cancel or Close");
            }
        }
    }

    private void handleWelcomeDialog() {
        Alert welcomeAlert = new Alert(Alert.AlertType.INFORMATION);
        welcomeAlert.setTitle("Welcome");
        welcomeAlert.setHeaderText(null);
        welcomeAlert.setContentText(uselessPerson.welcome());
        welcomeAlert.showAndWait();
    }

    private void handleGoodbyeDialog() {
        Alert goodbyeAlert = new Alert(Alert.AlertType.INFORMATION);
        goodbyeAlert.setTitle("Goodbye");
        goodbyeAlert.setHeaderText(null);
        goodbyeAlert.setContentText(uselessPerson.goodbye());
        goodbyeAlert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
