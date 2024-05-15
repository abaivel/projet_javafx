package Classes.Item.ConsumableItem;

import Classes.World.Position;
import Classes.Item.Item;
import javafx.scene.layout.GridPane;

//For potions :
//  code is 2 letters for the effect attribute ; + (positive) or - (negative) ; value from 10 to 100 (scale 10 in 10)
//  ST+20 --> means strength +20%
//  DE-40 --> means defense -40%
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

    public Potion(GridPane g, String name, boolean dropped, int x, int y, String effect, int duration) {
        super(g,x, y, name, dropped);
        this.position = position;
        this.effect = effect;
        this.duration = duration;
    }
}