package com.game.projet_javafx;

import Classes.Item.Item;
import Classes.NPC.NPC;
import Classes.Player.Player;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Optional;

public class DialogListItemsInventory{

    boolean toSteal;

    NPC npc;

    Player player;

    public DialogListItemsInventory(boolean toSteal, NPC npc, Player player) {
        this.toSteal = toSteal;
        this.npc = npc;
        this.player = player;
    }
    public void start() throws Exception {
        Alert itemDiag = new Alert(Alert.AlertType.CONFIRMATION);   //Alert dialog window
        itemDiag.setTitle(getNpc().getName()+"'s inventory'");                              //set title
        if (isToSteal()) {
            itemDiag.setHeaderText("Click on the button to steal a item");           //prints text
        }else{
            itemDiag.setHeaderText("This is "+getNpc().getName()+"'s inventory");
        }

        GridPane gridPane = new GridPane();                                                         //GridPane that will contain merchant's items to sell
        gridPane.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 3px;");     //style
        gridPane.setHgap(5);                                                                        //gap settings
        gridPane.setVgap(5);
        //Filling GridPane
        int row = 1;                                                                                                 //Setting row to 0 (to fill GripPane)
        for (Item item : this.getNpc().getInventory()) {                                                        //Loop on merchant's items
            Label itemLabel = new Label(item.getName() + " : " + item.getPrice() + " pièces");                    //label on each item with name + price
            gridPane.add(itemLabel,0,row);                                                              //add to GripPane buy button
            int currentRow = row;
            if (isToSteal()){
                Button stealButton = new Button("Steal");
                stealButton.setOnAction(event -> {                                                                        //When buy button clicked
                    if (player.addToInventory(item)){
                        npc.removeFromInventory(item);
                        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == currentRow);
                        itemDiag.close();
                    }else{
                        Alert warning = new Alert(Alert.AlertType.WARNING);                                             //warning alert window
                        warning.setTitle("Inventory full");
                        warning.setHeaderText(null);
                        warning.setContentText("Unable to steal the item. Player's inventory is full.");
                        warning.showAndWait();
                    }

                });
                gridPane.add(stealButton,1,row);
            }

            row++;
        }
        itemDiag.getDialogPane().setContent(gridPane);                                                              //sets GridPane to content of the window
        itemDiag.showAndWait();
    }

    public boolean isToSteal() {
        return toSteal;
    }
    public NPC getNpc() {
        return npc;
    }
    public Player getPlayer() {
        return player;
    }
}
