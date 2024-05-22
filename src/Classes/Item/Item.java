package Classes.Item;

import Classes.GameObject;
import Classes.World.Position;
import Classes.World.World;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public abstract class Item extends GameObject {
    //region Item's attributes
    private String name;
    private int price;
    private boolean dropped;    //true : on the ground
                                //false : in an inventory
    private boolean used;       //true if used
    //endregion

    //region Constructor
    public Item(World w, int x, int y, String name, boolean dropped, int price, String urlImage) {
        super(w, x, y);
        this.name = name;
        this.used = false;
        this.dropped = dropped;
        this.price = price;
        this.node = new ImageView(urlImage);
        ((ImageView)node).setFitHeight((double) Position.HEIGHT /Position.ROWS);
        ((ImageView)node).setFitWidth((double) Position.WIDTH/Position.COLUMNS);
    }
    //endregion

    //region Getters and Setters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public boolean isDropped() {return dropped;}
    public void setDropped(boolean dropped) {this.dropped = dropped;}
    public boolean isUsed() {
        return used;
    }
    public void setUsed(boolean used) {
        this.used = used;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    //endregion

    //region toString
    public String toString(){
        return "Name " + getName() + ", Price " + getPrice();
    }
    //endregion

    //region Item
    //When an item is picked up by the player
    public void pickedUp(){
        setDropped(false);
    }

    public void appear(){
        setDropped(true);
    }
    //endregion


}
