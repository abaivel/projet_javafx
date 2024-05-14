package Classes.Item.ConsumableItem;

import Classes.World.Position;
import Classes.Item.Item;
import javafx.scene.layout.GridPane;

//For potions :
//  code is 2 letters for the attribute ; + (positive) or - (negative) ; value from 10 to 100 (scale 10 in 10)
//  ST+20 --> means strength +20%
//  DE-40 --> means defense -40%
public class Potion extends Item{
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    Position position;

    public Potion(GridPane g, Position position, int x, int y) {
        super(g,x, y);
        this.position = position;
    }
}