package Classes.Item.ConsumableItem;

import Classes.World.Position;
import Classes.Item.Item;
import Classes.World.World;
import javafx.scene.image.ImageView;
import Classes.World.World;
//todo destroy the potions in the inventory after use
//For potions :
//  code is 2 letters for the effect attribute ; + (positive) or - (negative) ; value from 10 to 100 (scale 10 in 10)
//  ST+20 --> means strength +20%
//  DE-40 --> means defense -40%

//  LIFE10 --> regenerate 10 life points -> duration = 0
public class Potion extends Item{

    private String effect;
    private int duration;

    //region Getters and Setters
    public int getDuration() {return duration;}
    public void setDuration(int duration) {this.duration = duration;}

    public String getEffect() {
        return effect;
    }
    //endregion
    public Potion(World w, int x, int y, String name, boolean dropped, String effect, int price, int duration) {
        super(w,x, y, name, dropped, price);
        this.position = position;
        this.effect = effect;
        this.duration = duration;
    }

    public String toString(){
        String tmp = super.toString();
        tmp += ", Effect " + effect + ", Duration " + duration;
        return tmp;
    }
}