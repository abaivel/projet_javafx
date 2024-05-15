package Classes.NPC;

import Classes.NPC.NPC;
import Classes.Item.Item;
import Classes.World.Position;
import javafx.scene.layout.GridPane;

public class Merchant extends NPC {
    //region Merchant's attributes
    private String[] dialogues;
    //endregion

    //region Constructor with all parameters
    public Merchant(GridPane g,String name, double money, int x, int y) {
        super(g,name, money, x,y);
        this.dialogues = new String[5];
        this.dialogues[0] = "Would you like to see my wares ?\n";
        this.dialogues[1] = "Would you like to sell me something ?\n";
        this.dialogues[2] = "Goodbye !\n";
    }
    //endregion

    //region Default constructor
    public Merchant(String name) {
        this(null,name,100,0,0);
    }
    //endregion

    //region Getters and Setters
    public void setDialogues(String[] dialogues) {this.dialogues = dialogues;}
    public String[] getDialogues() {return this.dialogues;}
    //endregion

    //region For a merchant to sell stuff to the player -> merchant gains money
    public void sell(Item item){

    }
    //endregion

    //region For a merchant to buy stuff from the player   -> merchant looses money
    public void buy(Item item){

    }
    //endregion

    //region A function to do troc with the player ? Item against item
    public void troc(Item item){

    }
    //endregion

    public static void main(String[] args) {
        Merchant merchant = new Merchant("Bob The Merchant");

    }
}