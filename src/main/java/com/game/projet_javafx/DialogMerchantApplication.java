package com.game.projet_javafx;

import Classes.Item.Item;
import Classes.NPC.Merchant;
import Classes.Player.Player;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

//Handles the front for dialogs when going near a Merchant
public class DialogMerchantApplication extends Application {

    //region DiagApp's attributes
    private final Merchant merchant;
    private final Player player;
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

    //region start function
    @Override
    public void start(Stage stage) throws Exception {
        Alert dBox = new Alert(Alert.AlertType.CONFIRMATION);               //Alert dialog window : opens when nearby a Merchant
        dBox.setTitle("Dialog with NPC");                                   //set title
        dBox.setHeaderText("Dialog with " + merchant.getName());            //prints the name of the NPC
        dBox.setContentText("Choose your dialog  :");                       //Prints text

        //Options to choose from
        ButtonType btnDiag0 = new ButtonType(this.getMerchant().getDialogues()[0]);     //1st dialog possible for the player
        ButtonType btnDiag1 = new ButtonType(this.getMerchant().getDialogues()[1]);     //2nd dialog possible for the player
        ButtonType btnDiag2 = new ButtonType(this.getMerchant().getDialogues()[2]);     //etc
        ButtonType btnDiag3 = new ButtonType(this.getMerchant().getDialogues()[3]);
        dBox.getButtonTypes().setAll(btnDiag0, btnDiag1, btnDiag2, btnDiag3);           //Adding ButtonTypes to the alert window
        Optional<ButtonType> choice = dBox.showAndWait();                               //Gets the choice of the user depending on the button they click
                                                                                        //Show and wait -> will close once button clicked

        if (choice.isPresent()){                                                        //Verification if there's choice done by the player
            if (choice.get() == btnDiag0) {
                handlePlayerBuysDialog();
            } else if (choice.get() == btnDiag1) {
                handlePlayerSellsDialog();
            } else if (choice.get() == btnDiag2) {
                handleSwapDialog();
            } else {
                System.out.println("Close");
            }
        }

    }
    //endregion

    //region Handlers for player's choice of dialog
    private void handlePlayerBuysDialog() {                         //1st dialog -> players buys ; merchant sells
        System.out.println("Player wants to buy");

        //Alert creation for item choice
        Alert itemDiag = new Alert(Alert.AlertType.CONFIRMATION);   //Alert dialog window
        itemDiag.setTitle("Buy Item");                              //set title
        itemDiag.setHeaderText("Choose an item to buy:");           //prints text

        ButtonType okButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);     //creation of one button : ok
        itemDiag.getButtonTypes().setAll(okButtonType);                                     //Adding the button

        //GridPane creation
        GridPane gridPane = new GridPane();                                                         //GridPane that will contain merchant's items to sell
        gridPane.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 3px;");     //style
        gridPane.setHgap(5);                                                                        //gap settings
        gridPane.setVgap(5);

        //Money display
        Label merchantMoneyLabel = new Label( this.getMerchant().getName() + " money: " + merchant.getMoney());   //Creation of a label to track merchant's money value
        gridPane.add(merchantMoneyLabel, 0, 0);                                                                //Adding label to GridPane

        //Filling GridPane
        int row = 1;                                                                                                 //Setting row to 0 (to fill GripPane)
        for (Item item : this.getMerchant().getInventory()) {                                                        //Loop on merchant's items
            Label itemLabel = new Label(item.getName() + " : " + item.getPrice() + " pièces");                    //label on each item with name + price
            Button buyButton = new Button("Buy");                                                                 //buy button for each item --> player can buy multiple items
            int currentRow = row;                                                                                   // Capture current row for use in the lambda expression

            buyButton.setOnAction(event -> {                                                                        //When buy button clicked
                if(this.getMerchant().sell(this.getPlayer(), item)){                                                //if merchant can sell <-> if player can buy (because sell function returns boolean)
                    System.out.println("Bought item: " + item.getName());                                           //console output
                    gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == currentRow);              //removing bought item from GripPane thanks to current row
                    merchantMoneyLabel.setText(this.getMerchant().getName() + " money : " + this.getMerchant().getMoney()); //update merchant's money
                }else{                                                                                              //if merchant can't sell <-> if player can't buy
                    Alert warning = new Alert(Alert.AlertType.WARNING);                                             //warning alert window
                    if(player.inventoryIsFull()){
                        warning.setTitle("Inventory full");
                        warning.setHeaderText(null);
                        warning.setContentText("Unable to buy the item. Player's inventory is full.");
                    }else{
                        warning.setTitle("Insufficient Funds");                                                         //sets title
                        warning.setHeaderText(null);
                        warning.setContentText("You don't have enough money to buy this item.");                        //prints text

                    }
                    warning.showAndWait();
                                                                                             //closes when button clicked
                }

            });

            gridPane.add(itemLabel,0,row);                                                                        //add to GripPane label
            gridPane.add(buyButton, 1, row);                                                                      //add to GripPane buy button
            row++;                                                                                                  //increase row int for the loop
        }
        itemDiag.getDialogPane().setContent(gridPane);                                                              //sets GridPane to content of the window
        itemDiag.showAndWait();                                                                                     //closes when button clicked
    }

    //2nd dialog -> players sells ; merchant buys
    private void handlePlayerSellsDialog(){                 //see comments from handlePlayerBuysDialog() -> fully commented
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
                    if(merchant.inventoryIsFull()){
                        warning.setContentText("Unable to sell the item. Merchant's inventory is full.");
                    }else{
                        warning.setContentText("Unable to sell the item. The merchant does not have enough money");
                    }

                    warning.showAndWait();
                }
            });
            gridPane.add(itemNameLabel, 0, row);        //adding the item
            gridPane.add(sellButton, 1, row);           //adding buy button for item
            row++;
        }

        itemDiag.getDialogPane().setContent(gridPane);
        itemDiag.showAndWait();
    }

    //3rd dialog -> player and merchant swap items
    private void handleSwapDialog(){
        System.out.println(player);
        System.out.println(merchant);
        while (true) {                                                              //to repat swap item if failed -> breaks when successful or player cancels
            System.out.println("Player wants to swap items");

            List<Item> playerItems = player.getInventory();                         //Gets player's items
            List<Item> merchantItems = merchant.getInventory();                     //Gets merchant's items

            ChoiceDialog<Item> playerItemDialog = new ChoiceDialog<>(playerItems.get(0), playerItems);  //drop menu of the player's items
            playerItemDialog.setTitle("Select Player Item");                                            //sets title
            playerItemDialog.setHeaderText("Select an item from your inventory to swap:");              //sets header text
            playerItemDialog.setContentText("Player Items:");                                           //sets text

            Optional<Item> playerItemResult = playerItemDialog.showAndWait();                           //to get the player's choice for their item to swap

            if (playerItemResult.isEmpty()) {
                break; // Exit the loop if the player cancels the selection
            }

            Item playerItem = playerItemResult.get();                                                   //gets the items the player selected

            ChoiceDialog<Item> merchantItemDialog = new ChoiceDialog<>(merchantItems.get(0), merchantItems);    //drop menu of the merchant's items
            merchantItemDialog.setTitle("Select Merchant Item");                                                //sets title
            merchantItemDialog.setHeaderText("Select an item from merchant's inventory to swap:");              //sets header text
            merchantItemDialog.setContentText("Merchant Items:");                                               //sets text

            Optional<Item> merchantItemResult = merchantItemDialog.showAndWait();                               //to get the player's choice for the merchant's item to swap

            if (merchantItemResult.isEmpty()) {
                break; // Exit the loop if the player cancels the selection
            }

            Item merchantItem = merchantItemResult.get();                                                       //gets the merchant's item

            if (merchant.troc(player, merchantItem, playerItem)) {                                              //calls troc function : returns boolean if can troc or not
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);                                    //Alert dialog window : information -> no choice to make
                successAlert.setTitle("Swap Successful");                                                       //set title
                successAlert.setHeaderText(null);
                successAlert.setContentText("Item swap successful.");                                           //sets content
                successAlert.showAndWait();                                                                     //window will close when ok button clicked or top right cross clicked
                break;                                                                                          // Exit the loop after a successful swap
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);                                            //Alert dialog window : error -> troc failed
                errorAlert.setTitle("Swap Failed");                                                             //sets title
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Swap failed. Item values are too different.");                       //sets content
                errorAlert.showAndWait();                                                                       //window will close when ok button clicked or top right cross clicked
            }
        }
        System.out.println(player);
        System.out.println(merchant);
    }

    //endregion

}
