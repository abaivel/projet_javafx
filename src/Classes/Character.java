package Classes;

import Classes.Item.Item;
import Classes.World.World;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.Random;

public abstract class Character extends GameObject {
    private String name;
    private ArrayList<Item> inventory;
    private final IntegerProperty sizeInventory;
    public Character(World w, int x, int y, String name, ArrayList<Item> inventory) {
        super(w, x, y);
        this.name=name;
        this.inventory = inventory;
        this.sizeInventory = new SimpleIntegerProperty(inventory.size());
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public int getSizeInventory() {
        return sizeInventory.get();
    }

    public IntegerProperty getSizeInventoryProperty() {
        return sizeInventory;
    }

    public void setSizeInventory(int sizeInventory) {
        this.sizeInventory.set(sizeInventory);
    }

    public void addToInventory(Item item){
        if (this.getInventory().size()<10) {
            this.getInventory().add(item);
            item.setDropped(false);
            this.setSizeInventory(this.getSizeInventory()+1);
        }
    }
    public Item removeFromInventory(Item item){
        int index = this.getInventory().indexOf(item);
        if(index != -1){
            Item removed = this.getInventory().remove(index);        //get the removed item
            this.setSizeInventory(this.getSizeInventory()-1);
            return removed;
        }else{
            return null;                                        //if item not in inventory
        }
    }
    public boolean inventoryIsFull() {
        return this.getInventory().size() == 10;
    }
    public Item randomItemFromInventory() {
        if (!this.getInventory().isEmpty()) {
            int index = new Random().nextInt(this.getInventory().size());        //randomize an index
            return this.getInventory().get(index);                          //return the item linked to the random index
        }
        return null;
    }
}
