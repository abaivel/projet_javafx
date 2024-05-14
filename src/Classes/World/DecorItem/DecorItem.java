package Classes.World.DecorItem;

import Classes.GameObject;
import javafx.scene.layout.GridPane;

public abstract class DecorItem extends GameObject {

    public DecorItem(GridPane g, int x, int y) {
        super(g,x, y);
    }
}