package Classes.Item.NotConsumableItem.Weapon;

import Classes.Item.Item;

public abstract class Weapon extends Item {
    //region Weapon's attribute
    private String name;
    private double damage;
    //endregion

    //region Constructor
    public Weapon(GridPane g, String name, boolean dropped, int x, int y, double damage) {
        super(g,x,y,name,dropped);
        this.damage = damage;
    }
    //endregion

    //region Getters and Setters
    public String getName() {return name;}
    public double getDamage() {return damage;}
    public void setDamage(double damage) {this.damage = damage;}
    public void setName(String name) {this.name = name;}
    //endregion
}
