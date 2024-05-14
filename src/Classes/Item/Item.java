package Classes.Item;

import Classes.GameObject;
import Classes.World.Position;
import javafx.scene.layout.GridPane;

public abstract class Item extends GameObject {
    public Item(GridPane g, int x, int y) {
        super(g, x, y);
    }

    public void pickUp(){

    }
    public void appear(){

    }
}
