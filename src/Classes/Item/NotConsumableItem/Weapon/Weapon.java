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
    public Weapon(World w, String name, boolean dropped, int x, int y, double damage, int price, String urlImage) {
        super(w,x,y,name,dropped, price,urlImage);
        this.damage = damage;
    }
    //endregion

    //region Getters and Setters
    public double getDamage() {return damage;}
    public void setDamage(double damage) {this.damage = damage;}
    //endregion
}
