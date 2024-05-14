package Classes.Item.NotConsumableItem.Weapon;

import javafx.scene.layout.GridPane;
import Classes.Item.Item;

public abstract class Weapon extends Item {
    //region Weapon's attribute
    private double damage;
    //endregion

    //region Constructor
    public Weapon(GridPane g, String name, boolean dropped, int x, int y, double damage, int price) {
        super(g,x,y,name,dropped, price);
        this.damage = damage;
    }
    //endregion

    //region Getters and Setters
    public double getDamage() {return damage;}
    public void setDamage(double damage) {this.damage = damage;}
    //endregion
}
