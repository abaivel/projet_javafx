package Classes.NPC;

import Classes.Item.Item;
import Classes.World.Position;
import javafx.scene.layout.GridPane;

public class Merchant extends NPC{
    private String[] dialogues;

    //Constructor with all parameters
    public Merchant(GridPane g, String name, double money, int x, int y) {
        super(g,name, money, x, y);
        this.dialogues = new String[5];
        this.dialogues[0] = "Would you like to see my wares ?\n";
        this.dialogues[1] = "Would you like to sell me something ?\n";
        this.dialogues[2] = "Goodbye !\n";
    }

    //Default constructor
    public Merchant(String name) {
        this(null,name,100,0,0);
    }

    //Getters and Setters
    public void setDialogues(String[] dialogues) {this.dialogues = dialogues;}
    public String[] getDialogues() {return this.dialogues;}

    //For a merchant to sell stuff to the player -> merchant gains money
    public void sell(Item item){

    }

    //For a merchant to buy stuff from the player   -> merchant loose money
    public void buy(Item item){

    }

    public static void main(String[] args) {
        Merchant merchant = new Merchant("Bob The Merchant");

    }
}