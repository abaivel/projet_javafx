package Classes.Item.ConsumableItem;

import Classes.World.Position;
import Classes.Item.Item;
import Classes.World.World;
import javafx.scene.image.ImageView;
import Classes.World.World;

//For potions :
//  code is 2 letters for the attribute ; + (positive) or - (negative) ; value from 10 to 100 (scale 10 in 10)
//  ST+20 --> means strength +20%
//  DE-40 --> means defense -40%
public class Potion extends Item{

    String effect;
    public Potion(World w, int x, int y, String name, boolean dropped, String effect, int price) {
        super(w,x, y, name, dropped, price);
        this.effect=effect;
    }
    public String getEffect() {
        return effect;
    }
}