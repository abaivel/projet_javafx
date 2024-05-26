package Classes.Item.NotConsumableItem.Weapon;

import Classes.World.World;
import Classes.Item.Item;

//Item to deal more damage to an enemy
//Additional damage dealt is automatically added in the fight --> the greatest damage dealer weapon chosen
public abstract class Weapon extends Item {

    //region Weapon's attribute
    private double damage;
    //endregion

    //region Constructor
    public Weapon(World w, int x, int y, String name, int price, boolean dropped, String urlImage, double damage) {
        super(w,x,y,name, price,dropped,urlImage);
        this.damage = damage;
    }
    //endregion

    //region Getters and Setters
    public double getDamage() {return damage;}
    public void setDamage(double damage) {this.damage = damage;}
    //endregion
}
