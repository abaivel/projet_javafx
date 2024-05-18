package Classes.World.DecorItem.NotWalkThroughDecorItem;

import Classes.World.Position;
import Classes.World.World;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Wall extends NotWalkThroughDecorItem{
    public Wall(World w, int x, int y) {
        super(w,x, y);
        node=new Rectangle((double) Position.WIDTH/Position.COLUMNS, (double) Position.HEIGHT /Position.ROWS);
        this.node.setStyle("-fx-stroke: #24180a;-fx-fill: #744e20");
    }
}