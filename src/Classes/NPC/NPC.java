package Classes.NPC;
import Classes.Character;
import Classes.GameObject;
import Classes.Item.Item;
import Classes.World.Position;
import Classes.World.World;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

//Non-Playable Characters -> trigger dialogs when the player is in the 9 cases around the NPC
public abstract class NPC extends Character {

    //region NPC's attributes
    private double money;
    //endregion

    //region Constructor with all parameters
    public NPC(World w, int x, int y, String name, double money, String urlImage) {
        super(w,x,y,name,new ArrayList<>());
        this.money = money;
        this.node = new ImageView(urlImage);
        ((ImageView)node).setFitHeight((double) Position.HEIGHT /Position.ROWS);
        ((ImageView)node).setFitWidth((double) Position.WIDTH/Position.COLUMNS);
    }
    //endregion

    //region Getters and Setters

    public double getMoney() {return money;}
    public void setMoney(double money) {this.money = money;}
    //endregion

    //region ToString function to print
    public String toString() {
        String tmp = "Name :" + this.getName() + "\nMoney :" + this.getMoney() + "\nPosition :" + this.getPosition();
        for (Item item : this.getInventory()) {
            tmp += item.toString() + "\n";
        }
        return tmp;
    }
    //endregion





}