package com.game.projet_javafx;

import Classes.Item.Item;
import Classes.NPC.NPC;
import Classes.Player.Player;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class DialogListMetNPC {
    Player player;
    boolean toSteal;

    public DialogListMetNPC(Player player, boolean toSteal) {
        this.player=player;
        this.toSteal=toSteal;
    }
    public void start() throws Exception {
        Alert npcDiag = new Alert(Alert.AlertType.CONFIRMATION);   //Alert dialog window
        npcDiag.setTitle("See NPC's inventory");                              //set title
        if (isToSteal()){
            npcDiag.setHeaderText("Click on the npc you want to steal an item from");
        }else {
            npcDiag.setHeaderText("Click on the npc whose inventory you want to see");           //prints text
        }
        GridPane gridPane = new GridPane();                                                         //GridPane that will contain merchant's items to sell
        gridPane.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 3px;");     //style
        gridPane.setHgap(5);                                                                        //gap settings
        gridPane.setVgap(5);
        //Filling GridPane
        int row = 1;                                                                                                 //Setting row to 0 (to fill GripPane)
        for (NPC npc : this.getPlayer().getMetNPCs()) {                                                        //Loop on merchant's items
            Label itemLabel = new Label(npc.getName());
            Button seeInventoryButton = new Button("See inventory");
            seeInventoryButton.setOnAction(event -> {                                                                        //When buy button clicked
                DialogListItemsInventory dialogListItemsInventory = new DialogListItemsInventory(isToSteal(),npc, player);
                try {
                    dialogListItemsInventory.start();
                    npcDiag.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            gridPane.add(itemLabel,0,row);
            gridPane.add(seeInventoryButton,1,row);//add to GripPane buy button
            row++;
        }
        /*npcDiag.setOnCloseRequest(new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent dialogEvent) {
                System.out.println(dialogEvent.);
                System.out.println("CLOSE");
            }
        });*/
        npcDiag.getDialogPane().setContent(gridPane);                                                              //sets GridPane to content of the window
        npcDiag.showAndWait();
    }
    public Player getPlayer() {
        return player;
    }
    public boolean isToSteal() {
        return toSteal;
    }
}
