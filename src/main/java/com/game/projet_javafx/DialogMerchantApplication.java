package com.game.projet_javafx;

import Classes.Item.Item;
import Classes.NPC.Merchant;
import Classes.Player.Player;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Optional;

public class DialogMerchantApplication extends Application {
    //region DiagApp's attributes
    private Merchant merchant;
    private Player player;
    //endregion

    //region Constructor
    public DialogMerchantApplication(Merchant merchant, Player player) {
        this.merchant = merchant;
        this.player = player;
    }
    //endregion

    //region Getters
    public Player getPlayer() {return player;}
    public Merchant getMerchant() {return merchant;}
    //endregion


    @Override
    public void start(Stage stage) throws Exception {
        Alert dBox = new Alert(Alert.AlertType.CONFIRMATION);               //Alert creation
        dBox.setTitle("Dialog with NPC");
        dBox.setHeaderText("Dialog with " + merchant.getName());
        dBox.setContentText("Choose your dialog  :");

        //Options to choose from
        ButtonType btnDiag0 = new ButtonType(this.getMerchant().getDialogues()[0]);
        ButtonType btnDiag1 = new ButtonType(this.getMerchant().getDialogues()[1]);
        ButtonType btnDiag2 = new ButtonType(this.getMerchant().getDialogues()[2]);
        ButtonType btnDiag3 = new ButtonType(this.getMerchant().getDialogues()[3]);
        dBox.getButtonTypes().setAll(btnDiag0, btnDiag1, btnDiag2, btnDiag3);
        Optional<ButtonType> choice = dBox.showAndWait();

        //gets the answers of the choice --> add the function calls after
        if (choice.get() == btnDiag0) {
            System.out.println("Player wants to buy");

            //Alert creation for item choice
            Alert itemDiag = new Alert(Alert.AlertType.CONFIRMATION);
            itemDiag.setTitle("Buy Item");
            itemDiag.setHeaderText("Choose an item to buy:");

            ButtonType okButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            itemDiag.getButtonTypes().setAll(okButtonType);

            //GridPane creation
            GridPane gridPane = new GridPane();
            gridPane.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 3px;");
            gridPane.setHgap(5);
            gridPane.setVgap(5);

            //Money display
            Label merchantMoneyLabel = new Label( this.getMerchant().getName() + " money: " + merchant.getMoney());
            gridPane.add(merchantMoneyLabel, 0, 0);
            //Filling GridPane
            int row = 1;
            for (Item item : this.getMerchant().getInventory()) {
                Label itemLabel = new Label(item.getName() + " : " + item.getPrice() + " pièces");            //label
                Button buyButton = new Button("Buy");
                int currentRow = row;                                   // Capture current row for use in the lambda expression

                buyButton.setOnAction(event -> {
                    if(this.getMerchant().sell(this.getPlayer(), item)){
                        System.out.println("Bought item: " + item.getName());
                        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == currentRow);
                        merchantMoneyLabel.setText(this.getMerchant().getName() + " money : " + this.getMerchant().getMoney());
                    }else{
                        Alert warning = new Alert(Alert.AlertType.WARNING);
                        warning.setTitle("Insufficient Funds");
                        warning.setHeaderText(null);
                        warning.setContentText("You don't have enough money to buy this item.");
                        warning.showAndWait();
                    }

                });

                gridPane.add(itemLabel,0,row);
                gridPane.add(buyButton, 1, row);
                row++;
            }
            itemDiag.getDialogPane().setContent(gridPane);
            itemDiag.showAndWait();

        } else if (choice.get() == btnDiag1) {
            System.out.println("Player wants to sell");

            // Create an alert with a GridPane
            Alert itemDiag = new Alert(Alert.AlertType.CONFIRMATION);
            itemDiag.setTitle("Sell Item");
            itemDiag.setHeaderText("Choose an item to sell:");
            ButtonType okButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            itemDiag.getButtonTypes().setAll(okButtonType);

            // Create a GridPane
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            //Merchant money label
            Label merchantMoneyLabel = new Label( this.getMerchant().getName() + " money: " + merchant.getMoney());
            gridPane.add(merchantMoneyLabel, 0, 0);

            // Populate the GridPane with items
            int row = 1;
            for (Item item : this.player.getInventory()) {
                Label itemNameLabel = new Label(item.getName() + " : " + item.getPrice() + " pièces");
                Button sellButton = new Button("Sell");
                int currentRow = row;

                sellButton.setOnAction(event -> {
                    if(this.getMerchant().buy(this.getPlayer(), item)){
                        System.out.println("Sold item: " + item.getName());
                        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == currentRow);
                        merchantMoneyLabel.setText(this.getMerchant().getName() + " money : " + this.getMerchant().getMoney());
                    }else{
                        Alert warning = new Alert(Alert.AlertType.WARNING);
                        warning.setTitle("Transaction Failed");
                        warning.setHeaderText(null);
                        warning.setContentText("Unable to sell the item.");
                        warning.showAndWait();
                    }
                });
                gridPane.add(itemNameLabel, 0, row);        //adding the item
                gridPane.add(sellButton, 1, row);           //adding buy button for item
                row++;
            }

            itemDiag.getDialogPane().setContent(gridPane);
            itemDiag.showAndWait();
        } else if (choice.get() == btnDiag2) {
            System.out.println("Player want to swap items");
        } else {
            System.out.println("Cancel or Close");
        }
    }


    public static void main(String[] args) {
        launch();
    }
}
