package com.game.projet_javafx;

import Classes.Item.Item;
import Classes.NPC.Speaker;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DialogNewNPCApplication extends Application {
    Speaker npc;
    public DialogNewNPCApplication(Speaker npc) {
        this.npc = npc;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Alert itemDiag = new Alert(Alert.AlertType.CONFIRMATION);   //Alert dialog window
        itemDiag.setTitle(getNpc().getName()+" wants to show you their inventory");                              //set title
        itemDiag.setHeaderText("Hello ! Look at all the things I've got in my inventory !");           //prints text
        if (getNpc().getInventory().isEmpty()){
            itemDiag.setHeaderText("Oh no ! I've got nothing in my inventory");
        }

        GridPane gridPane = new GridPane();                                                         //GridPane that will contain merchant's items to sell
        gridPane.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 3px;");     //style
        gridPane.setHgap(5);                                                                        //gap settings
        gridPane.setVgap(5);
        //Filling GridPane
        int row = 1;                                                                                                 //Setting row to 0 (to fill GripPane)
        for (Item item : this.getNpc().getInventory()) {                                                        //Loop on merchant's items
            Label itemLabel = new Label(item.getName() + " : " + item.getPrice() + " pièces");                    //label on each item with name + price
            gridPane.add(itemLabel,0,row);                                                                  //add to GripPane buy button
            row++;
        }
        itemDiag.getDialogPane().setContent(gridPane);                                                              //sets GridPane to content of the window
        itemDiag.showAndWait();                                                                                     //closes when button clicked

    }

    public Speaker getNpc() {
        return npc;
    }
}
