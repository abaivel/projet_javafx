package Classes.NPC;

import Classes.NPC.NPC;
import Classes.Item.Item;
import Classes.World.Position;

public class Merchant extends NPC {
    private String[] dialogues;

    //Constructor with all parameters
    public Merchant(String name, double money, Position position) {
        super(name, money, position);
        this.dialogues = new String[5];
        this.dialogues[0] = "Would you like to see my wares ?\n";
        this.dialogues[1] = "Would you like to sell me something ?\n";
        this.dialogues[2] = "Goodbye !\n";
    }

    //Default constructor
    public Merchant(String name) {
        this(name,100,new Position(0,0));
    }

    //Getters and Setters
    public void setDialogues(String[] dialogues) {this.dialogues = dialogues;}
    public String[] getDialogues() {return this.dialogues;}

    //For a merchant to sell stuff to the player -> merchant gains money
    public void sell(Item item){

    }

    //For a merchant to buy stuff from the player   -> merchant looses money
    public void buy(Item item){

    }

    //A function to do troc with the player ? Item against item
    public void troc(Item item){

    }

    public static void main(String[] args) {
        Merchant merchant = new Merchant("Bob The Merchant");

    }
}