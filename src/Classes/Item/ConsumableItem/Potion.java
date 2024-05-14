package Classes.Item.ConsumableItem;

import Classes.World.Position;
import Classes.Item.Item;
import javafx.scene.layout.GridPane;

//For potions :
//  code is 2 letters for the attribute ; + (positive) or - (negative) ; value from 10 to 100 (scale 10 in 10)
//  ST+20 --> means strength +20%
//  DE-40 --> means defense -40%
public class Potion extends Item{
    private String effect;
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    Position position;

    public String getEffect() {
        return effect;
    }

    public Potion(GridPane g, String name, boolean dropped, int x, int y, String effect) {
        super(g,x, y, name, dropped);
        this.position = position;
        this.effect = effect;
    }
}