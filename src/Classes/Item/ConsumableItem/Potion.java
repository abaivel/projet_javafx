package Classes.Item.ConsumableItem;

import Classes.Item.Item;
import Classes.World.World;
//For potions :
//  code is 2 letters for the effect attribute ; + (positive) or - (negative) ; value from 10 to 100 (scale 10 in 10)
//  ST+20 --> means strength +20%
//  DE-40 --> means defense -40%

//  LIFE+10 --> regenerate 10 life points -> duration = 0
//  DEATH --> kills an enemy
public class Potion extends Item{

    //region Potion's attributes
    private final String effect;
    private int duration;

    //region Getters and Setters
    public int getDuration() {return duration;}
    public void setDuration(int duration) {this.duration = duration;}

    public String getEffect() {
        return effect;
    }

    //endregion
    public Potion(World w, int x, int y, String name, int price, boolean dropped, String urlImage, String effect,int duration) {
        super(w,x, y, name, price, dropped,urlImage);
        this.effect = effect;
        this.duration = duration;
    }
    //endregion
}