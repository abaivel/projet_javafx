package Classes.NPC;

import Classes.NPC.NPC;
import Classes.Item.Item;
import Classes.Player.Player;
import Classes.World.Position;
import Classes.World.World;

public class Merchant extends NPC {
    //region Merchant's attributes
    private String[] dialogues;
    //endregion

    //region Constructor with all parameters
    public Merchant(World w,String name, double money, int x, int y) {
        super(w,name, money, x,y);
        this.dialogues = new String[5];
        this.dialogues[0] = "Would you like to see my wares ?\n";
        this.dialogues[1] = "Would you like to sell me something ?\n";
        this.dialogues[2] = "Would you like to swap one of your item for one of mine ?\n";
        this.dialogues[3] = "Goodbye !\n";
    }
    //endregion

    //region Default constructor
    public Merchant(String name) {
        this(null,name,100,0,0);
    }

    //region Getters and Setters
    public void setDialogues(String[] dialogues) {this.dialogues = dialogues;}
    public String[] getDialogues() {return this.dialogues;}
    //endregion

    //region For a merchant to sell stuff to the player -> merchant gains money
    public boolean sell(Player p,Item item){
        if (item.getPrice()<p.getMoney()){ //if the player has enough money to buy the item, the merchant can sell the item to them
            p.addToInventory(item);
            this.removeFromInventory(item);
            return true;
        }else{
            return false;
        }
    }
    //endregion

    //region For a merchant to buy stuff from the player   -> merchant looses money
    public boolean buy(Player p, Item item){
        if (item.getPrice()<this.getMoney()){  //if the merchant has enough money to buy the item, the merchant can buy the item
            p.removeFromInventory(item);
            this.addToInventory(item);
            return true;
        }else{
            return false;
        }
    }
    //endregion

    //region For a merchant and a player to swap items
    public boolean troc(Player p,Item itemMerchant, Item itemPlayer){
        if (itemMerchant.getPrice()-5<itemPlayer.getPrice() && itemMerchant.getPrice()+5>itemPlayer.getPrice()){ //if the player's item is more or less than 5 coins than the item of the merchant
            p.removeFromInventory(itemPlayer);
            this.addToInventory(itemPlayer);
            p.addToInventory(itemMerchant);
            this.removeFromInventory(itemMerchant);
            return true;
        }else{
            return false;
        }
    }
    //endregion

    public static void main(String[] args) {
        Merchant merchant = new Merchant("Bob The Merchant");

    }
}